package com.triclinio.domains.restaurante

class Plato {

    String nombre
    String alias
    BigDecimal precio
    boolean comanda
    CategoriaPlato categoriaPlato
    
    

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;


    static constraints = {
        alias(nullable: true)
    }

    static mapping = {
        table 'rest_plato'
    }
}
