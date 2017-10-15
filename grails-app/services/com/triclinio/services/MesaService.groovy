package com.triclinio.services

import com.triclinio.domains.restaurante.EstadoMesa
import com.triclinio.domains.restaurante.Mesa
import grails.gorm.transactions.Transactional

@Transactional
class MesaService {

   def desactivarMesa(long idMesa){
      def mesa = Mesa.findById(idMesa)
      mesa.estadoMesa=EstadoMesa.findByCodigo(EstadoMesa.DESACTIVADA)
      mesa.save(flush: true, failOnError: true)
   }

   def habilitarMesa(long idMesa){
      def mesa = Mesa.findById(idMesa)
      mesa.estadoMesa=EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE)
      mesa.save(flush: true, failOnError: true)
   }
}
