package com.triclinio.controllers.restaurante

import com.triclinio.domains.restaurante.OrdenDetalle
import grails.plugin.springsecurity.annotation.Secured


@Secured(["ROLE_ADMIN"])
class OrdenesDetalleController {

    def index() {
        def listaOrdenDetalleTotal=OrdenDetalle.list()
        def listaOrdenDetalle=new ArrayList()

        for (OrdenDetalle ordenDetalle: listaOrdenDetalleTotal){
            if(ordenDetalle.clienteCuenta.cuenta.estadoCuenta.codigo==1000){
                listaOrdenDetalle.add(ordenDetalle)
            }
        }
        [listaOrdenDetalle:listaOrdenDetalle]
    }



}
