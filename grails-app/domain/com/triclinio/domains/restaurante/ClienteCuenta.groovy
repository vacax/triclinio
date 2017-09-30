package com.triclinio.domains.restaurante

class ClienteCuenta {

    Cuenta cuenta
    String nombre

    static hasMany = [listaOrdenes : Orden]

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;


    static constraints = {
    }

    static mapping = {
        table 'rest_cliente_cuenta'
    }
}
