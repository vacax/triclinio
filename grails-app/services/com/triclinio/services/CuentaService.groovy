package com.triclinio.services

import grails.gorm.transactions.Transactional

@Transactional
class CuentaService {

    def serviceMethod() {
        println("Hola Mundo Services")
    }
}
