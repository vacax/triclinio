package com.triclinio.controllers.restaurante

import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.OrdenDetalle
import grails.plugin.springsecurity.annotation.Secured


@Secured(["ROLE_ADMIN"])
class OrdenesDetalleController {

//    def index() {
//        def listaOrdenDetalleTotal=OrdenDetalle.list()
//        def listaOrdenDetalle=new ArrayList()
//
//        for (OrdenDetalle ordenDetalle: listaOrdenDetalleTotal){
//            if(ordenDetalle.getClienteCuenta().cuenta.estadoCuenta.codigo==1000 && ordenDetalle.getHabilitado())
//                listaOrdenDetalle.add(ordenDetalle)
//        }
//
//        [listaOrdenDetalle:listaOrdenDetalle]
//    }
//
//    def eliminarOrdenDetalle()
//    {
//        def orden = OrdenDetalle.findById(params.get("idOrden") as Long)
//        orden.setHabilitado(false)
//        orden.clienteCuenta.cuenta.setEstadoCuenta(EstadoCuenta.findById(2))
//        orden.save(flush: true, failOnError: true)
//        render orden.id
//    }

}
