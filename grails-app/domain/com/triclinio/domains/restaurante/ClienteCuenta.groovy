package com.triclinio.domains.restaurante

class ClienteCuenta {

    Cuenta cuenta
    String nombre

    static hasMany = [listaOrdenDetalle : OrdenDetalle]

    //
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
        table 'rest_cliente_cuenta'
        listaOrdenDetalle(lazy: false)
    }
}
