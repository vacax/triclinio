package com.triclinio.services

import com.triclinio.domains.configuracion.Secuencia
import com.triclinio.encapsulaciones.NombreSecuencia
import grails.gorm.transactions.Transactional

@Transactional
class ConfiguracionService {

    /**
     * 
     * @return
     */
    long generarSecuenciaFactura(){
        def secuencia = Secuencia.findByNombreSecuenciaAndHabilitado(NombreSecuencia.FACTURACION, true)
        secuencia.numeroSecuencia++
        secuencia.save(flush: true, failOnError: true)
        return secuencia.numeroSecuencia;
    }
}
