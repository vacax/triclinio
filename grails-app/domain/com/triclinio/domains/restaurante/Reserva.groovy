package com.triclinio.domains.restaurante

class Reserva {

    String aNombreDe;
    Integer cantidadPersonas;
    Date fecha;
    Date hora;
    boolean utilizada;
    boolean activa;

    Date dateCreated;
    Date lastUpdated;

    static constraints = {
    }

    static mapping = {
        table 'rest_reserva'
        utilizada defaultValue: false
        activa defaultValue: true
    }

}
