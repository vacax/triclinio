package com.triclinio.domains.restaurante

class PlatoTanda {
    final static int DIA = 1000
    final static int NOCHE = 1001
    final static int TODOELDIA = 1002


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
}
