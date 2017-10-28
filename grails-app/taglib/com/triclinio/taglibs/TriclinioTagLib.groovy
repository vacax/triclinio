package com.triclinio.taglibs

import com.triclinio.domains.restaurante.ClienteCuenta
import com.triclinio.domains.restaurante.Cuenta
import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.OrdenDetalle
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.venta.EstadoFactura
import com.triclinio.domains.venta.Factura
import com.triclinio.domains.venta.FacturaDetalle

class TriclinioTagLib {

    static namespace = "triclinio"
    static defaultEncodeAs = [taglib:'html']
    static encodeAsForTags = [botonPagar: [taglib:'none']]

    def springSecurityService

    def nombreUsuario={
       Usuario usuario = (Usuario)springSecurityService.currentUser
        out << "${usuario?.nombre}"
    }

    def numeroCuentasAbiertas={
        def numeroCuentasAbiertas = Cuenta.findAllByHabilitadoAndEstadoCuenta(true,EstadoCuenta.findByCodigo(EstadoCuenta.ABIERTO))
        out << "${numeroCuentasAbiertas?.size()}"
    }

    /**
     * @param attr.cuentaClienteId
     */
    def botonPagar= { attrs, body ->

        println("Cliente cuenta: ${attrs.cuentaClienteId}")
        ClienteCuenta clienteCuenta = ClienteCuenta.get(attrs.cuentaClienteId)
        println("La cuenta: " + clienteCuenta?.properties)
        if (!clienteCuenta) {
            return
        }


        OrdenDetalle ordenDetalle = OrdenDetalle.findByClienteCuentaAndHabilitado(clienteCuenta, true)
        Factura factura = FacturaDetalle.findByOrdenDetalle(ordenDetalle)?.factura
        if (factura && factura.estadoFactura.codigo == EstadoFactura.FACTURADA) {
            out << g.link(class: "btn btn-info", controller: "facturaDetalle", action: "imprimir", params: [idFactura: factura.id]) {
                "Pagar"
            }
        }
    }
}
