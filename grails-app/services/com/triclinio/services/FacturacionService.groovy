package com.triclinio.services

import com.triclinio.domains.cxc.Cliente
import com.triclinio.domains.restaurante.ClienteCuenta
import com.triclinio.domains.restaurante.Cuenta
import com.triclinio.domains.restaurante.OrdenDetalle
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.venta.EstadoFactura
import com.triclinio.domains.venta.Factura
import com.triclinio.domains.venta.FacturaDetalle
import grails.gorm.transactions.Transactional

@Transactional
class FacturacionService {

    /**
     * 
     * @param clienteCuentaId
     * @return
     */
    Factura procesarOrden(long clienteCuentaId, Usuario usuario){

        ClienteCuenta clienteCuenta = ClienteCuenta.get(clienteCuentaId)

        OrdenDetalle ordenDetalle = OrdenDetalle.findByClienteCuentaAndHabilitado(clienteCuenta, true)
        Factura facturaTmp = FacturaDetalle.findByOrdenDetalle(ordenDetalle)?.factura


        
        BigDecimal porcientoImpuesto=0
        BigDecimal porcientoDescuento=0
        BigDecimal montoBruto=0
        BigDecimal montoDescuento=0
        BigDecimal montoImpuesto=0
        BigDecimal montoNeto=0

        Cliente cliente = new Cliente()
        cliente.nombre = clienteCuenta.nombre

        //TODO: cambiar...
        Factura factura = new Factura()
        EstadoFactura estadoFactura = EstadoFactura.findByCodigo(EstadoFactura.PREFACTURA)
        println("La factura Estado: ${estadoFactura.properties}")
        factura.estadoFactura = estadoFactura
        factura.usuario = usuario
        factura.cliente = cliente

        if(facturaTmp){
            println "Ya facturada"
            println "Imprimiendo factura"+facturaTmp
           // facturaTmp.merge(flush: true, failOnError: true)
        }else{
            factura.save(flush: true, failOnError: true)
        }


        def ordenesActivas = OrdenDetalle.findAllByHabilitadoAndClienteCuenta(true, clienteCuenta)

        ordenesActivas.each {
            
            FacturaDetalle facturaDetalle = new FacturaDetalle()

            porcientoImpuesto = porcientoImpuesto + it.porcientoImpuesto
            porcientoDescuento = porcientoDescuento + it.porcientoDescuento
            montoBruto = montoBruto + it.montoBruto
            montoDescuento = montoDescuento + it.montoDescuento
            montoImpuesto = montoImpuesto + it.montoImpuesto
            montoNeto = montoNeto + it.montoNeto


            //Para no mostrar la lista de ordenes detalle dos veces
            if(facturaTmp){
                //Tengo un flag en el orden detalle que dice facturada
                if(!it.facturada) {
                    facturaDetalle.ordenDetalle = it
                    facturaDetalle.factura = facturaTmp
                    facturaDetalle.save(flush: true, failOnError: true)
                }else{

                }
            }else {
                //Si es la primera, ponme que sea true y guardame
                it.facturada=true
                facturaDetalle.ordenDetalle = it
                facturaDetalle.factura = factura
                facturaDetalle.save(flush: true, failOnError: true)
                it.save(flush:true,failOnError:true)
            }

        }

        factura.porcientoImpuesto=porcientoImpuesto
        factura.porcientoDescuento=porcientoDescuento
        factura.montoBruto=montoBruto
        factura.montoDescuento=montoDescuento
        factura.montoImpuesto=montoImpuesto
        factura.montoNeto=montoNeto


        //Para guardar la cantidad total
        if(facturaTmp){
            println "Ya facturada"
            facturaTmp.porcientoImpuesto=porcientoImpuesto
            facturaTmp.porcientoDescuento=porcientoDescuento
            facturaTmp.montoBruto=montoBruto
            facturaTmp.montoDescuento=montoDescuento
            facturaTmp.montoImpuesto=montoImpuesto
            facturaTmp.montoNeto=montoNeto
            facturaTmp.merge(flush: true, failOnError: true)
        }else{
            factura.save(flush: true, failOnError: true)
        }


    }
}
