package com.triclinio.controllers.restaurante

import com.triclinio.domains.cxc.Cliente
import com.triclinio.domains.restaurante.ClienteCuenta
import com.triclinio.domains.restaurante.Cuenta
import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.OrdenDetalle
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.venta.EstadoFactura
import com.triclinio.domains.venta.Factura
import com.triclinio.domains.venta.FacturaDetalle
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.web.servlet.ModelAndView


@Secured(["ROLE_ADMIN", "ROLE_CAMARERO","ROLE_FACTURADOR","ROLE_SUPERVISOR_FACTURADOR","ROLE_SUPERVISOR_CAMARERO"])
class FacturaDetalleController {

    //Datos dinero



    def index() { }

    def procesarOrden() {
        def ordenesDetalles;
        BigDecimal porcientoImpuesto=0
        BigDecimal porcientoDescuento=0
        BigDecimal montoBruto=0
        BigDecimal montoDescuento=0
        BigDecimal montoImpuesto=0
        BigDecimal montoNeto=0

        ordenesDetalles = params.OrdenDetalle
        def idsOrdenDetalle=ordenesDetalles.split(",")
        EstadoFactura estadoFactura=new EstadoFactura(codigo: EstadoFactura.FACTURADA,nombre: "estado FACTURA")

        List<OrdenDetalle> ordenDetalles = new ArrayList<>()
        Factura factura = new Factura().save(flush: true, failOnError: true)


        Cliente cliente = new Cliente()
        for (int i = 0; i < idsOrdenDetalle.size(); i++) {
            FacturaDetalle facturaDetalle = new FacturaDetalle()
            OrdenDetalle ordenDetalleActual = OrdenDetalle.findById(idsOrdenDetalle.getAt(i) as Long)
            ordenDetalles.add(ordenDetalleActual)

            porcientoImpuesto = porcientoImpuesto + ordenDetalleActual.porcientoImpuesto
            porcientoDescuento = porcientoDescuento + ordenDetalleActual.porcientoDescuento
            montoBruto = montoBruto + ordenDetalleActual.montoBruto
            montoDescuento = montoDescuento + ordenDetalleActual.montoDescuento
            montoImpuesto = montoImpuesto + ordenDetalleActual.montoImpuesto
            montoNeto = montoNeto + ordenDetalleActual.montoNeto

            cliente.setNombre(ordenDetalleActual.clienteCuenta.nombre)

            facturaDetalle.setOrdenDetalle(ordenDetalleActual)
            facturaDetalle.setFactura(factura)
            facturaDetalle.save(flush: true, failOnError: true)
        }

        factura.usuario = (Usuario) applicationContext.springSecurityService.getCurrentUser()
        factura.setCliente(cliente)
        factura.setPorcientoImpuesto(porcientoImpuesto)
        factura.setPorcientoDescuento(porcientoDescuento)
        factura.setMontoBruto(montoBruto)
        factura.setMontoDescuento(montoDescuento)
        factura.setMontoImpuesto(montoImpuesto)
        factura.setMontoNeto(montoNeto)
        factura.setEstadoFactura(EstadoFactura.findById(1000))
        factura.save(flush: true, failOnError: true)

        def idClienteCuenta = ordenDetalles.get(0).clienteCuenta.id
        def idCuenta = ordenDetalles.get(0).clienteCuenta.cuenta.id


        for (OrdenDetalle ordenDetalle : ordenDetalles) {
            ordenDetalle.clienteCuenta.habilitado = false

//                if(ordenDetalle.clienteCuenta.cuenta.listaClienteCuenta.size())
//                ordenDetalle.clienteCuenta.cuenta.setEstadoCuenta(EstadoCuenta.findById(2))
            ordenDetalle.save(flush: true, failOnError: true)
        }

        ClienteCuenta clienteCuenta = ClienteCuenta.findById(idClienteCuenta)
        clienteCuenta.habilitado = false
        clienteCuenta.save(flush: true, failOnError: true)

        println "Size cuenta" + Cuenta.findById(idCuenta).listaClienteCuenta.size()
        boolean habilitado = false

        for (ClienteCuenta clienteCuenta1 : Cuenta.findById(idCuenta).listaClienteCuenta)
            if (clienteCuenta1.habilitado) {
                habilitado = true
                break
            }

        if (!habilitado) {
            Cuenta cuenta = Cuenta.findById(idCuenta)
            cuenta.setEstadoCuenta(EstadoCuenta.findByCodigo(EstadoCuenta.CERRADA))
            cuenta.save(flush: true, failOnError: true)
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

        for(OrdenDetalle ordenDetalle: factura.listaFacturaDetalle.ordenDetalle){

            ordenDetalle.clienteCuenta.cuenta.setEstadoCuenta(EstadoCuenta.findById(1))
            for(ClienteCuenta clienteCuenta: ordenDetalle.clienteCuenta.cuenta.listaClienteCuenta){
                clienteCuenta.setHabilitado(true)
                clienteCuenta.save()
            }
            ordenDetalle.save(flush: true, failOnError: true)
        }

        factura.delete()
        redirect(uri:"/cuenta/cuentasAbiertas")
    }
}

