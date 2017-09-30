package com.triclinio.domains.venta

class EstadoFactura {

    final static int PREFACTURA = 1000
    final static int FACTUDA = 1001
    final static int FACTURADA_COBRADA = 1002

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

    static mapping = {
        table 'ven_estado_factura'
    }
}
