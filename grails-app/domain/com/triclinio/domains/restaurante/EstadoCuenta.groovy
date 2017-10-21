package com.triclinio.domains.restaurante

class EstadoCuenta {

    final static int ABIERTO = 1000
    final static int CERRADA = 1001
    final static int ELIMINADA = 1003
    
    int codigo
    String nombre

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;

    static constraints = {
    }

    static mapping = {
        table 'rest_estado_cuenta'
    }
}
