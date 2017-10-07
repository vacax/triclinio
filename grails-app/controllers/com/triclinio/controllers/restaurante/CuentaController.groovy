package com.triclinio.controllers.restaurante

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


    def static clienteCuentaStatic
    def static cuentaStatic
    def springSecurityService


    def indexRedirect(){
        cuentaStatic=null
        redirect(uri:"/")
    }

    def nuevaCuenta(){

    }

    def crearNuevaCuenta(){
        Cuenta cuenta = new Cuenta()
        cuenta.usuario = (Usuario)springSecurityService.currentUser
        cuenta.estadoCuenta = EstadoCuenta.findByCodigo(1000)

        cuenta.save(flush: true, failOnError: true)

        cuentaStatic = cuenta;


        for(int i=0;i<params.list("mesaId").size();i++){
            new CuentaMesa(mesa: Mesa.findById(params.list("mesaId").get(i)),cuenta: cuenta).save(flush: true, failOnError: true)
            Mesa mesa = Mesa.findById(params.list("mesaId").get(i))
            mesa.estadoMesa = EstadoMesa.findByCodigo(1000)
            mesa.save(flush: true, failOnError: true)
        }


        redirect(uri:'/cuenta/nuevoDetalleOrden')

    }

    def nuevoDetalleOrden(){
        //CLIENTE CUENTA

        def clienteCuenta = ClienteCuenta.newInstance()
        clienteCuenta.nombre = "Cliente Generico"
        clienteCuenta.cuenta = cuentaStatic

        //????
        clienteCuenta.porcientoImpuesto = 0.00
        clienteCuenta.porcientoDescuento = 0.00
        clienteCuenta.montoBruto = 0.00
        clienteCuenta.montoDescuento = 0.00
        clienteCuenta.montoImpuesto = 0.00
        clienteCuenta.montoNeto = 0.00

        clienteCuentaStatic = clienteCuenta
        clienteCuenta.save(flush: true, failOnError: true)


        def listaPlatos = Plato.list()
        [listaPlatos:listaPlatos]

    }

    def nuevaOrdenDetalle(){

        //ORDEN DETALLE

        def ordenDetalle = OrdenDetalle.newInstance()
        ordenDetalle.clienteCuenta=clienteCuentaStatic
        ordenDetalle.plato=Plato.findById(params.get("idPlato"))
        ordenDetalle.cantidad=ordenDetalle.cantidad+1
        ordenDetalle.nombrePlato=Plato.findById(params.get("idPlato")).nombre

        //????
        ordenDetalle.precio = 0.00
        ordenDetalle.importe = 0.00
        ordenDetalle.porcientoImpuesto = 0.00
        ordenDetalle.porcientoDescuento = 0.00
        ordenDetalle.montoBruto = 0.00
        ordenDetalle.montoDescuento = 0.00
        ordenDetalle.montoImpuesto = 0.00
        ordenDetalle.montoNeto = 0.00

        ordenDetalle.save(flush: true, failOnError: true)


      render ordenDetalle as JSON

    }

    def cuentaAgregarFinalizar(){
        clienteCuentaStatic = null
//        cuentaStatic =new Cuenta()
    }

}
