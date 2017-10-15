package com.triclinio.controllers.restaurante

import com.triclinio.commands.PlatoForm
import com.triclinio.domains.restaurante.Plato
import com.triclinio.domains.seguridad.Perfil
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.seguridad.UsuarioPerfil
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class PlatoCrearController {


    def index() {
        def platos=Plato.findAllByHabilitado(true)

        [platos:platos]
    }

    def crearNuevoPlato(){}

    def nuevoPlato(){

        Plato plato=new Plato()

        def nombre=params.get("nombrePlato")
        def precio=params.get("precioPlato")


        plato.setNombre(nombre as String)
        plato.setPrecio(precio as BigDecimal)
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
        def idPlato=params.get("idPlato")
        def plato=Plato.findById(idPlato as Long)
    }
}
