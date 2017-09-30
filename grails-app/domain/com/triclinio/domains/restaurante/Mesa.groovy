package com.triclinio.domains.restaurante

class Mesa {

    int numeroMesa
    String nombre;
    EstadoMesa estadoMesa
    

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;


    static constraints = {
    }

    static mapping = {
        table 'rest_mesa'
    }
}
