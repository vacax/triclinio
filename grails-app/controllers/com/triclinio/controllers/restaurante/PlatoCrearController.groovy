package com.triclinio.controllers.restaurante

import com.triclinio.commands.PlatoForm
import com.triclinio.domains.restaurante.CategoriaPlato
import com.triclinio.domains.restaurante.Plato
import com.triclinio.domains.restaurante.PlatoTanda
import com.triclinio.domains.seguridad.Perfil
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.seguridad.UsuarioPerfil
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN", "ROLE_CAMARERO","ROLE_FACTURADOR","ROLE_SUPERVISOR_FACTURADOR","ROLE_SUPERVISOR_CAMARERO"])
class PlatoCrearController {


    def index() {
        def platos=Plato.findAllByHabilitado(true)

        [platos:platos]
    }

    def crearNuevoPlato(){
        def lista = CategoriaPlato.findAllByHabilitado(true)
        [listaCategoriaPlato :  lista]
    }

    def nuevoPlato(String nombrePlato, String precioPlato, boolean comanda, long categoriaId, String alias, String tandaId){


        withForm {
            Plato plato=new Plato()

            plato.setNombre(nombrePlato)
            plato.setPrecio(new BigDecimal(precioPlato))
            plato.comanda = comanda
            plato.alias = alias
            plato.categoriaPlato = CategoriaPlato.get(categoriaId)
            plato.platoTanda=PlatoTanda.get(tandaId)
            plato.save(flush:true,failOnError:true)

        }.invalidToken {
            println "Doble Posteo detectado"
        }

        redirect(uri: "/platoCrear/index")

    }

    def eliminarPlato(){
        def idPlato=params.get("idPlato")
        def plato=Plato.findById(idPlato as Long)
        plato.setHabilitado(false)
        plato.save(flush:true,failOnError:true)
        render plato.id
    }

    def modificarPlato(){

        def idPlato=params.get("id")
        def plato=Plato.findById(idPlato as Long)
        [plato: plato, listaCategoriaPlato: CategoriaPlato.findAllByHabilitado(true)]
    }

    /**
     * 
     * @param idPlato
     * @param nombrePlato
     * @param precioPlato
     * @param alias
     * @param comanda
     * @param categoriaId
     * @return
     */
    def modificarPlatoPost(long idPlato, String nombrePlato, String precioPlato, String alias, boolean comanda, long categoriaId){

        withForm {
            def plato=Plato.get(idPlato)

            plato.nombre=nombrePlato
            plato.precio= new BigDecimal(precioPlato)
            plato.alias = alias
            plato.comanda = comanda
            plato.categoriaPlato = CategoriaPlato.get(categoriaId)

            plato.save(flush:true, failsOnError:true)


        }.invalidToken {
          println("Doble posteo detectado...")
        }

        redirect(action: "index")
    }
}
