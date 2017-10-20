package com.triclinio.controllers.restaurante

import com.triclinio.commands.restaurant.OrdenDetalleCuentaForm
import com.triclinio.commands.restaurant.UpdateOrdenDetalleCuenta
import com.triclinio.domains.cxc.Cliente
import com.triclinio.domains.restaurante.ClienteCuenta
import com.triclinio.domains.restaurante.Cuenta
import com.triclinio.domains.restaurante.CuentaMesa
import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.EstadoMesa
import com.triclinio.domains.restaurante.HistorialMesa
import com.triclinio.domains.restaurante.Mesa
import com.triclinio.domains.restaurante.OrdenDetalle
import com.triclinio.domains.restaurante.Plato
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.venta.EstadoFactura
import com.triclinio.domains.venta.Factura
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

@Secured(["ROLE_ADMIN", "ROLE_CAMARERO","ROLE_FACTURADOR","ROLE_SUPERVISOR_FACTURADOR","ROLE_SUPERVISOR_CAMARERO"])
class CuentaController {
    def clienteCuentaService
    def ordenDetalleService
    def springSecurityService
    def matricialService





    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * VENTANA QUE MUESTRA EL LISTADO DE MESAS PARA INICIAR CON LA CREACION DE LA CUENTA
     * @return
     */
    def nuevaCuenta(){
        def mesasDesactivadas = Mesa.findAllByEstadoMesa(EstadoMesa.findAllByCodigo(EstadoMesa.DESACTIVADA))
        def mesas = Mesa.list()
        mesas.removeAll(mesasDesactivadas)
        def listaMostrar = new HashSet()

        mesas.each {
            if (it.habilitado){
                listaMostrar.add(it)
            }
        }

        [mesas: listaMostrar]

    }

    /**
     * PROCESA LA CUENTA LUEGO DE SELECCIONAR LAS MESAS
     * @return
     */
    def crearNuevaCuenta(){
        withForm {
            Cuenta cuenta = new Cuenta()
            cuenta.usuario = (Usuario)springSecurityService.currentUser
            cuenta.estadoCuenta = EstadoCuenta.findByCodigo(EstadoCuenta.ABIERTO)

            cuenta.save(flush: true, failOnError: true)



            for(int i=0;i<params.list("mesaId").size();i++){
                new CuentaMesa(mesa: Mesa.findById(params.list("mesaId").get(i)),cuenta: cuenta).save(flush: true, failOnError: true)
                Mesa mesa = Mesa.findById(params.list("mesaId").get(i))
                mesa.estadoMesa = EstadoMesa.findByCodigo(EstadoMesa.getOCUPADA())
                mesa.historial.add(new HistorialMesa(usuario:(Usuario)springSecurityService.currentUser, descripcion: "Se ha creado una cuenta", fecha: new Date() ))
                mesa.save(flush: true, failOnError: true)
            }
            println("Nueva cuenta creada!")


            redirect(uri:"/cuenta/detalleOrdenIndex?idCuenta="+cuenta.id)
        }.invalidToken {
            // bad request
            redirect(action: "cuentasAbiertas")
        }


    }

    /**
     * VENTANA QUE MUESTRA EL LISTADO DE PLATOS PARA TOMAR LA ORDEN
     * @param idCuenta
     * @return
     */
    def detalleOrdenIndex(long idCuenta){
        def listaPlatos = Plato.findAllByHabilitado(true)
        [listaPlatos:listaPlatos,cuenta: Cuenta.findById(idCuenta)]
    }


