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
import com.triclinio.domains.venta.FacturaDetalle
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

@Secured(["ROLE_ADMIN", "ROLE_CAMARERO", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
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
    def nuevaCuenta() {
        def mesas = Mesa.findAllByEstadoMesaNotEqualAndHabilitado(EstadoMesa.findAllByCodigo(EstadoMesa.OCUPADA).first(), true)
        [mesas: mesas]
    }

    /**
     * PROCESA LA CUENTA LUEGO DE SELECCIONAR LAS MESAS
     * @return
     */
    def crearNuevaCuenta() {

        def mesas = Mesa.findAllByEstadoMesaNotEqualAndHabilitado(EstadoMesa.findAllByCodigo(EstadoMesa.OCUPADA).first(), true)

        withForm {
            Cuenta cuenta = new Cuenta()
            cuenta.usuario = (Usuario) springSecurityService.currentUser
            cuenta.estadoCuenta = EstadoCuenta.findByCodigo(EstadoCuenta.ABIERTO)
            cuenta.save(flush: true, failOnError: true)
            for (int i = 0; i < params.list("mesaId").size(); i++) {
                Mesa mesa = Mesa.findByNumeroMesa(params.list("mesaId").get(i) as int)
                new CuentaMesa(mesa: mesa, cuenta: cuenta).save(flush: true, failOnError: true)
                if (mesa.numeroMesa != 0) {
                    mesa.estadoMesa = EstadoMesa.findByCodigo(EstadoMesa.getOCUPADA())
                }
                mesa.historial.add(new HistorialMesa(usuario: (Usuario) springSecurityService.currentUser, descripcion: "Se ha creado una cuenta", fecha: new Date()))
                mesa.save(flush: true, failOnError: true)
            }
            redirect(uri: "/cuenta/detalleOrdenIndex?idCuenta=" + cuenta.id)
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
    def detalleOrdenIndex(long idCuenta) {
        def listaPlatos = Plato.findAllByHabilitado(true)

        def platosPorCategoria = [:]

        listaPlatos.each {
            if (platosPorCategoria.containsKey(it.categoriaPlato)) {
                platosPorCategoria[it.categoriaPlato].add(it)
            } else {
                platosPorCategoria[it.categoriaPlato] = [it]
            }
        }

        [listaPlatos: listaPlatos, platosPorCategoria: platosPorCategoria, cuenta: Cuenta.findById(idCuenta)]
    }

    /**
     * PROCESA LA ORDEN DEL CLIENTE, ES DECIR, CADA ORDEN DETALLE DEL CLIENTE
     * @param form
     * @return
     */
    def nuevaOrdenDetalle(OrdenDetalleCuentaForm form) {

        def clienteCuenta = clienteCuentaService.procesarClienteCuenta(form)
        ordenDetalleService.procesarOrdenDetalle(form, clienteCuenta)


        render clienteCuenta.cuenta as JSON
    }

    /**
     * PROCESA NUEVA ORDEN DETALLE A CUENTA EXISTENTE
     * @param form
     * @return
     */

    def nuevaOrdenDetalleCuentaExistentes(UpdateOrdenDetalleCuenta form) {

        ClienteCuenta clienteCuenta = ClienteCuenta.get(form.clienteCuentaId)



        //TODO COMENTARIOS AREGLAR (SE ESTA REMPLAZANDO)
        clienteCuenta.comentario = form.comentario
        clienteCuenta.save(flush: true, failOnError: true)
        ordenDetalleService.updateProcesarOrdenDetalle(form, clienteCuenta)
//
//        matricialService.generarComandaCocina(clienteCuenta.cuenta.id, true)
//        matricialService.generarComandaCocina(clienteCuenta.cuenta.id, false)
        matricialService.generarComandaCocinaAgrupadaCategoria(clienteCuenta.cuenta.id, true)
        matricialService.generarComandaCocinaAgrupadaCategoria(clienteCuenta.cuenta.id, false)

        render clienteCuenta.cuenta as JSON
    }

    /**
     * VENTANA PARA FINALIZAR/AGREAR CLIENTE A CUENTA
     * @param idCuenta
     * @return
     */
    def cuentaAgregarFinalizar(long id) {
        if (id == 0) {
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
    def cancelarCuentaEnProgreso(long idCuenta) {
        Cuenta cuenta = Cuenta.get(idCuenta as Long)

        cuenta.listaMesa.each {
            it.mesa.estadoMesa = EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE)
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
    def cuentasAbiertas() {
        def cuentasAbiertas = Cuenta.findAllByEstadoCuentaAndHabilitado(EstadoCuenta.findByCodigo(EstadoCuenta.ABIERTO), true)
        [cuentasAbiertas: cuentasAbiertas]
    }

    /**
     * VENTANA QUE MUESTRA LA LISTA DE CLIENTES QUE TIENE POSEE LA CUENTA
     * @param idCuenta
     * @return
     */
    def detalleCuenta(long idCuenta, long idFactura) {
//        List<ClienteCuenta> cuentaArrayList=new ArrayList<>()

        def cuenta = Cuenta.get(idCuenta)

//        def listadoClienteCuentas = CuentaMesa.findAllByCuenta(cuenta)
        def listadoMesas = CuentaMesa.findAllByHabilitadoAndCuenta(true, cuenta).mesa

        Factura factura = Factura.get(idFactura)

//        listadoClienteCuentas.each {
//            if(it.habilitado){
//                listadoMesas.add(it.mesa)
//            }
//        }
        def clienteCuenta = ClienteCuenta.findAllByHabilitadoAndCuenta(true, cuenta)

//        for(int i=0;i<cuenta.listaClienteCuenta.size();i++){
//
//            if(cuenta.listaClienteCuenta[i].habilitado){
//                cuentaArrayList.add(cuenta.listaClienteCuenta[i])
//            }
//        }


        [cuenta: cuenta, clienteCuenta: clienteCuenta, listadoMesas: listadoMesas, factura: factura]

    }

    /**
     * SACA UN ARTICULO DE UNA CUENTA EXISTENTE
     * @param clienteCuentaId
     * @param idPlato
     * @return
     */
    def sacarItemCuenta(long clienteCuentaId, long idPlato, long ordenDetalleId) {
        OrdenDetalle ordenDetalle = OrdenDetalle.findByClienteCuentaAndPlato(ClienteCuenta.get(clienteCuentaId), Plato.get(idPlato))

        OrdenDetalle ordenDetalle1 = OrdenDetalle.findById(ordenDetalleId)
        Factura facturaTmp = FacturaDetalle.findByOrdenDetalle(ordenDetalle1)?.factura

        if (facturaTmp) {

            facturaTmp.porcientoImpuesto = facturaTmp.porcientoImpuesto - ordenDetalle1.porcientoImpuesto
            facturaTmp.porcientoDescuento = facturaTmp.porcientoDescuento - ordenDetalle1.porcientoDescuento
            facturaTmp.montoBruto = facturaTmp.montoBruto - ordenDetalle1.montoBruto
            facturaTmp.montoDescuento = facturaTmp.montoDescuento - ordenDetalle1.montoDescuento
            facturaTmp.montoImpuesto = facturaTmp.montoImpuesto - ordenDetalle1.montoImpuesto
            facturaTmp.montoNeto = facturaTmp.montoNeto - ordenDetalle1.montoNeto

            facturaTmp.save(flush: true, failOnError: true)

            facturaTmp.executeUpdate("delete FacturaDetalle f where f.ordenDetalle=:idOrdenDetalle", [idOrdenDetalle: ordenDetalle1])

            ordenDetalle.habilitado = false
            ordenDetalle.save(flush: true, failOnError: true)


        } else {
            ordenDetalle.habilitado = false
            ordenDetalle.save(flush: true, failOnError: true)
        }

        redirect(action: "eliminarOrdenDetalleClienteCuenta", params: [clienteCuentaId: ClienteCuenta.get(clienteCuentaId).id])

    }

    def agregarOrdenDetalleClienteCuenta(long clienteCuentaId) {


        def listadoPlatos = Plato.findAllByHabilitado(true)
        ClienteCuenta clienteCuenta = ClienteCuenta.get(clienteCuentaId)

        def listadoMesas = new HashSet()

        def platosPorCategoria = [:]

        listadoPlatos.each {
            if (platosPorCategoria.containsKey(it.categoriaPlato)) {
                platosPorCategoria[it.categoriaPlato].add(it)
            } else {
                platosPorCategoria[it.categoriaPlato] = [it]
            }
        }

        CuentaMesa.findAllByCuenta(clienteCuenta.cuenta).each {
            if (it.habilitado) {
                listadoMesas.add(it.mesa)
            }
        }


        [listaPlatos: listadoPlatos, platosPorCategoria: platosPorCategoria, clienteCuenta: clienteCuenta, listadoMesas: listadoMesas]
    }

    def eliminarOrdenDetalleClienteCuenta(long clienteCuentaId) {
        ClienteCuenta clienteCuenta = ClienteCuenta.get(clienteCuentaId)

        def ordenesActivas = OrdenDetalle.findAllByHabilitadoAndClienteCuenta(true, clienteCuenta)



        def listadoMesas = new HashSet()

        CuentaMesa.findAllByCuenta(clienteCuenta.cuenta).each {
            if (it.habilitado) {
                listadoMesas.add(it.mesa)
            }
        }


        [clienteCuenta: clienteCuenta, ordenesActivas: ordenesActivas, listadoMesas: listadoMesas]
    }

    /**
     *
     * @param idCuenta
     * @return
     */
    def imprimirComanda(long idCuenta) {

        matricialService.generarComandaCocinaAgrupadaCategoria(idCuenta, true)
        matricialService.generarComandaCocinaAgrupadaCategoria(idCuenta, false)
        redirect(action: "cuentasAbiertas")
    }

    /**
     *
     * @param idCuenta
     * @return
     */
    def reImprimirComanda(long idCuenta) {
        matricialService.generarComandaCocinaAgrupadaCategoria(idCuenta, true, true)
        matricialService.generarComandaCocinaAgrupadaCategoria(idCuenta, false, true)

        //matricialService.generarComandaCocina(idCuenta, false, true)
        redirect(action: "cuentasAbiertas")
    }

    def previewCuentaCliente(long clienteCuentaId) {
        def clienteCuenta = ClienteCuenta.get(clienteCuentaId)

        def ordenesActivas = OrdenDetalle.findAllByHabilitadoAndClienteCuenta(true, clienteCuenta)

        double totalCuenta = 0
        ordenesActivas.each {
            totalCuenta += it.importe
        }

        def listadoClienteCuentas = CuentaMesa.findAllByCuenta(clienteCuenta.cuenta)
        def listadoMesas = new HashSet()

        listadoClienteCuentas.each {
            if (it.habilitado) {
                listadoMesas.add(it.mesa)
            }
        }



        [clienteCuenta: clienteCuenta, ordenesActivas: ordenesActivas, listadoMesas: listadoMesas, totalCuenta: totalCuenta]


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //OJOOO!
    def separarCuenta() {
        def clienteCuenta = ClienteCuenta.findById(params.get("clienteCuenta"))
        [clienteCuenta: clienteCuenta]

    }


    def verOrdenes(long clienteCuenta) {

        def clienteCuentaTmp = ClienteCuenta.findById(clienteCuenta)
//        def clienteCuenta = ClienteCuenta.findById(id)
        def listaOrdenDetalle = new ArrayList()

        //TODO: cambiar..
        for (OrdenDetalle ordenDetalle : clienteCuentaTmp.listaOrdenDetalle) {
            if (ordenDetalle.habilitado) {
                listaOrdenDetalle.add(ordenDetalle)
            }
        }

        [listaOrdenDetalle: listaOrdenDetalle, clienteCuentaId: clienteCuenta]
    }

    def cerraCuenta(long idCuenta) {
        Cuenta cuenta = Cuenta.get(idCuenta)
        cuenta.habilitado = false
        cuenta.estadoCuenta = EstadoCuenta.findByCodigo(EstadoCuenta.ELIMINADA)
        cuenta.save(flush: true, failOnError: true)
        cuenta.listaMesa.each { //TODO: mejorar....
            def mesa = Mesa.get(it.mesa.id)
            mesa.estadoMesa = EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE)
            mesa.save(flush: true, failOnError: true)
        }
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
//
//
//
//        //redirect(action: "cuentaAgregarFinalizar", params: [idCuenta: clienteCuenta.cuenta.id])
//        render clienteCuenta.cuenta as JSON
//
//    }


}
