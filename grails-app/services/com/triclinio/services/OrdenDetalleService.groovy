package com.triclinio.services

import com.triclinio.commands.restaurant.OrdenDetalleCuentaForm
import com.triclinio.commands.restaurant.UpdateOrdenDetalleCuenta
import com.triclinio.domains.restaurante.ClienteCuenta
import com.triclinio.domains.restaurante.OrdenDetalle
import com.triclinio.domains.restaurante.Plato
import grails.gorm.transactions.Transactional

@Transactional
class OrdenDetalleService {

    def procesarOrdenDetalle(OrdenDetalleCuentaForm form, ClienteCuenta clienteCuenta){

        form.listaPlato.each {
            Plato plato = Plato.get(it.idPlato)
            int cantidad = it.cantidad;

            def ordenDetalle = new OrdenDetalle()
            ordenDetalle.clienteCuenta=clienteCuenta
            ordenDetalle.plato=plato
            ordenDetalle.cantidad=it.cantidad
            ordenDetalle.nombrePlato=plato.nombre

            //calculo de dinero
            ordenDetalle.precio = plato.precio
            ordenDetalle.importe = cantidad * ordenDetalle.precio
            ordenDetalle.porcientoImpuesto = 0.00
            ordenDetalle.porcientoDescuento = 0.00
            ordenDetalle.montoDescuento = ordenDetalle.importe * ordenDetalle.porcientoDescuento
            ordenDetalle.montoBruto = ordenDetalle.importe - ordenDetalle.montoDescuento
            ordenDetalle.montoImpuesto = ordenDetalle.montoBruto * ordenDetalle.porcientoImpuesto
            ordenDetalle.montoNeto = ordenDetalle.montoBruto + ordenDetalle.montoImpuesto

            ordenDetalle.save(flush: true, failOnError: true)
        }
    }
    def updateProcesarOrdenDetalle(UpdateOrdenDetalleCuenta form, ClienteCuenta clienteCuenta){

        form.listaPlato.each {
            Plato plato = Plato.get(it.idPlato)
            int cantidad = it.cantidad;

            def ordenDetalle = new OrdenDetalle()
            ordenDetalle.clienteCuenta=clienteCuenta
            ordenDetalle.plato=plato
            ordenDetalle.cantidad=it.cantidad
            ordenDetalle.nombrePlato=plato.nombre

            //calculo de dinero
            ordenDetalle.precio = plato.precio
            ordenDetalle.importe = cantidad * ordenDetalle.precio
            ordenDetalle.porcientoImpuesto = 0.00
            ordenDetalle.porcientoDescuento = 0.00
            ordenDetalle.montoDescuento = ordenDetalle.importe * ordenDetalle.porcientoDescuento
            ordenDetalle.montoBruto = ordenDetalle.importe - ordenDetalle.montoDescuento
            ordenDetalle.montoImpuesto = ordenDetalle.montoBruto * ordenDetalle.porcientoImpuesto
            ordenDetalle.montoNeto = ordenDetalle.montoBruto + ordenDetalle.montoImpuesto

            ordenDetalle.save(flush: true, failOnError: true)
        }
    }
}
