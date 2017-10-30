package triclinio

import com.triclinio.domains.configuracion.Parametro
import com.triclinio.domains.cxc.Cliente
import com.triclinio.domains.restaurante.CategoriaPlato
import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.EstadoMesa
import com.triclinio.domains.restaurante.Mesa
import com.triclinio.domains.restaurante.Plato
import com.triclinio.domains.seguridad.Perfil
import com.triclinio.domains.seguridad.Requestmap
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.seguridad.UsuarioPerfil
import com.triclinio.domains.venta.EstadoFactura
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->

        println "Inicializando la Aplicación"

        Usuario usuario = Usuario.findByUsername("admin") ? null :new Usuario(username: "admin", password: "admin", nombre: "Administrador").save(flush: true, failOnError: true)
        if(usuario) {
            Perfil perfil = new Perfil(authority: "ROLE_ADMIN").save(flush: true, failOnError: true)

            UsuarioPerfil.create(usuario, perfil)
//            new Requestmap(url: "/**", configAttribute: "ROLE_ADMIN").save(flush: true, failOnError: true)

            new Perfil(authority: "ROLE_CAMARERO").save(flush: true, failOnError: true)
            new Perfil(authority: "ROLE_FACTURADOR").save(flush: true, failOnError: true)
            new Perfil(authority: "ROLE_SUPERVISOR_FACTURADOR").save(flush: true, failOnError: true)
            new Perfil(authority: "ROLE_SUPERVISOR_CAMARERO").save(flush: true, failOnError: true)
            
            for (String url in [
                    '/', '/error', '/index', '/index.gsp', '/**/favicon.ico', '/shutdown',
                    '/assets/**', '/**/js/**', '/**/css/**', '/**/images/**',
                    '/login', '/login.*', '/login/*',
                    '/logout', '/logout.*', '/logout/*', '/dbconsole/**']) {
                new Requestmap(url: url, configAttribute: 'permitAll,ROLE_ANONYMOUS').save(flush: true, failOnError: true)
            }


            new Requestmap(url: '/console/**', configAttribute: 'ROLE_ADMIN').save(flush: true, failOnError: true)
            new Requestmap(url: '/plugins/console*/**', configAttribute: 'ROLE_ADMIN').save(flush: true, failOnError: true)
            new Requestmap(url: '/static/console/**', configAttribute: 'ROLE_ADMIN').save(flush: true, failOnError: true)
            new Requestmap(url: '/profile/**', configAttribute: 'ROLE_USER').save(flush: true, failOnError: true)
            new Requestmap(url: '/admin/**', configAttribute: 'ROLE_ADMIN').save(flush: true, failOnError: true)
            new Requestmap(url: '/admin/role/**', configAttribute: 'ROLE_SUPERVISOR').save(flush: true, failOnError: true)
            new Requestmap(url: '/admin/user/**', configAttribute: 'ROLE_ADMIN,ROLE_SUPERVISOR').save(flush: true, failOnError: true)
            new Requestmap(url: '/login/impersonate', configAttribute: 'ROLE_SWITCH_USER,isFullyAuthenticated()').save(flush: true, failOnError: true)
        }

        //
        Parametro.findByCodigo(Parametro.JMS_URL) ?: new Parametro(codigo: Parametro.JMS_URL, nombre: "JMS_URL", valor: "PonerIP").save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.JMS_COLA) ?: new Parametro(codigo: Parametro.JMS_COLA, nombre: "JMS_COLA", valor: "tricli").save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.JMS_USUARIO) ?: new Parametro(codigo: Parametro.JMS_USUARIO, nombre: "JMS_USUARIO", valor: "admin").save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.JMS_PASSWORD) ?: new Parametro(codigo: Parametro.JMS_PASSWORD, nombre: "JMS_PASSWORD", valor: "admin").save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.JMS_HABILITAR) ?: new Parametro(codigo: Parametro.JMS_HABILITAR, nombre: "JMS_HABILITAR", valor: "1").save(flush: true, failOnError: true)

        //
        Parametro.findByCodigo(Parametro.APP_NOMBRE_RESTAURANTE) ?: new Parametro(codigo: Parametro.APP_NOMBRE_RESTAURANTE, nombre: "APP_NOMBRE_RESTAURANTE", valor: "GUAVA").save(flush: true, failOnError: true)

        //
        Parametro.findByCodigo(Parametro.TICKET_ENCABEZADO_1) ?: new Parametro(codigo: Parametro.TICKET_ENCABEZADO_1, nombre: "TICKET_ENCABEZADO_1", valor: "PUCMM - ADH").save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.TICKET_ENCABEZADO_2) ?: new Parametro(codigo: Parametro.TICKET_ENCABEZADO_2, nombre: "TICKET_ENCABEZADO_2", valor: "SANTIAGO, R.D.").save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.TICKET_ENCABEZADO_3) ?: new Parametro(codigo: Parametro.TICKET_ENCABEZADO_3, nombre: "TICKET_ENCABEZADO_3", valor: "FACTURA").save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.TICKET_ENCABEZADO_4) ?: new Parametro(codigo: Parametro.TICKET_ENCABEZADO_4, nombre: "TICKET_ENCABEZADO_4", valor: "REIMPRESION FACTURA").save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.TICKET_PIEPAG_1) ?: new Parametro(codigo: Parametro.TICKET_PIEPAG_1, nombre: "TICKET_PIEPAG_1", valor: "Gracias por preferirnos!!").save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.TICKET_PIEPAG_2) ?: new Parametro(codigo: Parametro.TICKET_PIEPAG_2, nombre: "TICKET_PIEPAG_2", valor: "Thanks for choosing us").save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.COMANDA_ENCABEZADO_3) ?: new Parametro(codigo: Parametro.COMANDA_ENCABEZADO_3, nombre: "COMANDA_ENCABEZADO_3", valor: "COMANDA").save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.PRECUENTA_ENCABEZADO_3) ?: new Parametro(codigo: Parametro.PRECUENTA_ENCABEZADO_3, nombre: "PRECUENTA_ENCABEZADO_3", valor: "PRECUENTA").save(flush: true, failOnError: true)

        Parametro.findByCodigo(Parametro.CANTIDAD_COMANDA) ?: new Parametro(codigo: Parametro.CANTIDAD_COMANDA, nombre: "CANTIDAD_COMANDA", valor: 3).save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.CANTIDAD_FACTURA) ?: new Parametro(codigo: Parametro.CANTIDAD_FACTURA, nombre: "CANTIDAD_FACTURA", valor: 2).save(flush: true, failOnError: true)
        Parametro.findByCodigo(Parametro.CANTIDAD_PRECUENTA) ?: new Parametro(codigo: Parametro.CANTIDAD_PRECUENTA, nombre: "CANTIDAD_PRECUENTA", valor: 1).save(flush: true, failOnError: true)


        //Estado Mesa.
        EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE) ?:new EstadoMesa(codigo: EstadoMesa.DISPONIBLE,nombre: "Disponible").save(flush: true, failOnError: true)
        EstadoMesa.findByCodigo(EstadoMesa.OCUPADA) ?:new EstadoMesa(codigo: EstadoMesa.OCUPADA,nombre: "Ocupada").save(flush: true, failOnError: true)
        EstadoMesa.findByCodigo(EstadoMesa.DESACTIVADA) ?:new EstadoMesa(codigo: EstadoMesa.DESACTIVADA,nombre: "Desactivada").save(flush: true, failOnError: true)

        //Estado de Cuenta
        EstadoCuenta.findByCodigo(EstadoCuenta.ABIERTO) ?: new EstadoCuenta(codigo: EstadoCuenta.ABIERTO,nombre: "ABIERTO").save(flush: true, failOnError: true)
        EstadoCuenta.findByCodigo(EstadoCuenta.CERRADA) ?:new EstadoCuenta(codigo: EstadoCuenta.CERRADA,nombre: "CERRADA").save(flush: true, failOnError: true)
        EstadoCuenta.findByCodigo(EstadoCuenta.ELIMINADA) ?:new EstadoCuenta(codigo: EstadoCuenta.ELIMINADA,nombre: "ELIMINADA").save(flush: true, failOnError: true)

        //Estado de factura.
        EstadoFactura.findByCodigo(EstadoFactura.PREFACTURA) ?: new EstadoFactura(codigo: EstadoFactura.PREFACTURA,nombre: "PRE_FACTURADA").save(flush: true, failOnError: true)
        EstadoFactura.findByCodigo(EstadoFactura.FACTURADA) ?: new EstadoFactura(codigo: EstadoFactura.FACTURADA,nombre: "FACTURADA").save(flush: true, failOnError: true)
        EstadoFactura.findByCodigo(EstadoFactura.FACTURADA_COBRADA) ?: new EstadoFactura(codigo: EstadoFactura.FACTURADA_COBRADA,nombre: "FACTURADA_COBRADA").save(flush: true, failOnError: true)

        CategoriaPlato.findByNombre("Entradas") ?: new CategoriaPlato(nombre: "Entradas").save(flush: true, failOnError: true)
        CategoriaPlato.findByNombre("Ensaladas") ?: new CategoriaPlato(nombre: "Ensaladas").save(flush: true, failOnError: true)
        CategoriaPlato.findByNombre("Platos Fuertes") ?: new CategoriaPlato(nombre: "Platos Fuertes").save(flush: true, failOnError: true)
        CategoriaPlato.findByNombre("Postres") ?: new CategoriaPlato(nombre: "Postres").save(flush: true, failOnError: true)
        CategoriaPlato.findByNombre("Bebidas") ?: new CategoriaPlato(nombre: "Bebidas").save(flush: true, failOnError: true)


        println("Aplicación en modo ${Environment.current}")
        if(Environment.current == Environment.DEVELOPMENT) {


            println("Aplicación en modo desarrollo")

//            //Platos para pruebas
//            Plato.findByNombre("La Orquesta")?: new Plato(nombre: "La Orquesta", precio: 75.00).save(flush: true, failOnError: true)
//            Plato.findByNombre("Popurri")?: new Plato(nombre: "Popurri", precio: 95.00).save(flush: true, failOnError: true)
//            Plato.findByNombre("Dulce Tentación")?: new Plato(nombre: "Dulce Tentación", precio: 125.00).save(flush: true, failOnError: true)

            //Mesas para pruebas
            Mesa.findByNumeroMesa(1) ?: new Mesa(numeroMesa: 1, nombre: "Mesa 1", estadoMesa: EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE)).save(flush: true, failOnError: true)
            Mesa.findByNumeroMesa(2) ?:  new Mesa(numeroMesa: 2, nombre: "Mesa 2", estadoMesa: EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE)).save(flush: true, failOnError: true)
            Mesa.findByNumeroMesa(3) ?:  new Mesa(numeroMesa: 3, nombre: "Mesa 3", estadoMesa: EstadoMesa.findByCodigo(EstadoMesa.DISPONIBLE)).save(flush: true, failOnError: true)

