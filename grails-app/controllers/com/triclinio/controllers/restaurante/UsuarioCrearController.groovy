package com.triclinio.controllers.restaurante

import antlr.collections.List
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
        String[] roles=params.get("SeleccionarRol")
        println roles.size()
        println roles.toList().get(1)

        Usuario usuario=new Usuario()

        def username=params.get("username")
        def password=params.get("passwordUser")
        def nombre=params.get("nombreUser")


        usuario.setNombre(nombre as String)
        usuario.setUsername(username as String)
        usuario.setPassword(password as String)

        usuario.save(flush:true,failOnError:true)

        for(int i=0;i<roles.toList().size();i++){
            println "Roles a cada posicion "+roles[i]
            new UsuarioPerfil(usuario: usuario, perfil: Perfil.findByAuthority(roles[i])).save(flush: true, failOnError: true)
        }
        redirect(uri: "/usuarioCrear/index")
    }

    def verUsuario(){
        def idUsuario=params.get("id")
        Usuario usuario=Usuario.findById(idUsuario as Long)

        [usuario:usuario]
    }

    def modificarUser(){
        def idUsuario=params.get("id")

        println "Param"+ idUsuario
        Usuario usuario=Usuario.findById(idUsuario as Long)

        [usuario: usuario]
    }
}
