package com.triclinio.domains.restaurante

class ClienteCuenta {

    Cuenta cuenta
    String nombre = "Cliente Generico"
    String comentario;


    static hasMany = [listaOrdenDetalle : OrdenDetalle]

    //
    BigDecimal porcientoImpuesto = BigDecimal.ZERO
    BigDecimal porcientoDescuento = BigDecimal.ZERO
    BigDecimal montoBruto = BigDecimal.ZERO
    BigDecimal montoDescuento = BigDecimal.ZERO
    BigDecimal montoImpuesto = BigDecimal.ZERO
    BigDecimal montoNeto  = BigDecimal.ZERO

    //Datos genericos del dominio.
    boolean habilitado = true;
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;


    static constraints = {
        comentario(nullable:true)

    }

    static mapping = {
        table 'rest_cliente_cuenta'
        listaOrdenDetalle(lazy: false)
    }
}
