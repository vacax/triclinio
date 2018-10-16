package com.triclinio.controllers.restaurante

import antlr.collections.List
import com.triclinio.domains.seguridad.Perfil
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.seguridad.UsuarioPerfil
import grails.plugin.springsecurity.annotation.Secured


@Secured(["ROLE_ADMIN", "ROLE_CAMARERO", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
class UsuarioCrearController {

    def index() {
        def usuarios = Usuario.list()
        [listaUsuarios: usuarios]
    }

    def crearNuevoUsuario() {
        [roles: Perfil.list()]
    }

    def nuevoUsuario() {
        String[] roles = params.get("SeleccionarRol")
        Usuario usuario = new Usuario()

        def username = params.get("username")
        def password = params.get("passwordUser")
        def nombre = params.get("nombreUser")

        usuario.setNombre(nombre as String)
        usuario.setUsername(username as String)
        usuario.setPassword(password as String)

        usuario.save(flush: true, failOnError: true)

        if (roles.size() >= 2 && roles.size() <= 5) {
            for (int i = 0; i < roles.size(); i++) {
                println "Roles a cada posicion " + roles[i]
                new UsuarioPerfil(usuario: usuario, perfil: Perfil.findByAuthority(roles[i])).save(flush: true, failOnError: true)
            }
        } else if (roles.size() > 5) {
            StringBuilder builder = new StringBuilder();
            for (String value : roles) {
                builder.append(value);
            }
            String text = builder.toString();
            new UsuarioPerfil(usuario: usuario, perfil: Perfil.findByAuthority(text)).save(flush: true, failOnError: true)
        }
        redirect(uri: "/usuarioCrear/index")
    }


    def verUsuario() {
        def idUsuario = params.get("id")
        Usuario usuario = Usuario.findById(idUsuario as Long)

        def perfiles = UsuarioPerfil.findAllByUsuario(usuario).perfil


        [usuario: usuario, listaPerfil: perfiles]
    }

    def modificarUser() {
        def idUsuario = params.get("id")

        println "Param" + idUsuario
        Usuario usuario = Usuario.findById(idUsuario as Long)
        def perfiles = UsuarioPerfil.findAllByUsuario(usuario).perfil

        [usuario: usuario, usuarioPerfil: perfiles.get(0)]
    }
}
