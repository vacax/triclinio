package com.triclinio.domains.configuracion

class Parametro {

    final static int JMS_URL = 1000
    final static int JMS_USUARIO = 1001
    final static int JMS_PASSWORD = 1002
    final static int JMS_COLA = 1003

    final static int APP_NOMBRE_RESTAURANTE = 1004

    final static int TICKET_ENCABEZADO_1 = 1005
    final static int TICKET_ENCABEZADO_2 = 1006
    final static int TICKET_ENCABEZADO_3 = 1007
    final static int TICKET_PIEPAG_1 = 1008
    final static int TICKET_PIEPAG_2 = 1009



    //TICKET COMANDA
    final static int COMANDA_ENCABEZADO_3 = 1010




    int codigo
    String nombre
    String valor

    static constraints = {
    }

    static mapping = {
        table "conf_parametro"
    }
}
