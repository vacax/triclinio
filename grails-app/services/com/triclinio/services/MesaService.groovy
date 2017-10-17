package com.triclinio.services

import com.triclinio.domains.restaurante.EstadoMesa
import com.triclinio.domains.restaurante.HistorialMesa
import com.triclinio.domains.restaurante.Mesa
import com.triclinio.domains.seguridad.Usuario
import grails.gorm.transactions.Transactional

@Transactional
class MesaService {

   def springSecurityService


   def desactivarMesa(long idMesa){
      def mesa = Mesa.findById(idMesa)
      mesa.estadoMesa=EstadoMesa.findByCodigo(EstadoMesa.DESACTIVADA)
      mesa.historial.add(new HistorialMesa(usuario:(Usuario)springSecurityService.currentUser, descripcion: "Estado de la muestra actualizado a desactivada", fecha: new Date() ))
      mesa.save(flush: true, failOnError: true)
   }

   def habilitarMesa(long idMesa){
      def mesa = Mesa.findById(idMesa as Long)
      mesa.estadoMesa=EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE)
      mesa.historial.add(new HistorialMesa(usuario:(Usuario)springSecurityService.currentUser, descripcion: "Estado de la muestra actualizado a disponible", fecha: new Date() ))
      mesa.save(flush: true, failOnError: true)
   }
}
