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
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN", "ROLE_CAMARERO","ROLE_FACTURADOR","ROLE_SUPERVISOR_FACTURADOR","ROLE_SUPERVISOR_CAMARERO"])
class FacturaDetalleController {

    //Datos dinero
    def matricialService
    def facturacionService


    def index() { }

    /**
     * 
     * @param clienteCuentaId
     * @return
     */
    def procesarOrden(long clienteCuentaId) {

        Factura factura = facturacionService.procesarOrden(clienteCuentaId, (Usuario) applicationContext.springSecurityService.getCurrentUser())

        render factura.id
    }

    def imprimirPreCuenta(long idFactura){
        Factura factura = Factura.findById(idFactura)


        matricialService.generarPreCuenta(factura.id)
        println "Impuesto "+factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.cuenta.id

        redirect(uri:"/cuenta/detalleCuenta?idFactura="+factura.id+"&idCuenta="+factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.cuenta.id)
    }

    def facturar(long factura){
        Factura facturaTmp=Factura.findById(factura)
        //TODO: cambiar..
        List<FacturaDetalle> facturaDetalles=new ArrayList<>()

        facturaDetalles= FacturaDetalle.findAllByFactura(facturaTmp)


        facturaTmp.setEstadoFactura(EstadoFactura.findByCodigo(EstadoFactura.FACTURADA))
        facturaTmp.save(flush: true, failOnError: true)

        [facturaDetalles:facturaDetalles,factura:facturaTmp]
    }

    def imprimir(long idFactura){

        println "Ver:"+idFactura
        Factura factura=Factura.findById(idFactura)

        [factura: factura]
    }


    def imprimirFactura(long id){
        /*def idParametro=params.id
        def idFactura=idParametro.toString()*/

        Factura factura=Factura.findById(id)
        factura.setEstadoFactura(EstadoFactura.findByCodigo(EstadoFactura.FACTURADA))

        matricialService.generarFactura(factura.id)


        def idClienteCuenta = factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.id
        def idCuenta =factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.cuenta.id

        for (OrdenDetalle ordenDetalle : factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.listaOrdenDetalle) {
            ordenDetalle.clienteCuenta.habilitado = false
            ordenDetalle.save(flush: true, failOnError: true)
        }
//
        ClienteCuenta clienteCuenta = ClienteCuenta.findById(idClienteCuenta)
        clienteCuenta.habilitado = false
        clienteCuenta.save(flush: true, failOnError: true)
//
//        println "Size cuenta" + Cuenta.findById(idCuenta).listaClienteCuenta.size()
        boolean habilitado = false
//
        for (ClienteCuenta clienteCuenta1 : Cuenta.findById(idCuenta).listaClienteCuenta)
            if (clienteCuenta1.habilitado) {
                habilitado = true
                break
            }
//
        if (!habilitado) {
            Cuenta cuenta = Cuenta.findById(idCuenta)
            cuenta.setEstadoCuenta(EstadoCuenta.findByCodigo(EstadoCuenta.CERRADA))
            cuenta.save(flush: true, failOnError: true)
        }

        redirect(uri:"/cuenta/cuentasAbiertas")
    }


    def historialFacturas(){
        def facturas=Factura.findAllByEstadoFactura(EstadoFactura.findByCodigo(EstadoFactura.FACTURADA_COBRADA))
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

        redirect(uri:"/cuenta/cuentasAbiertas")
    }

    /**
     * TODO: implementar el metodo e indicando label que diga reimpresion.
     * @param facturaId
     * @return
     */
    def reimpresionFactura(long facturaId){
        matricialService.generarFactura(facturaId);
        render "Imprimiendo"
    }
}

