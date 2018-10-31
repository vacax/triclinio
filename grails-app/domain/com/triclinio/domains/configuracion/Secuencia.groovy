package com.triclinio.domains.configuracion

import com.triclinio.encapsulaciones.NombreSecuencia

class Secuencia {

    NombreSecuencia nombreSecuencia
    long numeroSecuencia

    //
    boolean habilitado = true
    String creadoPor = "sistemas"
    String modificadoPor = "sistemas"
    Date dateCreated
    Date lastUpdated

    static constraints = {

    }

    static mapping = {
        table("conf_secuencia")
        nombreSecuencia enumType: 'string'
    }
}
