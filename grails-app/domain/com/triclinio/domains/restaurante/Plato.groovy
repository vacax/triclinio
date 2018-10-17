package com.triclinio.domains.restaurante

class Plato {

    String nombre
    String alias
    BigDecimal precio
    boolean comanda
    CategoriaPlato categoriaPlato
    static fetchMode = [categoriaPlato: 'lazy']
    PlatoTanda platoTanda
    boolean prefix = false
    

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;


    static constraints = {
        alias(nullable: true)
        platoTanda(nullable: true)
        prefix(nullable:true)
    }

    static mapping = {
        table 'rest_plato'
    }
}
