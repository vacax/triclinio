package com.triclinio.domains.restaurante

class OrdenDetalle {

    ClienteCuenta clienteCuenta
    Plato plato
    int cantidad
    String nombrePlato

    //
    BigDecimal precio
    BigDecimal importe
    BigDecimal porcientoImpuesto
    BigDecimal porcientoDescuento
    BigDecimal montoBruto
    BigDecimal montoDescuento
    BigDecimal montoImpuesto
    BigDecimal montoNeto

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;


    static constraints = {
    }

    static mapping = {
        table 'rest_orden_detalle'
    }
}
