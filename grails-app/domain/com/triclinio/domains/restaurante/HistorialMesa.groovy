package com.triclinio.domains.restaurante

import com.triclinio.domains.seguridad.Usuario

class HistorialMesa {

    Usuario usuario
    String descripcion
    Date fecha

    static constraints = {
    }
}
