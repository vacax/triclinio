package com.triclinio.commands

import grails.validation.Validateable

class PlatoForm implements Validateable{

    String nombre
    BigDecimal precio

    static constraints = {
        
    }

}
