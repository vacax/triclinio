package com.triclinio.commands.restaurant

import grails.validation.Validateable

class OrdenDetalleCuentaForm implements Validateable {

    long cuentaId
    String nombreCliente
    List<OrdenDetallePlatoForm> listaPlato


    static constraints = {
       nombreCliente(nullable: true)
    }
}
