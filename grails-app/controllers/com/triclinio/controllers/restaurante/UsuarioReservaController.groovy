package com.triclinio.controllers.restaurante

import com.triclinio.domains.restaurante.Reserva
import com.triclinio.domains.restaurante.UsuarioReserva
import com.triclinio.domains.seguridad.Usuario
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@Secured(["ROLE_ADMIN", "ROLE_CAMARERO", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
class UsuarioReservaController {
    def springSecurityService

    //get current user camarero, get pending reservations assigned and list them. When selected, open an account.

    def index() {
        def user = (Usuario) springSecurityService.currentUser
        def res_pendientes = []

        UsuarioReserva.findAllByUsuario(user).each {
            if (it.reservacion.estado == Reserva.PENDIENTE) {
                res_pendientes.add(it.reservacion)
            }
        }
        ['pendientes': res_pendientes]
    }

    def abrirCuenta(Reserva reserva) {
        if (reserva == null) {
            return
        }

        try {
            reserva.estado = Reserva.APROBADA
            reserva.save(flush: true, failOnError: true)
        } catch (ValidationException e) {

        }

        redirect(controller: 'cuenta' , action: "nuevaCuenta", method: 'GET')
    }
}
