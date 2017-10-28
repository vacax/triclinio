package com.triclinio.commands.restaurant

import grails.validation.Validateable

class UpdateOrdenDetalleCuenta implements Validateable {

    long clienteCuentaId
    String comentario
    List<OrdenDetallePlatoForm> listaPlato


    static constraints = {
        comentario(nullable:true)
    }
}
