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
    final static int TICKET_ENCABEZADO_4 = 1008
    final static int TICKET_PIEPAG_1 = 1008
    final static int TICKET_PIEPAG_2 = 1009

    final static int CANTIDAD_COMANDA = 1010
    final static int CANTIDAD_FACTURA = 1012
    final static int CANTIDAD_PRECUENTA = 1013
/*
    final static int CANTIDAD_IMPRESION_FACTURA= 1014
*/
    //TICKET COMANDA
    final static int COMANDA_ENCABEZADO_3 = 1010
    final static int PRECUENTA_ENCABEZADO_3 = 1011

    //Imprimir JMS
    final static int JMS_HABILITAR = 1014

    final static int TANDADIA = 1015
    final static int PREFIX = 1016

    int codigo
    String nombre
    String valor

    static constraints = {
    }

    static mapping = {
        table "conf_parametro"
    }
}
