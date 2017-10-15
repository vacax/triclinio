package com.triclinio.controllers.restaurante

import com.triclinio.commands.restaurant.OrdenDetalleCuentaForm
import com.triclinio.domains.cxc.Cliente
import com.triclinio.domains.restaurante.ClienteCuenta
import com.triclinio.domains.restaurante.Cuenta
import com.triclinio.domains.restaurante.CuentaMesa
import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.EstadoMesa
import com.triclinio.domains.restaurante.Mesa
import com.triclinio.domains.restaurante.OrdenDetalle
import com.triclinio.domains.restaurante.Plato
import com.triclinio.domains.seguridad.Usuario
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

@Secured(["ROLE_ADMIN"])
class CuentaController {

    def clienteCuentaService
    def ordenDetalleService
    def springSecurityService



//    def indexRedirect(){
////        cuentaStatic=null
//        redirect(uri:"/cuenta/cuentasAbiertas")
//    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * VENTANA QUE MUESTRA EL LISTADO DE MESAS PARA INICIAR CON LA CREACION DE LA CUENTA
     * @return
     */
    def nuevaCuenta(){
        def mesasDesactivadas = Mesa.findAllByEstadoMesa(EstadoMesa.findAllByCodigo(EstadoMesa.DESACTIVADA))
        def mesas = Mesa.list()
        mesas.removeAll(mesasDesactivadas)
        [mesas: mesas]

    }

    //POST CREAR CUENTA
    def crearNuevaCuenta(){
        Cuenta cuenta = new Cuenta()
        cuenta.usuario = (Usuario)springSecurityService.currentUser
        cuenta.estadoCuenta = EstadoCuenta.findByCodigo(EstadoCuenta.ABIERTO)

        cuenta.save(flush: true, failOnError: true)

        for(int i=0;i<params.list("mesaId").size();i++){
            new CuentaMesa(mesa: Mesa.findById(params.list("mesaId").get(i)),cuenta: cuenta).save(flush: true, failOnError: true)
            Mesa mesa = Mesa.findById(params.list("mesaId").get(i))
            mesa.estadoMesa = EstadoMesa.findByCodigo(EstadoMesa.getOCUPADA())
            mesa.save(flush: true, failOnError: true)
        }
        println("Nueva cuenta creada!")


        redirect(uri:"/cuenta/detalleOrdenIndex?idCuenta="+cuenta.id)

    }

    /**
     * VENTANA QUE MUESTRA EL LISTADO DE PLATOS PARA TOMAR LA ORDEN
     * @param idCuenta
     * @return
     */
    def detalleOrdenIndex(Long idCuenta){
        def listaPlatos = Plato.list()
        [listaPlatos:listaPlatos,cuenta: Cuenta.findById(idCuenta)]
    }

    /**
     *
     * @param form
     * @return
     */
    def nuevaOrdenDetalle(OrdenDetalleCuentaForm form){

        def clienteCuenta = clienteCuentaService.procesarClienteCuenta(form)
        ordenDetalleService.procesarOrdenDetalle(form,clienteCuenta)

        println("Nuevo detalle orden creado!")

        render clienteCuenta.cuenta as JSON
    }

    /**
     * VENTANA PARA FINALIZAR/AGREAR CLIENTE A CUENTA
     * @param idCuenta
     * @return
     */
    def cuentaAgregarFinalizar(Long id){
        println "El id: "+id
        if(id == 0){
            redirect(action: "detalleOrdenIndex", params: [error: "Información ncomplet,,,,"])
            return;
        }
        def cuenta = Cuenta.findById(id)
        //TODO: validar...
        [cuenta: cuenta]
    }

    /**
     *
     * @return
     */
    def cuentasAbiertas(){
        def cuentasAbiertas = Cuenta.findAllByEstadoCuenta(EstadoCuenta.findByCodigo(EstadoCuenta.ABIERTO))
        [cuentasAbiertas:cuentasAbiertas]
    }

    /**
     * VENTANA QUE MUESTRA LA LISTA DE CLIENTES QUE TIENE POSEE LA CUENTA
     * @param idCuenta
     * @return
     */
    def detalleCuenta(Long idCuenta){
        def cuenta = Cuenta.findById(idCuenta)
        [cuenta: cuenta]
    }























    //TODO CHEQUEAR ESTOOO!!!!
    //POST AGREGAS NUEVOS ITEMS A CLIENTE
    def nuevoDetalleOrden2(){
        def clienteCuenta = ClienteCuenta.findById(params.get("clienteCuenta"))
        [clienteCuenta:clienteCuenta]
    }


    def separarCuenta(){
        def clienteCuenta = ClienteCuenta.findById(params.get("clienteCuenta"))
        [clienteCuenta:clienteCuenta]

    }


    def verOrdenes(long id){

        def clienteCuenta = ClienteCuenta.findById(params.get("clienteCuenta"))
        [listaOrdenDetalle:clienteCuenta.listaOrdenDetalle]
    }


    def eliminarOrdenDetalle(){
        def orden = ClienteCuenta.findById(params.get("orden") as Long)
        println "Orden : "+orden
        redirect(action:'detalleCuenta')
    }

}
