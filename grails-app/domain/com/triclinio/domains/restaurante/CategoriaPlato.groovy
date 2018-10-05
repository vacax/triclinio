package com.triclinio.domains.restaurante

class CategoriaPlato {

    String nombre;

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated

    String toString(){
        return nombre
    }

    static constraints = {
    }

    static mapping = {
        table "rest_categoria_plato"
    }
}
