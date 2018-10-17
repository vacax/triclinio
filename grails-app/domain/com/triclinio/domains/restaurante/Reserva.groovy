package com.triclinio.domains.restaurante

class Reserva {

    public static int ACTIVO = 1001
    public static int CANCELADA = 1002;
    public static int APROBADA = 1003;
    public static int PENDIENTE = 1004;

    String aNombreDe;
    Integer cantidadPersonas;
    Date fecha;
    int estado;
    String observaciones = " "

    Date dateCreated;
    Date lastUpdated;

    static constraints = {
        observaciones nullable: true
    }

    static mapping = {
        table 'rest_reserva'
        estado defaultValue: 1001
        observaciones defaultValue: " "
    }

}
