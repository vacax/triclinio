package com.triclinio.domains.restaurante

import com.triclinio.domains.seguridad.Usuario

class UsuarioReserva {

    Usuario usuario
    Reserva reservacion

    Date dateCreated;
    Date lastUpdated;

    static constraints = {

    }
}
