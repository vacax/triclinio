package triclinio

import com.triclinio.domains.restaurante.ClienteCuenta
import com.triclinio.domains.restaurante.Cuenta
import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.EstadoMesa
import com.triclinio.domains.restaurante.Mesa
import com.triclinio.domains.restaurante.Plato
import com.triclinio.domains.seguridad.Perfil
import com.triclinio.domains.seguridad.Requestmap
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.seguridad.UsuarioPerfil
import org.springframework.web.bind.annotation.RequestMapping

class BootStrap {

    def init = { servletContext ->

        println "Inicializando la Aplicación"

        Usuario usuario = new Usuario(username: "admin", password: "admin", nombre: "Administrador").save(flush: true, failOnError: true)
        Perfil perfil = new Perfil(authority: "ROLE_ADMIN").save(flush: true, failOnError: true)
        UsuarioPerfil.create(usuario, perfil)
        new Requestmap(url: "/**", configAttribute: "ROLE_ADMIN").save(flush: true, failOnError: true)

        for (String url in [
                '/', '/error', '/index', '/index.gsp', '/**/favicon.ico', '/shutdown',
                '/assets/**', '/**/js/**', '/**/css/**', '/**/images/**',
                '/login', '/login.*', '/login/*',
                '/logout', '/logout.*', '/logout/*', '/dbconsole/**']) {
            new Requestmap(url: url, configAttribute: 'permitAll,ROLE_ANONYMOUS').save(flush: true, failOnError: true)
        }

        new Requestmap(url: '/console/**',    configAttribute: 'ROLE_ADMIN').save(flush: true, failOnError: true)
        new Requestmap(url: '/plugins/console*/**',    configAttribute: 'ROLE_ADMIN').save(flush: true, failOnError: true)
        new Requestmap(url: '/static/console/**',    configAttribute: 'ROLE_ADMIN').save(flush: true, failOnError: true)
        new Requestmap(url: '/profile/**',    configAttribute: 'ROLE_USER').save(flush: true, failOnError: true)
        new Requestmap(url: '/admin/**',      configAttribute: 'ROLE_ADMIN').save(flush: true, failOnError: true)
        new Requestmap(url: '/admin/role/**', configAttribute: 'ROLE_SUPERVISOR').save(flush: true, failOnError: true)
        new Requestmap(url: '/admin/user/**', configAttribute: 'ROLE_ADMIN,ROLE_SUPERVISOR').save(flush: true, failOnError: true)
        new Requestmap(url: '/login/impersonate', configAttribute: 'ROLE_SWITCH_USER,isFullyAuthenticated()').save(flush: true, failOnError: true)

        //TODO: Crear modelo de Cuenta para poder facturar
        new Plato(nombre: "Nuevo Plato", precio: 150.00).save(flush: true, failOnError: true)
        new Plato(nombre: "Flan de leche",precio:  100.00).save(flush: true, failOnError: true)


        new EstadoMesa(codigo: 1001, nombre: "Disponible").save(flush: true, failOnError: true)
        new EstadoMesa(codigo: 1000, nombre: "Ocupada").save(flush: true, failOnError: true)

        new EstadoCuenta(codigo: 1000,nombre: "Abierta").save(flush: true, failOnError: true)

        new Mesa(numeroMesa: 1, nombre: "Mesa 1",estadoMesa: EstadoMesa.findByCodigo(1001)).save(flush: true, failOnError: true)
        new Mesa(numeroMesa: 2, nombre: "Mesa 2",estadoMesa: EstadoMesa.findByCodigo(1000)).save(flush: true, failOnError: true)
        new Mesa(numeroMesa: 3, nombre: "Mesa 3",estadoMesa: EstadoMesa.findByCodigo(1001)).save(flush: true, failOnError: true)
        new Mesa(numeroMesa: 4, nombre: "Mesa 4",estadoMesa: EstadoMesa.findByCodigo(1001)).save(flush: true, failOnError: true)


//        new ClienteCuenta(cuenta:  new Cuenta(usuario: Usuario.findByUsername("admin"),estadoCuenta: EstadoCuenta.findByCodigo(1000)).save(flush: true, failOnError: true) ,nombre: "Cliente Generico",montoDescuento: 0.00,porcientoDescuento: 0.00,porcientoImpuesto: 0.00,montoBruto: 0.00,montoImpuesto: 0.00,montoNeto: 0.00).save(flush: true, failOnError: true)

    }

    def destroy = {
        println "Parando la Aplicacion"
    }
}
