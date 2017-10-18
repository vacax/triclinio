package com.triclinio.controllers.restaurante

import com.triclinio.domains.cxc.Cliente
import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.OrdenDetalle
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.venta.EstadoFactura
import com.triclinio.domains.venta.Factura
import com.triclinio.domains.venta.FacturaDetalle
import grails.plugin.springsecurity.annotation.Secured

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
        EstadoFactura estadoFactura=new EstadoFactura(codigo: EstadoFactura.FACTURADA,nombre: "estado FACTURA")

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

//        factura.usuario = (Usuario)springSecurityService.currentUser
        factura.usuario=(Usuario)applicationContext.springSecurityService.getCurrentUser()
        factura.setCliente(cliente)
        factura.setPorcientoImpuesto(porcientoImpuesto)
        factura.setPorcientoDescuento(porcientoDescuento)
        factura.setMontoBruto(montoBruto)
        factura.setMontoDescuento(montoDescuento)
        factura.setMontoImpuesto(montoImpuesto)
        factura.setMontoNeto(montoNeto)
        factura.setEstadoFactura(EstadoFactura.findById(1000))
        factura.save(flush: true, failOnError: true)

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
        List<FacturaDetalle> facturaDetalles=new ArrayList<>()

        facturaDetalles= FacturaDetalle.findAllByFactura(factura)


        factura.setEstadoFactura(EstadoFactura.findById(1001))
        factura.save(flush: true, failOnError: true)

        [facturaDetalles:facturaDetalles,factura:factura]
    }

    def imprimir(){
        def idParametro=params.id
        def idFactura=idParametro.toString()

        println "Ver:"+idFactura

        Factura factura=Factura.findById(idFactura as Long)
        //factura.setEstadoFactura(EstadoFactura.findById(1002))

        [factura: factura]
    }

    def imprimirFactura(){
        def idParametro=params.id
        def idFactura=idParametro.toString()

        Factura factura=Factura.findById(idFactura as Long)
        factura.setEstadoFactura(EstadoFactura.findById(1002))

        redirect(uri:"/cuenta/cuentasAbiertas")
    }


    def historialFacturas(){
        def facturas=Factura.findAllByEstadoFactura(EstadoFactura.findById(1002))
        [facturas: facturas]
    }

    def reversarFactura(){
        def factura=Factura.findById(params.get("idFactura") as Long)
        factura.setEstadoFactura(EstadoFactura.findById(1000))
        factura.setMontoImpuesto(0)
        factura.setMontoNeto(0)
        factura.setMontoDescuento(0)
        factura.setMontoBruto(0)
        factura.setPorcientoDescuento(0)
        factura.setPorcientoImpuesto(0)

        factura.save(flush:true,failsOnError:true)


        for(OrdenDetalle ordenDetalle: factura.listaFacturaDetalle.ordenDetalle){

            ordenDetalle.clienteCuenta.cuenta.setEstadoCuenta(EstadoCuenta.findById(1))
            ordenDetalle.save(flush: true, failOnError: true)
        }

        redirect(uri:"/cuenta/cuentasAbiertas")
    }
}

