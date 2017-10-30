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
    Factura procesarOrden(long clienteCuentaId, Usuario usuario) {

        ClienteCuenta clienteCuenta = ClienteCuenta.get(clienteCuentaId)

        List<OrdenDetalle> ordenDetalles = OrdenDetalle.findAllByClienteCuentaAndHabilitado(clienteCuenta, true)
//        Factura facturaTmp = FacturaDetalle.findByOrdenDetalle(ordenDetalle)?.factura

        BigDecimal porcientoImpuesto = 0
        BigDecimal porcientoDescuento = 0
        BigDecimal montoBruto = 0
        BigDecimal montoDescuento = 0
        BigDecimal montoImpuesto = 0
        BigDecimal montoNeto = 0


        Factura facturaTmp;
        boolean tieneFactura = false

        for (OrdenDetalle ordenDetalle : ordenDetalles) {
            println "Ya en orden Detalle"
            facturaTmp = FacturaDetalle.findByOrdenDetalle(ordenDetalle)?.factura
            if (facturaTmp) {
                println "Ya tiene factura"
                tieneFactura = true
                break
            }
        }

        def ordenesActivas = OrdenDetalle.findAllByHabilitadoAndClienteCuenta(true, clienteCuenta)

        if (tieneFactura) {
            println "Ya facturada"

            ordenesActivas.each {
                if (!it.facturada) {
                    println "No se ha facturado"
                    FacturaDetalle facturaDetalle = new FacturaDetalle()

                    facturaTmp.porcientoImpuesto = facturaTmp.porcientoImpuesto + it.porcientoImpuesto
                    facturaTmp.porcientoDescuento = facturaTmp.porcientoDescuento + it.porcientoDescuento
                    facturaTmp.montoBruto = facturaTmp.montoBruto + it.montoBruto
                    facturaTmp.montoDescuento = facturaTmp.montoDescuento + it.montoDescuento
                    facturaTmp.montoImpuesto = facturaTmp.montoImpuesto + it.montoImpuesto
                    facturaTmp.montoNeto = facturaTmp.montoNeto + it.montoNeto


                    it.facturada = true
                    it.save(flush: true, failOnError: true)

                    facturaDetalle.ordenDetalle = it
                    facturaDetalle.factura = facturaTmp
                    facturaDetalle.save(flush: true, failOnError: true)
                }
            }

//            facturaTmp.porcientoImpuesto = porcientoImpuesto
//            facturaTmp.porcientoDescuento = porcientoDescuento
//            facturaTmp.montoBruto = montoBruto
//            facturaTmp.montoDescuento = montoDescuento
//            facturaTmp.montoImpuesto = montoImpuesto
//            facturaTmp.montoNeto = montoNeto
//
//
            facturaTmp.save(flush: true, failOnError: true)

        } else {
            println "Nueva Factura"
            Cliente cliente = new Cliente()
            cliente.nombre = clienteCuenta.nombre

            //TODO: cambiar...
            Factura factura = new Factura()

            factura.setEstadoFactura(EstadoFactura.findByCodigo(EstadoFactura.PREFACTURA))
            factura.usuario = usuario
            factura.cliente = cliente

            factura.save(flush: true, failOnError: true)

            ordenesActivas.each {
                println "Creando Nueva factura"
                FacturaDetalle facturaDetalle = new FacturaDetalle()

                porcientoImpuesto = porcientoImpuesto + it.porcientoImpuesto
                porcientoDescuento = porcientoDescuento + it.porcientoDescuento
                montoBruto = montoBruto + it.montoBruto
                montoDescuento = montoDescuento + it.montoDescuento
                montoImpuesto = montoImpuesto + it.montoImpuesto
                montoNeto = montoNeto + it.montoNeto

                it.facturada = true
                it.save(flush: true, failOnError: true)
                facturaDetalle.ordenDetalle = it
                facturaDetalle.factura = factura
                facturaDetalle.save(flush: true, failOnError: true)

            }

            factura.porcientoImpuesto = porcientoImpuesto
            factura.porcientoDescuento = porcientoDescuento
            factura.montoBruto = montoBruto
            factura.montoDescuento = montoDescuento
            factura.montoImpuesto = montoImpuesto
            factura.montoNeto = montoNeto

//        //Para guardar la cantidad total
//        if(facturaTmp){
//            println "Ya facturada"
//            facturaTmp.porcientoImpuesto=porcientoImpuesto
//            facturaTmp.porcientoDescuento=porcientoDescuento
//            facturaTmp.montoBruto=montoBruto
//            facturaTmp.montoDescuento=montoDescuento
//            facturaTmp.montoImpuesto=montoImpuesto
//            facturaTmp.montoNeto=montoNeto
//            facturaTmp.merge(flush: true, failOnError: true)
//        }else{
            factura.save(flush: true, failOnError: true)
            // }


        }
    }
}