package com.triclinio.services

import com.triclinio.commands.restaurant.OrdenDetalleCuentaForm
import com.triclinio.domains.restaurante.ClienteCuenta
import com.triclinio.domains.restaurante.Cuenta
import grails.gorm.transactions.Transactional

@Transactional
class ClienteCuentaService {

    def procesarClienteCuenta(OrdenDetalleCuentaForm form){
        ClienteCuenta clienteCuenta = new ClienteCuenta()
        clienteCuenta.cuenta = Cuenta.get(form.cuentaId)
        clienteCuenta.nombre = form.nombreCliente ? form.nombreCliente : "Cliente Generico"
        clienteCuenta.comentario = form.comentario
        clienteCuenta.cantidadPersonas = form.cantidadPersonas ? form.cantidadPersonas : 1

        clienteCuenta.save(flush: true, failOnError: true)
    }
}
