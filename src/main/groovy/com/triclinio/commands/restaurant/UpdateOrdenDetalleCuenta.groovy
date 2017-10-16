package com.triclinio.commands.restaurant

import grails.validation.Validateable

class UpdateOrdenDetalleCuenta implements Validateable {

    long cuentaId
    long clienteId
    List<OrdenDetallePlatoForm> listaPlato


    static constraints = {
    }
}
