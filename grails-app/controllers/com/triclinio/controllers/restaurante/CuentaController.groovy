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

    // TODO: Ver documentación en https://docs.grails.org/2.3.4/ref/Servlet%20API/session.html
    def static clienteCuentaStatic  = ClienteCuenta.newInstance()
    //
    def springSecurityService
    def cuentaService



    def indexRedirect(){
//        cuentaStatic=null
        redirect(uri:"/cuenta/cuentasAbiertas")
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //INDEX CREAR CUENTA
    def nuevaCuenta(){

    }

    //POST CREAR CUENTA
    def crearNuevaCuenta(){
        Cuenta cuenta = new Cuenta()
        cuenta.usuario = (Usuario)springSecurityService.currentUser
        cuenta.estadoCuenta = EstadoCuenta.findByCodigo(1000)

        cuenta.save(flush: true, failOnError: true)

        for(int i=0;i<params.list("mesaId").size();i++){
            new CuentaMesa(mesa: Mesa.findById(params.list("mesaId").get(i)),cuenta: cuenta).save(flush: true, failOnError: true)
            Mesa mesa = Mesa.findById(params.list("mesaId").get(i))
            mesa.estadoMesa = EstadoMesa.findByCodigo(1000)
            mesa.save(flush: true, failOnError: true)
        }
        println("Nueva cuenta creada!")


        redirect(uri:"/cuenta/detalleOrdenIndex?idCuenta="+cuenta.id)

    }

    //INDEX CREAR DETALLE ORDEN
    def detalleOrdenIndex(long idCuenta){
        def listaPlatos = Plato.list()
        [listaPlatos:listaPlatos,cuenta: Cuenta.findById(idCuenta)]
    }

    //POST CREAR NUEVO CLIENTE
    def clienteCuenta(long cuentaAsignadaId){
        //        //CLIENTE CUENTA
        def clienteCuenta = ClienteCuenta.newInstance()
        clienteCuenta.nombre = params.get("nombreCliente")
        clienteCuenta.cuenta = Cuenta.findById(cuentaAsignadaId)

        //????
        clienteCuenta.porcientoImpuesto = 0.00
        clienteCuenta.porcientoDescuento = 0.00
        clienteCuenta.montoBruto = 0.00
        clienteCuenta.montoDescuento = 0.00
        clienteCuenta.montoImpuesto = 0.00
        clienteCuenta.montoNeto = 0.00
        clienteCuenta.save(flush: true, failOnError: true)

        println("Nuevo cliente creado!")

        render clienteCuenta as JSON
    }

    /**
     *
     * @param idCliente
     * @param idPlato
     * @param cantidad
     * @return
     */
    def nuevaOrdenDetalle(OrdenDetalleCuentaForm form){
        def json = request.getJSON();
        println("EL JSON: "+json)
        println "El form recibido: "+(form as JSON)
        println "Los parametros enviandos: "+params
        //ORDEN DETALLE

        ClienteCuenta clienteCuenta = new ClienteCuenta()
        clienteCuenta.cuenta = Cuenta.get(form.cuentaId)
        clienteCuenta.nombre = form.nombreCliente ? form.nombreCliente : "Cliente Generico"

        clienteCuenta.save(flush: true, failOnError: true)
        
        form.listaPlato.each {

            //
            Plato plato = Plato.get(it.idPlato)
            int cantidad = it.cantidad;

            def ordenDetalle = new OrdenDetalle()
            ordenDetalle.clienteCuenta=clienteCuenta
            ordenDetalle.plato=plato
            ordenDetalle.cantidad=it.cantidad
            ordenDetalle.nombrePlato=plato.nombre

            //calculo de dinero
            ordenDetalle.precio = plato.precio
            ordenDetalle.importe = cantidad * ordenDetalle.precio
            ordenDetalle.porcientoImpuesto = 0.00
            ordenDetalle.porcientoDescuento = 0.00
            ordenDetalle.montoDescuento = ordenDetalle.importe * ordenDetalle.porcientoDescuento
            ordenDetalle.montoBruto = ordenDetalle.importe - ordenDetalle.montoDescuento
            ordenDetalle.montoImpuesto = ordenDetalle.montoBruto * ordenDetalle.porcientoImpuesto
            ordenDetalle.montoNeto = ordenDetalle.montoBruto + ordenDetalle.montoImpuesto

            ordenDetalle.save(flush: true, failOnError: true)
        }


        println("Nuevo detalle orden creado!")



        //redirect(action: "cuentaAgregarFinalizar", params: [idCuenta: clienteCuenta.cuenta.id])
        render clienteCuenta.cuenta as JSON

    }

    /**
     * VENTANA PARA FINALIZAR/AGREAR CLIENTE A CUENTA
     * @param idCuenta
     * @return
     */
    def cuentaAgregarFinalizar(long id){
        println "El id: "+id
        if(id == 0){
            redirect(action: "detalleOrdenIndex", params: [error: "Información ncomplet,,,,"])
            return;
        }
        def cuenta = Cuenta.findById(id)
        //TODO: validar...
        [cuenta: cuenta]
    }

    //INDEX CUENTAS ABIERTAS
    def cuentasAbiertas(){
        def cuentas = Cuenta.findByEstadoCuenta(EstadoCuenta.findByCodigo(EstadoCuenta.getABIERTO()))
        [cuentas:cuentas]
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *
     * @return
     */






    /**
     * TODO: el action debe tener los metodos que reciben,
     *
     *
     * @return
     */



//hello


    //INDEX DETALLE CUENTA ( MUESTRAS LISTADO CLIENTES )
    def detalleCuenta(){
        println(params.get("idCuenta"))
        def cuenta = Cuenta.findById(params.get("idCuenta"))
        [cuenta: cuenta]
    }

    //POST AGREGAS NUEVOS ITEMS A CLIENTE
    def nuevoDetalleOrden2(){
        def clienteCuenta = ClienteCuenta.findById(params.get("clienteCuenta"))
        [clienteCuenta:clienteCuenta]
    }

//    def obtenerDatos(){
//        render ClienteCuenta.list() as JSON
//    }

    //OJOOO!
    def separarCuenta(){
        def clienteCuenta = ClienteCuenta.findById(params.get("clienteCuenta"))
        [clienteCuenta:clienteCuenta]

    }

}