//            //TODO: Crear modelo de Cuenta para poder facturar(Usuario)
//            Usuario usuario1 = new Usuario(username: "john", password: "1234", nombre: "N/A").save(flush: true, failOnError: true)
//            Usuario usuario2 = new Usuario(username: "tolentino", password: "1234", nombre: "N/A").save(flush: true, failOnError: true)
//
//            //TODO: Crear modelo de Cuenta para poder facturar(Cliente)
//            Cliente cliente = new Cliente(nombre: "John").save(flush: true, failOnError: true)
//            Cliente cliente1 = new Cliente(nombre: "Camacho").save(flush: true, failOnError: true)
//            Cliente cliente2 = new Cliente(nombre: "Tolentino").save(flush: true, failOnError: true)
//
//
//            //TODO: Crear modelo de Cuenta para poder facturar(Cuenta)
//            Cuenta cuenta=new Cuenta(usuario: usuario1,estadoCuenta: estadoCuenta).save(flush: true, failOnError: true)//Preguntarle al profe como se pone la lista
//            Cuenta cuenta1=new Cuenta(usuario: usuario1,estadoCuenta: estadoCuenta1).save(flush: true, failOnError: true)
//            Cuenta cuenta2=new Cuenta(usuario: usuario2,estadoCuenta: estadoCuenta).save(flush: true, failOnError: true)
//            Cuenta cuenta3=new Cuenta(usuario: usuario2,estadoCuenta: estadoCuenta1).save(flush: true, failOnError: true)
//
//            //TODO: Crear modelo de Cuenta para poder facturar(CuentaMesa)
//            CuentaMesa cuentaMesa=new CuentaMesa(cuenta: cuenta,mesa: mesa).save(flush: true, failOnError: true)
//            CuentaMesa cuentaMesa1=new CuentaMesa(cuenta: cuenta1,mesa: mesa1).save(flush: true, failOnError: true)
//
//            //TODO: Crear modelo de Cuenta para poder facturar(ClienteCuenta)
//            ClienteCuenta clienteCuenta=new ClienteCuenta(nombre: "Ridore",cuenta: cuenta1,porcientoImpuesto:0.3 ,porcientoDescuento:0.21,montoBruto:150,montoDescuento:210,montoImpuesto:0.25,montoNeto:121).save(flush: true, failOnError: true)
//            ClienteCuenta clienteCuenta1=new ClienteCuenta(nombre: "Carlos",cuenta: cuenta,porcientoImpuesto: 0.5,porcientoDescuento:0.21,montoBruto:150,montoDescuento:210,montoImpuesto:0.25,montoNeto:121).save(flush: true, failOnError: true)
//            ClienteCuenta clienteCuenta21=new ClienteCuenta(nombre: "Lisa",cuenta: cuenta2,porcientoImpuesto: 0.9,porcientoDescuento:0.21,montoBruto:150,montoDescuento:210,montoImpuesto:0.25,montoNeto:121).save(flush: true, failOnError: true)
//
//        ClienteCuenta clienteCuenta2=new ClienteCuenta(nombre: "Marcano",cuenta: cuenta3,porcientoImpuesto: 150,porcientoDescuento:0.21,montoBruto:150,montoDescuento:210,montoImpuesto:0.25,montoNeto:121).save(flush: true, failOnError: true)
//

            //TODO: Crear modelo de Cuenta para poder facturar(OrdenDetalle)