    /**
     * PROCESA LA ORDEN DEL CLIENTE, ES DECIR, CADA ORDEN DETALLE DEL CLIENTE
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
     * PROCESA NUEVA ORDEN DETALLE A CUENTA EXISTENTE
     * @param form
     * @return
     */
    def nuevaOrdenDetalleCuentaExistentes(UpdateOrdenDetalleCuenta form){
        def clienteCuenta = ClienteCuenta.findById(form.clienteId)
        clienteCuenta.comentario=form.comentario
        clienteCuenta.save(flush:true,failOnError:true)
        ordenDetalleService.updateProcesarOrdenDetalle(form,clienteCuenta)

        println("Nuevo detalle orden agregada!")
        matricialService.generarComandaCocina(clienteCuenta.cuenta.id, true)
        matricialService.generarComandaCocina(clienteCuenta.cuenta.id, false)


        render clienteCuenta.cuenta as JSON
    }

    /**
     * VENTANA PARA FINALIZAR/AGREAR CLIENTE A CUENTA
     * @param idCuenta
     * @return
     */
    def cuentaAgregarFinalizar(long id){
        if(id == 0){
            redirect(action: "detalleOrdenIndex", params: [error: "Información ncomplet,,,,"])
            return
        }
        def cuenta = Cuenta.findById(id as Long)
        //TODO: validar...
        [cuenta: cuenta]
    }

    /**
     *
     * PROCESO QUE SE ENCARGA DE CANCELAR CUENTA EN CURSO SI NO EXISTEN NINGUNA ORDEN DETALLE YA GUARDADA
     * @param idCuenta
     * @return
     */
    def cancelarCuentaEnProgreso(long idCuenta){
        Cuenta cuenta = Cuenta.get(idCuenta as Long)

        cuenta.listaMesa.each {
            it.mesa.estadoMesa=EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE)
            it.mesa.save(flush: true, failOnError: true)

        }
        cuenta.listaMesa*.delete()

        cuenta.delete(flush: true, failOnError: true)

