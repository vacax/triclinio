package com.triclinio.controllers.restaurante

import com.triclinio.domains.restaurante.Cuenta
import com.triclinio.domains.restaurante.CuentaMesa
import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.EstadoMesa
import com.triclinio.domains.restaurante.Mesa
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class MesaController {

    def mesaService

    def mesasOcupadasIndex(){
        def mesasOcupadas = Mesa.findAllByEstadoMesa(EstadoMesa.findByCodigo(EstadoMesa.OCUPADA))
        def listadoMesasNoCandidatasDesocupar = new ArrayList()
        def cuentasAbiertas = Cuenta.findAllByEstadoCuenta(EstadoCuenta.findByCodigo(EstadoCuenta.ABIERTO))

        for(int i=0;i<cuentasAbiertas.size();i++){
            def cuentaMesa = CuentaMesa.findAllByCuenta(cuentasAbiertas.get(i))
            for(int j=0;j<cuentaMesa.size();j++){
                listadoMesasNoCandidatasDesocupar.add(cuentaMesa.get(j).mesa)
            }
        }
        mesasOcupadas.removeAll(listadoMesasNoCandidatasDesocupar)

        [mesasOcupadas: mesasOcupadas]


    }

    def habilitarMesa(Long idMesa){
        mesaService.habilitarMesa(idMesa)
        redirect(action:"mesasOcupadasIndex")

    }

    def desactivarMesa(Long idMesa){
        mesaService.desactivarMesa(idMesa)
        redirect(action:"mesasDesactivarActivarIndex")

    }

    def mesasDesactivarActivarIndex(){
        def listadoMesas = Mesa.findAllByEstadoMesa(EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE))
        listadoMesas.addAll(Mesa.findAllByEstadoMesa(EstadoMesa.findByCodigo(EstadoMesa.DESACTIVADA)))
        [listadoMesas: listadoMesas]
    }

}
