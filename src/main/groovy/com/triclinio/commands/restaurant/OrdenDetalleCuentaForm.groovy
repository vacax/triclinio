package com.triclinio.commands.restaurant

import grails.validation.Validateable

class OrdenDetalleCuentaForm implements Validateable {

    long cuentaId
    String nombreCliente
    String comentario
    Integer cantidadPersonas
    List<OrdenDetallePlatoForm> listaPlato


    static constraints = {
        nombreCliente(nullable: true)
        comentario(nullable: true)
    }
}
