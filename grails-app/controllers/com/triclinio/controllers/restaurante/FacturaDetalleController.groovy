package com.triclinio.controllers.restaurante

import com.triclinio.domains.cxc.Cliente
import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.OrdenDetalle
import com.triclinio.domains.venta.EstadoFactura
import com.triclinio.domains.venta.Factura
import com.triclinio.domains.venta.FacturaDetalle
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.web.servlet.ModelAndView

@Secured(["ROLE_ADMIN"])
class FacturaDetalleController {
    static def ordenesDetalles;
    //Datos dinero
    BigDecimal porcientoImpuesto=0
    BigDecimal porcientoDescuento=0
    BigDecimal montoBruto=0
    BigDecimal montoDescuento=0
    BigDecimal montoImpuesto=0
    BigDecimal montoNeto=0


    def index() { }

    def procesarOrden(){
        ordenesDetalles = params.OrdenDetalle
        def idsOrdenDetalle=ordenesDetalles.split(",")
        EstadoFactura estadoFactura=new EstadoFactura(codigo: EstadoFactura.FACTUDA,nombre: "estado FACTURA")

        List<OrdenDetalle> ordenDetalles=new ArrayList<>()
        Factura factura=new Factura().save(flush: true, failOnError: true)


        Cliente cliente=new Cliente()
        for(int i=0;i<idsOrdenDetalle.size();i++){
            FacturaDetalle facturaDetalle=new FacturaDetalle()
            OrdenDetalle ordenDetalleActual=OrdenDetalle.findById(idsOrdenDetalle.getAt(i) as Long)
            ordenDetalles.add(ordenDetalleActual)

            porcientoImpuesto=porcientoImpuesto+ordenDetalleActual.porcientoImpuesto
            porcientoDescuento=porcientoDescuento+ordenDetalleActual.porcientoDescuento
            montoBruto=montoBruto+ordenDetalleActual.montoBruto
            montoDescuento=montoDescuento+ordenDetalleActual.montoDescuento
            montoImpuesto=montoImpuesto+ordenDetalleActual.montoImpuesto
            montoNeto=montoNeto+ordenDetalleActual.montoNeto

            cliente.setNombre(ordenDetalleActual.clienteCuenta.nombre)

            facturaDetalle.setOrdenDetalle(ordenDetalleActual)
            facturaDetalle.setFactura(factura)
            facturaDetalle.save(flush: true, failOnError: true)
        }

        factura.setCliente(cliente)
        factura.setPorcientoImpuesto(porcientoImpuesto)
        factura.setPorcientoDescuento(porcientoDescuento)
        factura.setMontoBruto(montoBruto)
        factura.setMontoDescuento(montoDescuento)
        factura.setMontoImpuesto(montoImpuesto)
        factura.setMontoNeto(montoNeto)
        factura.setEstadoFactura(EstadoFactura.findById(1001))
        factura.save(flush: true, failOnError: true)

        println  idsOrdenDetalle


        for(OrdenDetalle ordenDetalle: ordenDetalles){

            ordenDetalle.clienteCuenta.cuenta.setEstadoCuenta(EstadoCuenta.findById(2))
            ordenDetalle.save(flush: true, failOnError: true)
        }


        render factura.id
    }


    def facturar(){
        def idParam=params.factura
        def idFactura=idParam.toString()
        Factura factura=Factura.findById(idFactura as Long)

        List<FacturaDetalle> facturaDetalles= FacturaDetalle.findAllByFactura(factura) as List<FacturaDetalle>


        //Osea el puede ver ese metodo el hace el redirect aqui pero ese no renderiza
        for (FacturaDetalle facturaDetalle: facturaDetalles ){
            println "F: "+facturaDetalle.id
        }

        [facturaDetalles:facturaDetalles,factura:factura]
    }

    def imprimir(){
        def idParametro=params.id
        def idFactura=idParametro.toString()

        println "Ver:"+idFactura

        Factura factura=Factura.findById(idFactura as Long)
        factura.setEstadoFactura(EstadoFactura.findById(1002))

        render "Facturada"
    }


}

