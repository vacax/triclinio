package com.triclinio.domains.restaurante

class OrdenDetalle {

    ClienteCuenta clienteCuenta
    Plato plato
    int cantidad=0
    String nombrePlato
    boolean activa = true
    boolean impreso =false

    boolean facturada=false
    boolean eliminada=false

    //comentario sobre el plato, si existen excepciones
    String comentario

    //Precio del articulo
    BigDecimal precio
    //Cantidad por precio.
    BigDecimal importe
    //Indica el porciento de impuestos en Erredé es 18%, NA
    BigDecimal porcientoImpuesto
    //indica el porciento del descuento. No
    BigDecimal porcientoDescuento
    //Importe - el descuento.
    BigDecimal montoBruto
    //El porciento aplicado al importe. ejemplo: importe es 100, es descuento 10%, montodescuento =10
    BigDecimal montoDescuento
    //montoBruto * porcientoImporte.
    BigDecimal montoImpuesto
    //montoBruto + montoImpuesto.
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