        redirect(action: "cuentasAbiertas")
    }

    /**
     * VENTANA QUE MUESTRA EL LISTADO DE CUENTAS ABIERTAS
     * @return
     */
    def cuentasAbiertas(){
        def cuentasAbiertas = Cuenta.findAllByEstadoCuentaAndHabilitado(EstadoCuenta.findByCodigo(EstadoCuenta.ABIERTO), true)
        [cuentasAbiertas:cuentasAbiertas]
    }

    /**
     * VENTANA QUE MUESTRA LA LISTA DE CLIENTES QUE TIENE POSEE LA CUENTA
     * @param idCuenta
     * @return
     */
    def detalleCuenta(long idCuenta, long idFactura){
        List<ClienteCuenta> cuentaArrayList=new ArrayList<>()


        def cuenta = Cuenta.findById(idCuenta)

        def listadoClienteCuentas = CuentaMesa.findAllByCuenta(cuenta)
        def listadoMesas = new HashSet()
        Factura factura=Factura.get(idFactura)

        listadoClienteCuentas.each {
            if(it.habilitado){
                listadoMesas.add(it.mesa)
            }
        }

        for(int i=0;i<cuenta.listaClienteCuenta.size();i++){

            if(cuenta.listaClienteCuenta[i].habilitado){
                cuentaArrayList.add(cuenta.listaClienteCuenta[i])
            }
        }


        [cuenta: cuentaArrayList,listadoMesas:listadoMesas,factura:factura]

    }

    /**
     * SACA UN ARTICULO DE UNA CUENTA EXISTENTE
     * @param clienteCuentaId
     * @param idPlato
     * @return
     */
    def sacarItemCuenta(long clienteCuentaId,long idPlato){

        OrdenDetalle ordenDetalle = OrdenDetalle.findByClienteCuentaAndPlato(ClienteCuenta.get(clienteCuentaId), Plato.get(idPlato))
        println(ordenDetalle.plato.nombre)
        ordenDetalle.habilitado = false
        ordenDetalle.save(flush:true, failOnError:true)
//        def cliente = ClienteCuenta.get(clienteCuentaId)
//        cliente.listaOrdenDetalle.each {
//            if(it.plato.id==idPlato){
//                it.habilitado=false
//                it.save(flush:true, failOnError:true)
//
//            }
//        }
        redirect(action: "nuevoDetalleOrden2", params:[ clienteCuenta : clienteCuentaId])

    }

    /**
     * VENTANA QUE MUESTRA LAS ORDENES DEL CLIENTE EN UNA CUENTA EXISTENTE, ASI COMO PARA AGREGAR NUEVO ITEMS
     * @return
     */
    def nuevoDetalleOrden2(){
        def clienteCuenta = ClienteCuenta.findById(params.get("clienteCuenta"))

        def ordenesActivas = OrdenDetalle.findAllByHabilitadoAndClienteCuenta(true, clienteCuenta)

//        def ordenesActivas = clienteCuenta.listaOrdenDetalle

//        ordenesActivas.each {
//            if(!it.habilitado){
//                ordenesActivas.remove(it)
//            }
//        }

        def listadoClienteCuentas = CuentaMesa.findAllByCuenta(clienteCuenta.cuenta)
        def listadoMesas = new HashSet()

        listadoClienteCuentas.each {
            if(it.habilitado){
                listadoMesas.add(it.mesa)
            }
        }


        [clienteCuenta:clienteCuenta, ordenesActivas: ordenesActivas, listadoMesas: listadoMesas]
    }

    def imprimirComanda(long idCuenta){

        println(idCuenta)
        matricialService.generarComandaCocinaAgrupadaCategoria(idCuenta, true)
        matricialService.generarComandaCocinaAgrupadaCategoria(idCuenta, false)
        redirect(action: "cuentasAbiertas")
    }

    def reImprimirComanda(long idCuenta){
        println(idCuenta)
        matricialService.generarComandaCocinaAgrupadaCategoria(idCuenta, true, true)
        matricialService.generarComandaCocinaAgrupadaCategoria(idCuenta, false, true)



        //matricialService.generarComandaCocina(idCuenta, false, true)
        redirect(action: "cuentasAbiertas")
    }

    def previewCuentaCliente(long clienteCuentaId){
        def clienteCuenta = ClienteCuenta.get(clienteCuentaId)

        def ordenesActivas = OrdenDetalle.findAllByHabilitadoAndClienteCuenta(true, clienteCuenta)

        double totalCuenta = 0
        ordenesActivas.each {
            totalCuenta+=it.importe
        }

        def listadoClienteCuentas = CuentaMesa.findAllByCuenta(clienteCuenta.cuenta)
        def listadoMesas = new HashSet()

        listadoClienteCuentas.each {
            if(it.habilitado){
                listadoMesas.add(it.mesa)
            }
        }

        println(totalCuenta)


        [clienteCuenta:clienteCuenta, ordenesActivas: ordenesActivas, listadoMesas: listadoMesas,totalCuenta: totalCuenta]


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //OJOOO!
    def separarCuenta(){
        def clienteCuenta = ClienteCuenta.findById(params.get("clienteCuenta"))
        [clienteCuenta:clienteCuenta]

    }


    def verOrdenes(long clienteCuenta){

        def clienteCuentaTmp = ClienteCuenta.findById(clienteCuenta)
//        def clienteCuenta = ClienteCuenta.findById(id)
        def listaOrdenDetalle=new ArrayList()

        //TODO: cambiar..
        for(OrdenDetalle ordenDetalle: clienteCuentaTmp.listaOrdenDetalle){
            if(ordenDetalle.habilitado){
                listaOrdenDetalle.add(ordenDetalle)
            }
        }

        [listaOrdenDetalle: listaOrdenDetalle, clienteCuentaId: clienteCuenta]
    }

    def cerraCuenta(long idCuenta){
        Cuenta cuenta = Cuenta.get(idCuenta)
        cuenta.habilitado = false
        cuenta.save(flush:true, failOnError:true)
        redirect(action: "cuentasAbiertas")

    }





















//
//    //TODO CHEQUEAR ESTOOO!!!!
//    //POST AGREGAS NUEVOS ITEMS A CLIENTE
//    def nuevoDetalleOrden2(){
//        def clienteCuenta = ClienteCuenta.findById(params.get("clienteCuenta"))
//        [clienteCuenta:clienteCuenta]
//    }
//
//
//    def separarCuenta(){
//        def clienteCuenta = ClienteCuenta.findById(params.get("clienteCuenta"))
//        [clienteCuenta:clienteCuenta]
//
//    }
//
//
//    def verOrdenes(long id){
//
//        def clienteCuenta = ClienteCuenta.findById(params.get("clienteCuenta"))
//        [listaOrdenDetalle:clienteCuenta.listaOrdenDetalle]
//    }
//
//
//    def eliminarOrdenDetalle(){
//        def orden = ClienteCuenta.findById(params.get("orden") as Long)
//        println "Orden : "+orden
//        redirect(action:'detalleCuenta')
//    }

    //    //POST CREAR NUEVO CLIENTE
//    def clienteCuenta(long cuentaAsignadaId){
//        //        //CLIENTE CUENTA
//        def clienteCuenta = ClienteCuenta.newInstance()
//        clienteCuenta.nombre = params.get("nombreCliente")
//        clienteCuenta.cuenta = Cuenta.findById(cuentaAsignadaId)
//
//        //????
//        clienteCuenta.porcientoImpuesto = 0.00
//        clienteCuenta.porcientoDescuento = 0.00
//        clienteCuenta.montoBruto = 0.00
//        clienteCuenta.montoDescuento = 0.00
//        clienteCuenta.montoImpuesto = 0.00
//        clienteCuenta.montoNeto = 0.00
//        clienteCuenta.save(flush: true, failOnError: true)
//
//        println("Nuevo cliente creado!")
//
//        render clienteCuenta as JSON
//    }

//    /**
//     *
//     * @param idCliente
//     * @param idPlato
//     * @param cantidad
//     * @return
//     */
//    def nuevaOrdenDetalle(OrdenDetalleCuentaForm form){
//        def json = request.getJSON();
//        println("EL JSON: "+json)
//        println "El form recibido: "+(form as JSON)
//        println "Los parametros enviandos: "+params
//        //ORDEN DETALLE
//
//        ClienteCuenta clienteCuenta = new ClienteCuenta()
//        clienteCuenta.cuenta = Cuenta.get(form.cuentaId)
//        clienteCuenta.nombre = form.nombreCliente ? form.nombreCliente : "Cliente Generico"
//
//        clienteCuenta.save(flush: true, failOnError: true)
//
//        form.listaPlato.each {
//
//            //
//            Plato plato = Plato.get(it.idPlato)
//            int cantidad = it.cantidad;
//
//            def ordenDetalle = new OrdenDetalle()
//            ordenDetalle.clienteCuenta=clienteCuenta
//            ordenDetalle.plato=plato
//            ordenDetalle.cantidad=it.cantidad
//            ordenDetalle.nombrePlato=plato.nombre
//
//            //calculo de dinero
//            ordenDetalle.precio = plato.precio
//            ordenDetalle.importe = cantidad * ordenDetalle.precio
//            ordenDetalle.porcientoImpuesto = 0.00
//            ordenDetalle.porcientoDescuento = 0.00
//            ordenDetalle.montoDescuento = ordenDetalle.importe * ordenDetalle.porcientoDescuento
//            ordenDetalle.montoBruto = ordenDetalle.importe - ordenDetalle.montoDescuento
//            ordenDetalle.montoImpuesto = ordenDetalle.montoBruto * ordenDetalle.porcientoImpuesto
//            ordenDetalle.montoNeto = ordenDetalle.montoBruto + ordenDetalle.montoImpuesto
//
//            ordenDetalle.save(flush: true, failOnError: true)
//        }
//
//
//        println("Nuevo detalle orden creado!")
//
//
//
//        //redirect(action: "cuentaAgregarFinalizar", params: [idCuenta: clienteCuenta.cuenta.id])
//        render clienteCuenta.cuenta as JSON
//
//    }



}
