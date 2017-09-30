package triclinio

import com.triclinio.domains.seguridad.Perfil
import com.triclinio.domains.seguridad.Requestmap
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.seguridad.UsuarioPerfil
import org.springframework.web.bind.annotation.RequestMapping

class BootStrap {

    def init = { servletContext ->

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
    }
    
    def destroy = {
    }
}
