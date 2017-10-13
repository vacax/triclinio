package com.triclinio.controllers.restaurante

import grails.plugin.springsecurity.annotation.Secured


@Secured(["ROLE_ADMIN"])
class UsuarioCrearController {

    def index() { }

    def nuevoUsuario(){

    }
}
