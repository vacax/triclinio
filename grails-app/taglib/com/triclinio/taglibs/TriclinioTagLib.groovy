package com.triclinio.taglibs

import com.triclinio.domains.seguridad.Usuario

class TriclinioTagLib {

    static namespace = "triclinio"
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    def springSecurityService

    def nombreUsuario={
       Usuario usuario = (Usuario)springSecurityService.currentUser
        out << "${usuario.nombre}"
    }

}
