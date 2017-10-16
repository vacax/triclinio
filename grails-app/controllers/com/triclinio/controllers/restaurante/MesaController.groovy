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

    /**
     * VENTANA QUE MUESTRA LAS MESAS OCUPADAS QUE NO TIENEN CUENTA ABIERTA PARA SER HABILITADAS
     * @return
     */
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

    /**
     * PROCESO QUE HABILITA LA MESA (ESTADOMESA = DISPONIBLE)
     * @param idMesa
     * @return
     */
    def habilitarMesa(Long idMesa){
        mesaService.habilitarMesa(idMesa)
        redirect(action:"mesasOcupadasIndex")

    }

    /**
     * PROCESO QUE DESACTIVA LA MESA (ESTADOMESA = DESACTIVADA)
     * @param idMesa
     * @return
     */

    def desactivarMesa(Long idMesa){
        mesaService.desactivarMesa(idMesa)
        redirect(action:"mesasDesactivarActivarIndex")

    }

    /**
     * VENTANA QUE MUESTRAS LAS MESAS QUE PUEDEN SER DESACTIVADAS
     * @return
     */
    def mesasDesactivarActivarIndex(){
        def listadoMesas = Mesa.findAllByEstadoMesa(EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE))
        listadoMesas.addAll(Mesa.findAllByEstadoMesa(EstadoMesa.findByCodigo(EstadoMesa.DESACTIVADA)))
        [listadoMesas: listadoMesas]
    }



    def sacarMesaCuenta(long idCuenta){
       def cuenta=Cuenta.findById(idCuenta)
        def listadoMesas = cuenta.listaMesa


        [listadoMesas: listadoMesas, cuenta: cuenta]

    }

    def procesarSacarMesaCuenta(long idCuenta, long idMesa){

        def mesa = Mesa.get(idMesa)
        def cuenta = Cuenta.get(idCuenta)


        cuenta.listaMesa.each {
            if(it.mesa.id==mesa.id){
                //TODO PREGUNTAR!!! ???
                it.delete(flush:true)
            }
        }

        redirect(action: "sacarMesaCuenta" ,params:[idCuenta:cuenta.id])

    }

}
