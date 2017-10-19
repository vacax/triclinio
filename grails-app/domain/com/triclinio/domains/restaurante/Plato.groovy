package com.triclinio.domains.restaurante

class Plato {

    String nombre
    BigDecimal precio
    boolean comanda
    

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;


    static constraints = {
    }

    static mapping = {
        table 'rest_plato'
    }
}
