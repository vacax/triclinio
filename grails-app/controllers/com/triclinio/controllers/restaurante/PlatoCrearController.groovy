package com.triclinio.controllers.restaurante

import com.triclinio.commands.PlatoForm
import com.triclinio.domains.restaurante.Plato
import com.triclinio.domains.seguridad.Perfil
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.seguridad.UsuarioPerfil
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN", "ROLE_CAMARERO","ROLE_FACTURADOR","ROLE_SUPERVISOR_FACTURADOR","ROLE_SUPERVISOR_CAMARERO"])
class PlatoCrearController {


    def index() {
        def platos=Plato.findAllByHabilitado(true)

        [platos:platos]
    }

    def crearNuevoPlato(){}

    def nuevoPlato(String nombrePlato, String precioPlato, boolean comanda){

        Plato plato=new Plato()

        plato.setNombre(nombrePlato)
        plato.setPrecio(new BigDecimal(precioPlato))
        plato.comanda = comanda
        plato.save(flush:true,failOnError:true)

        redirect(uri: "/platoCrear/index")

    }

    def eliminarPlato(){
        def idPlato=params.get("idPlato")
        def plato=Plato.findById(idPlato as Long)
        plato.setHabilitado(false)
        plato.save(flush:true,failOnError:true)
        render plato.id
    }

    def modificarPlato(){

        def idPlato=params.get("id")
        def plato=Plato.findById(idPlato as Long)
        [plato: plato]
    }

    def modificarPlatoPost(){

        def idPlato=params.get("idPlato")
        def plato=Plato.findById(idPlato as Long)

        plato.nombre=params.get("nombrePlato")
        plato.precio= params.get("precioPlato") as BigDecimal

        plato.save(flush:true, failsOnError:true)

        redirect(action: "index")

    }
}
