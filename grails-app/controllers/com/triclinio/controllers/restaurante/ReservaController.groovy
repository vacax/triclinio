package com.triclinio.controllers.restaurante

import com.triclinio.domains.restaurante.Reserva
import com.triclinio.domains.restaurante.UsuarioReserva
import com.triclinio.domains.seguridad.Perfil
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.seguridad.UsuarioPerfil
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@Secured(["ROLE_ADMIN", "ROLE_CAMARERO", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
class ReservaController {

    def index() {
        def camareros = []
        def l = UsuarioPerfil.findAllByPerfil(Perfil.findAllByAuthority('ROLE_CAMARERO'))
        l.each{ camareros.add(it.usuario) }
        ['reservas': Reserva.findAllByEstadoNotEqual(1004), 'camareros': camareros]
    }

    def crear() {
        respond new Reserva(params)
    }

    def save(Reserva reserva) {

        if (reserva == null) {
            notFound()
            return
        }

        try {
            reserva.estado = Reserva.ACTIVO
            reserva.save()
        } catch (ValidationException e) {
            respond reserva.errors, view: 'crear'
            return
        }

        redirect action: "index", method: "GET"
    }

    def aprobar() {
        def reserva = Reserva.findById(params.reserv as Long)
        def camarero = Usuario.findById(params.camarero as Long)

        try {
            reserva.estado = Reserva.PENDIENTE
            reserva.save(flush: true, failOnError: true)
            new UsuarioReserva(usuario: camarero, reservacion: reserva).save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            println('error')
            respond reserva.errors, view: 'index'
            return
        }

        redirect action: "index", method: "GET"
    }

    def cancelar(Reserva reserva) {

        if (reserva == null) {
            notFound()
            return
        }

        try {
            reserva.estado = Reserva.CANCELADA
            reserva.save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            println('error')
            respond reserva.errors, view: 'index'
            return
        }

        redirect action: "index", method: "GET"
    }

    def activar(Reserva reserva) {
        if (reserva == null) {
            notFound()
            return
        }

        try {
            reserva.estado = Reserva.ACTIVO
            reserva.save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            println('error')
            respond reserva.errors, view: 'index'
            return
        }

        redirect action: "index", method: "GET"
    }

    def historial() {
        ['reservas': Reserva.list()]
    }


    protected void notFound() {
        request.withFormat {
            '*' { render status: NOT_FOUND }
        }
    }
}
