package com.triclinio.controllers.restaurante

import com.triclinio.domains.restaurante.Reserva
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import groovy.time.TimeCategory

@Secured(["ROLE_ADMIN", "ROLE_CAMARERO", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
class ReservaController {

    def index() {
        ['reservas': reservas]
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

    def aprobar(Reserva reserva) {
        if (reserva == null) {
            notFound()
            return
        }

        try {
            reserva.estado = Reserva.APROBADA
            reserva.save(flush: true, failOnError: true)
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
