package com.triclinio.encapsulaciones

import groovy.transform.CompileStatic

@CompileStatic
enum NombreSecuencia {

    FACTURACION(0)

    final int id
    private NombreSecuencia(int id) { this.id = id }

}