//        OrdenDetalle ordenDetalle=new OrdenDetalle(clienteCuenta: clienteCuenta,plato: plato,cantidad: 1,nombrePlato: "Nuevo Plato",precio: 150,importe: 10,porcientoImpuesto: 0.50,porcientoDescuento: 0.2,montoBruto: 150,montoDescuento: 145,montoImpuesto: 170,montoNeto: 150).save(flush: true, failOnError: true)//Preguntrle al profe porque nombre plato?
//
//        OrdenDetalle ordenDetalle1=new OrdenDetalle(clienteCuenta: clienteCuenta1,plato: plato2,cantidad: 1,nombrePlato: "Nuevo Plato 2",precio: 200,importe: 2,porcientoImpuesto: 0.50,porcientoDescuento: 0.2,montoBruto: 200,montoDescuento: 170,montoImpuesto: 250,montoNeto: 200).save(flush: true, failOnError: true)
//        OrdenDetalle ordenDetalle12=new OrdenDetalle(clienteCuenta: clienteCuenta1,plato: plato2,cantidad: 1,nombrePlato: "Nuevo Plato 2",precio: 200,importe: 2,porcientoImpuesto: 0.50,porcientoDescuento: 0.2,montoBruto: 200,montoDescuento: 170,montoImpuesto: 250,montoNeto: 200).save(flush: true, failOnError: true)
//        OrdenDetalle ordenDetalle13=new OrdenDetalle(clienteCuenta: clienteCuenta1,plato: plato2,cantidad: 1,nombrePlato: "Nuevo Plato 2",precio: 200,importe: 2,porcientoImpuesto: 0.50,porcientoDescuento: 0.2,montoBruto: 200,montoDescuento: 170,montoImpuesto: 250,montoNeto: 200).save(flush: true, failOnError: true)
//        OrdenDetalle ordenDetalle14=new OrdenDetalle(clienteCuenta: clienteCuenta21,plato: plato2,cantidad: 1,nombrePlato: "Nuevo Plato 2",precio: 800,importe: 2,porcientoImpuesto: 0.50,porcientoDescuento: 0.2,montoBruto: 150,montoDescuento: 170,montoImpuesto: 250,montoNeto: 200).save(flush: true, failOnError: true)
//
//        OrdenDetalle ordenDetalle2=new OrdenDetalle(clienteCuenta: clienteCuenta2,plato: plato3,cantidad: 1,nombrePlato: "Nuevo Plato 3",precio: 150,importe: 10,porcientoImpuesto: 0.50,porcientoDescuento: 0.2,montoBruto: 150,montoDescuento: 145,montoImpuesto: 170,montoNeto: 150).save(flush: true, failOnError: true)

//        //TODO: Crear modelo de Cuenta para poder facturar(Factura)
//        Factura factura=new Factura(cliente: cliente1,usuario: usuario1,estadoFactura: estadoFactura,porcientoImpuesto: 150,porcientoDescuento:0.21,montoBruto:150,montoDescuento:210,montoImpuesto:0.25,montoNeto:121).save(flush: true, failOnError: true)
//
//        Factura factura1=new Factura(cliente: cliente2,usuario: usuario2,estadoFactura: estadoFactura1,porcientoImpuesto: 150,porcientoDescuento:0.21,montoBruto:150,montoDescuento:210,montoImpuesto:0.25,montoNeto:121).save(flush: true, failOnError: true)
//
//        //TODO: Crear modelo de Cuenta para poder facturar(FacturaDetalle)
//        new FacturaDetalle(factura: factura,ordenDetalle: ordenDetalle1).save(flush: true, failOnError: true)
//        new FacturaDetalle(factura: factura1,ordenDetalle: ordenDetalle2).save(flush: true, failOnError: true)
        }

    }

    def destroy = {
        println "Parando la Aplicacion"
    }
}
