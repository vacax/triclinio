package com.triclinio.services

import com.triclinio.domains.cxc.Cliente
import com.triclinio.domains.restaurante.ClienteCuenta
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
        factura.save(flush: true, failOnError: true)

        def ordenesActivas = OrdenDetalle.findAllByHabilitadoAndClienteCuenta(true, clienteCuenta)


        ordenesActivas.each {
            
            FacturaDetalle facturaDetalle = new FacturaDetalle()

            porcientoImpuesto = porcientoImpuesto + it.porcientoImpuesto
            porcientoDescuento = porcientoDescuento + it.porcientoDescuento
            montoBruto = montoBruto + it.montoBruto
            montoDescuento = montoDescuento + it.montoDescuento
            montoImpuesto = montoImpuesto + it.montoImpuesto
            montoNeto = montoNeto + it.montoNeto
            
            facturaDetalle.ordenDetalle = it
            facturaDetalle.factura = factura
            facturaDetalle.save(flush: true, failOnError: true)
        }


        factura.porcientoImpuesto=porcientoImpuesto
        factura.porcientoDescuento=porcientoDescuento
        factura.montoBruto=montoBruto
        factura.montoDescuento=montoDescuento
        factura.montoImpuesto=montoImpuesto
        factura.montoNeto=montoNeto
        
        factura.save(flush: true, failOnError: true)


    }
}
