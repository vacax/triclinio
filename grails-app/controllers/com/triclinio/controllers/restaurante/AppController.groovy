package com.triclinio.controllers.restaurante

import grails.plugin.springsecurity.annotation.Secured

@Secured(["IS_AUTHENTICATED_REMEMBERED"])
class AppController {

    def springSecurityService;

    def index() {
        println("entro al index....")
        render(view: "/index", model: [])
    }
}
