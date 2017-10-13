package com.triclinio.controllers.restaurante

import com.triclinio.domains.seguridad.Perfil
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.seguridad.UsuarioPerfil
import grails.plugin.springsecurity.annotation.Secured


@Secured(["ROLE_ADMIN"])
class UsuarioCrearController {

    def index() {
        def usuarios=Usuario.list()

        [listaUsuarios:usuarios]
    }

    def crearNuevoUsuario(){}

    def nuevoUsuario(){

        Usuario usuario=new Usuario()

        def username=params.get("username")
        def password=params.get("passwordUser")
        def nombre=params.get("nombreUser")

        println "Dusk :"+username

        usuario.setNombre(nombre as String)
        usuario.setUsername(username as String)
        usuario.setPassword(password as String)

        usuario.save(flush:true,failOnError:true)
        UsuarioPerfil.create(usuario,Perfil.findById(1))

        redirect(uri: "/usuarioCrear/index")

    }
}
