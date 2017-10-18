package com.triclinio.domains.restaurante

class EstadoMesa {

    final static int OCUPADA = 1000
    final static int DISPONIBLE = 1001
    final static int AVERIADA = 1002
    final static int DESACTIVADA = 1003

    int codigo
    String nombre;

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;


    static constraints = {
    }

    static mapping = {
        table 'rest_estado_mesa'
    }
}