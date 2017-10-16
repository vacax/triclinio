package com.triclinio.domains.restaurante

import com.triclinio.domains.seguridad.Usuario

class Cuenta {

    static hasMany = [listaMesa :  CuentaMesa, listaClienteCuenta: ClienteCuenta]

    Usuario usuario
    EstadoCuenta estadoCuenta

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;

    static constraints = {

    }

    static mapping = {
        cascade: 'none'
        table 'rest_cuenta'
    }
}
