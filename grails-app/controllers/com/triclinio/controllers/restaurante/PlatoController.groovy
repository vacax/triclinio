package com.triclinio.controllers.restaurante

import com.triclinio.commands.PlatoForm
import com.triclinio.domains.restaurante.Plato
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class PlatoController {



    def index() { }

    def prueba(PlatoForm platoForm){
       render "el plato: "+platoForm.properties
    }
}
