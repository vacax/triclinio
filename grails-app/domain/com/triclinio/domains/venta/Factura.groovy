package com.triclinio.domains.venta

import com.triclinio.domains.cxc.Cliente
import com.triclinio.domains.seguridad.Usuario

class Factura {

    Cliente cliente
    Usuario usuario

    EstadoFactura estadoFactura

    static hasMany = [listaFacturaDetalle : FacturaDetalle]

    //Datos dinero
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
        porcientoImpuesto(nullable: true)
        porcientoDescuento(nullable: true)
        montoBruto(nullable: true)
        montoDescuento(nullable: true)
        montoImpuesto(nullable: true)
        montoNeto(nullable: true)
        cliente(nullable: true)
        usuario(nullable: true)
        estadoFactura(nullable: true)
    }

    static mapping = {
        table 'ven_factura'
    }
}
