package com.triclinio.domains.venta

import com.triclinio.domains.restaurante.OrdenDetalle

class FacturaDetalle {

    Factura factura
    OrdenDetalle ordenDetalle

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;


    static constraints = {
    }

    static mapping = {
            table 'ven_factura_detalle'
    }
}
