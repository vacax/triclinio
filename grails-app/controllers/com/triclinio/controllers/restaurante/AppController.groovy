package com.triclinio.controllers.restaurante

import com.triclinio.domains.configuracion.Parametro
import com.triclinio.domains.restaurante.Plato
import com.triclinio.domains.restaurante.PlatoTanda
import grails.plugin.springsecurity.annotation.Secured

@Secured(["IS_AUTHENTICATED_REMEMBERED"])
class AppController {

    def springSecurityService;

    def index() {
        println("entro al index....")
        render(view: "/index", model:[tandaPlatos: Parametro.findByCodigo(Parametro.TANDADIA)])
    }

    def tandaPlatos(){
        Parametro parametro =Parametro.findByCodigo(Parametro.TANDADIA)
        println(parametro.valor)
        if(parametro.valor=="1"){
            parametro.valor="0"
            parametro.save(flush:true,failOnError:true)
            Plato.findAllByPlatoTanda(PlatoTanda.findByCodigo(PlatoTanda.NOCHE)).each {
                it.habilitado=true
                it.save(flush:true, failOnError:true)
            }
            Plato.findAllByPlatoTanda(PlatoTanda.findByCodigo(PlatoTanda.DIA)).each {
                it.habilitado=false
                it.save(flush:true, failOnError:true)
            }

        }
        else if(parametro.valor=="0"){
            parametro.valor="1"
            parametro.save(flush:true,failOnError:true)
            Plato.findAllByPlatoTanda(PlatoTanda.findByCodigo(PlatoTanda.NOCHE)).each {
                it.habilitado=false
                it.save(flush:true, failOnError:true)
            }
            Plato.findAllByPlatoTanda(PlatoTanda.findByCodigo(PlatoTanda.DIA)).each {
                it.habilitado=true
                it.save(flush:true, failOnError:true)
            }
        }

        redirect(uri: "/")
    }
}
