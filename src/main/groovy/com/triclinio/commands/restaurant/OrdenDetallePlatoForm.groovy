package com.triclinio.commands.restaurant

import grails.validation.Validateable

class OrdenDetallePlatoForm implements Validateable {

    long idPlato
    int cantidad
    String comentario



    static constraints = {
        comentario(nullable:true)
    }

}
