package com.triclinio.controllers.restaurante

import com.triclinio.domains.restaurante.Reserva
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN", "ROLE_CAMARERO", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
class ReservaController {

    def index() {
        def reservas = Reserva.list()
        ['reservas' : reservas]
    }

    def crear(){
        respond new Reserva(params)
    }

}
