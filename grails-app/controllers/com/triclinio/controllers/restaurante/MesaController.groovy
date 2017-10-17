package com.triclinio.controllers.restaurante

import com.triclinio.domains.restaurante.Cuenta
import com.triclinio.domains.restaurante.CuentaMesa
import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.EstadoMesa
import com.triclinio.domains.restaurante.HistorialMesa
import com.triclinio.domains.restaurante.Mesa
import com.triclinio.domains.seguridad.Usuario
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class MesaController {
    def springSecurityService


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
    def habilitarMesa(long idMesa){
        mesaService.habilitarMesa(idMesa)

        redirect(action:"mesasOcupadasIndex")

    }

    /**
     * PROCESO QUE HABILITA LA MESA (ESTADOMESA = DISPONIBLE)
     * @param idMesa
     * @return
     */
    def habilitarMesa2(long idMesa){
        mesaService.habilitarMesa(idMesa)

        redirect(action:"mesasDesactivarActivarIndex")

    }

    /**
     * PROCESO QUE DESACTIVA LA MESA (ESTADOMESA = DESACTIVADA)
     * @param idMesa
     * @return
     */

    def desactivarMesa(long idMesa){
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

        def listadoMesasHabilitadas = new HashSet()

        listadoMesas.each {
            if(it.habilitado){
                listadoMesasHabilitadas.add(it)
            }

        }
        [listadoMesas: listadoMesasHabilitadas, cuenta: cuenta]

    }

    def procesarSacarMesaCuenta(long idCuenta, long idMesa){

        def mesa = Mesa.get(idMesa)
        def cuenta = Cuenta.get(idCuenta)
        def cuentaMesa = CuentaMesa.findByMesa(mesa)
        cuentaMesa.habilitado=false
        cuentaMesa.mesa.estadoMesa=EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE)
        cuentaMesa.save(flush:true,failOnError:true)

        mesa.historial.add(new HistorialMesa(usuario:(Usuario)springSecurityService.currentUser, descripcion: "Mesa sacada de cuenta :"+cuenta.id, fecha: new Date() ))
        mesa.save(flush:true,failOnError:true)




        redirect(action: "sacarMesaCuenta" ,params:[idCuenta:cuenta.id])

    }


    def historialMesaIndex(){

        [mesas:Mesa.list()]
    }

    def mesaHistorial(long idMesa){
        def mesa = Mesa.get(idMesa)
        [notificaciones:mesa.historial]
    }

}
