package com.triclinio.controllers.restaurante

import com.triclinio.domains.configuracion.Parametro
import com.triclinio.domains.cxc.Cliente
import com.triclinio.domains.restaurante.ClienteCuenta
import com.triclinio.domains.restaurante.Cuenta
import com.triclinio.domains.restaurante.EstadoCuenta
import com.triclinio.domains.restaurante.OrdenDetalle
import com.triclinio.domains.seguridad.Usuario
import com.triclinio.domains.venta.EstadoFactura
import com.triclinio.domains.venta.Factura
import com.triclinio.domains.venta.FacturaDetalle
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.Border
import jxl.format.BorderLineStyle
import jxl.format.Colour
import jxl.format.VerticalAlignment
import jxl.write.Label
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook

import java.text.SimpleDateFormat


@Secured(["ROLE_ADMIN", "ROLE_CAMARERO", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
class FacturaDetalleController {

    def TANDA_DIA = 1
    def TANDA_NOCHE = 2
    def TANDA_DIA_COMPLETO = 3

    //Datos dinero
    def matricialService
    def facturacionService

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    SimpleDateFormat sdf2 = new SimpleDateFormat('yyyy-MM-dd')
    SimpleDateFormat hrs = new SimpleDateFormat("HH:mm:ss")

    def index() {}

    /**
     *
     * @param clienteCuentaId
     * @return
     */
    def procesarOrden(long clienteCuentaId) {

        Factura factura = facturacionService.procesarOrden(clienteCuentaId, (Usuario) applicationContext.springSecurityService.getCurrentUser())

        render factura.id
    }

    def imprimirPreCuenta(long idFactura) {
        Factura factura = Factura.findById(idFactura)

        matricialService.generarPreCuenta(factura.id)
        println "Impuesto " + factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.cuenta.id

        redirect(uri: "/cuenta/detalleCuenta?idFactura=" + factura.id + "&idCuenta=" + factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.cuenta.id)
    }

    def facturar(long factura) {
        Factura facturaTmp = Factura.findById(factura)

        List<FacturaDetalle> facturaDetalles = new ArrayList<>()

        facturaDetalles = FacturaDetalle.findAllByFactura(facturaTmp)
        facturaTmp.setEstadoFactura(EstadoFactura.findByCodigo(EstadoFactura.FACTURADA))
        facturaTmp.save(flush: true, failOnError: true)

        [facturaDetalles: facturaDetalles, factura: facturaTmp]
    }

    def imprimir(long idFactura) {

        println "Ver:" + idFactura
        Factura factura = Factura.findById(idFactura)

        [factura: factura]
    }

    def imprimirFactura(long id) {
        Factura factura = Factura.findById(id)
        factura.numeroAutorizacion = params.get("numeroAutorizacion" as String)
        factura.terminalTarjeta = params.get("terminalTarjeta" as String)
        factura.setEstadoFactura(EstadoFactura.findByCodigo(EstadoFactura.FACTURADA_COBRADA))
        factura.save(flush: true, failOnError: true)
        int cantidadImpresion = Parametro.findByCodigo(Parametro.CANTIDAD_IMPRESION_FACTURA).valor.toInteger()

//        for (int i=1;i<cantidadImpresion;i++) {
        matricialService.generarFactura(factura.id)
//        }

        def idClienteCuenta = factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.id
        def idCuenta = factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.cuenta.id

        for (OrdenDetalle ordenDetalle : factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.listaOrdenDetalle) {
            ordenDetalle.clienteCuenta.habilitado = false
            ordenDetalle.save(flush: true, failOnError: true)
        }

        ClienteCuenta clienteCuenta = ClienteCuenta.findById(idClienteCuenta)
        clienteCuenta.habilitado = false
        clienteCuenta.save(flush: true, failOnError: true)

        boolean habilitado = false

        for (ClienteCuenta clienteCuenta1 : Cuenta.findById(idCuenta).listaClienteCuenta)
            if (clienteCuenta1.habilitado) {
                habilitado = true
                break
            }

        if (!habilitado) {
            Cuenta cuenta = Cuenta.findById(idCuenta)
            cuenta.setEstadoCuenta(EstadoCuenta.findByCodigo(EstadoCuenta.CERRADA))
            cuenta.save(flush: true, failOnError: true)
        }
        factura.save(flush: true, failOnError: true)

        redirect(uri: "/cuenta/cuentasAbiertas")
    }

    @Secured(["ROLE_ADMIN", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
    def historialFacturas() {
        def facturas = Factura.findAllByHabilitadoAndEstadoFactura(true, EstadoFactura.findByCodigo(EstadoFactura.FACTURADA_COBRADA))
        [facturas: facturas]
    }

    @Secured(["ROLE_ADMIN", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
    def reversarFactura() {
        def factura = Factura.findById(params.get("idFactura") as Long)

        for (OrdenDetalle ordenDetalle : factura.listaFacturaDetalle.ordenDetalle) {

            ordenDetalle.clienteCuenta.cuenta.setEstadoCuenta(EstadoCuenta.findById(1))
            for (ClienteCuenta clienteCuenta : ordenDetalle.clienteCuenta.cuenta.listaClienteCuenta) {
                clienteCuenta.setHabilitado(true)
                clienteCuenta.save()
            }
            ordenDetalle.save(flush: true, failOnError: true)
        }

        factura.estadoFactura = EstadoFactura.findByCodigoAndHabilitado(EstadoFactura.FACTURADA, true)
        factura.save(flush: true, failOnError: true)
        redirect(uri: "/cuenta/cuentasAbiertas")
    }

    /**agar
     * TODO: implementar el metodo e indicando label que diga reimpresion.
     * @param facturaId
     * @return
     */
    @Secured(["ROLE_ADMIN", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
    def facturaReimprimir() {
        def facturas = Factura.findAllByHabilitadoAndEstadoFactura(true, EstadoFactura.findByCodigo(EstadoFactura.FACTURADA_COBRADA))
        //def facturas = Factura.findAllByHabilitado(true)
        [facturas: facturas]
    }

    def reimpresionFactura(long facturaid) {
        matricialService.generarFactura(facturaid);
        redirect(uri: "/facturaDetalle/facturaReimprimir");
    }

    @Secured(["ROLE_ADMIN", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
    def indexCuadre() {}

    @Secured(["ROLE_ADMIN", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
    def cuadre() {
        [facturas: getCuadre(TANDA_DIA_COMPLETO, sdf2.format(new Date()), sdf2.format(new Date()))]
    }

    def refrescar(String data) {
        def (inicio, fin) = data.tokenize('_')

        Calendar upperDate = Calendar.getInstance()

        upperDate.set(Calendar.YEAR, fin.tokenize('-')[0] as int)
        upperDate.set(Calendar.MONTH, ((fin.tokenize('-')[1] as int) - 1))
        upperDate.set(Calendar.DAY_OF_MONTH, fin.tokenize('-')[2] as int)
        upperDate.set(Calendar.HOUR_OF_DAY, 23)
        upperDate.set(Calendar.MINUTE, 59)
        upperDate.set(Calendar.SECOND, 59)

        Calendar lowerDate = Calendar.getInstance()
        lowerDate.set(Calendar.YEAR, inicio.tokenize('-')[0] as int)
        upperDate.set(Calendar.MONTH, ((inicio.tokenize('-')[1] as int) - 1))
        lowerDate.set(Calendar.DAY_OF_MONTH, inicio.tokenize('-')[2] as int)
        lowerDate.set(Calendar.HOUR_OF_DAY, 0)
        lowerDate.set(Calendar.MINUTE, 0)
        lowerDate.set(Calendar.SECOND, 0)

        List<Factura> facturas = Factura.findAllByEstadoFacturaAndDateCreatedBetween(EstadoFactura.findByCodigo(EstadoFactura.FACTURADA_COBRADA), lowerDate.getTime(), upperDate.getTime())

        def fs = []
        facturas.each {
            if (hrs.format(it.dateCreated) > hrs.format(lowerDate.getTime())) {
                if (hrs.format(it.dateCreated) < hrs.format(upperDate.getTime())) {
                    def map = [:]
                    map['id'] = it.id
                    map['usuario'] = it.usuario.nombre
                    map['fecha'] = sdf.format(it.dateCreated)
                    map['metodoPago'] = it.terminalTarjeta ? "Tarjeta" : "Efectivo"
                    map['monto'] = it.montoNeto
                    fs.add(map)
                }
            }
        }

        render fs as JSON
    }

    @Secured(["ROLE_ADMIN", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
    def cuadreTandaMedioDia() {
        [facturas: getCuadre(TANDA_DIA, sdf2.format(new Date()), sdf2.format(new Date()))]
    }

    def refrescarDia(String data) {
        def (inicio, fin) = data.tokenize('_')

        Calendar upperDate = Calendar.getInstance()
        Calendar lowerDate = Calendar.getInstance()

        upperDate.set(Calendar.YEAR, fin.tokenize('-')[0] as int)
        upperDate.set(Calendar.MONTH, ((fin.tokenize('-')[1] as int) - 1))
        upperDate.set(Calendar.DAY_OF_MONTH, fin.tokenize('-')[2] as int)
        upperDate.set(Calendar.HOUR_OF_DAY, 16)
        upperDate.set(Calendar.MINUTE, 59)
        upperDate.set(Calendar.SECOND, 59)

        lowerDate.set(Calendar.YEAR, inicio.tokenize('-')[0] as int)
        upperDate.set(Calendar.MONTH, ((inicio.tokenize('-')[1] as int) - 1))
        lowerDate.set(Calendar.DAY_OF_MONTH, inicio.tokenize('-')[2] as int)
        lowerDate.set(Calendar.HOUR_OF_DAY, 0)
        lowerDate.set(Calendar.MINUTE, 0)
        lowerDate.set(Calendar.SECOND, 0)

        List<Factura> facturas = Factura.findAllByEstadoFacturaAndDateCreatedBetween(EstadoFactura.findByCodigo(EstadoFactura.FACTURADA_COBRADA), lowerDate.getTime(), upperDate.getTime())

        def fs = []

        facturas.each {
            if (hrs.format(it.dateCreated) > hrs.format(lowerDate.getTime())) {
                if (hrs.format(it.dateCreated) < hrs.format(upperDate.getTime())) {
                    def map = [:]
                    map['id'] = it.id
                    map['usuario'] = it.usuario.nombre
                    map['fecha'] = sdf.format(it.dateCreated)
                    map['metodoPago'] = it.terminalTarjeta ? "Tarjeta" : "Efectivo"
                    map['monto'] = it.montoNeto
                    fs.add(map)
                }
            }
        }
        render fs as JSON
    }

    @Secured(["ROLE_ADMIN", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
    def cuadreTandaNoche() {
        [facturas: getCuadre(TANDA_NOCHE, sdf2.format(new Date()), sdf2.format(new Date()))]
    }

    def refrescarNoche(String data) {
        def (inicio, fin) = data.tokenize('_')
        Calendar upperDate = Calendar.getInstance()
        Calendar lowerDate = Calendar.getInstance()


        upperDate.set(Calendar.YEAR, fin.tokenize('-')[0] as int)
        upperDate.set(Calendar.MONTH, ((fin.tokenize('-')[1] as int) - 1))
        upperDate.set(Calendar.DAY_OF_MONTH, fin.tokenize('-')[2] as int)
        upperDate.set(Calendar.HOUR_OF_DAY, 23)
        upperDate.set(Calendar.MINUTE, 59)
        upperDate.set(Calendar.SECOND, 59)

        lowerDate.set(Calendar.YEAR, inicio.tokenize('-')[0] as int)
        lowerDate.set(Calendar.MONTH, ((inicio.tokenize('-')[1] as int) - 1))
        lowerDate.set(Calendar.DAY_OF_MONTH, inicio.tokenize('-')[2] as int)
        lowerDate.set(Calendar.HOUR_OF_DAY, 18)
        lowerDate.set(Calendar.MINUTE, 30)
        lowerDate.set(Calendar.SECOND, 0)


        List<Factura> facturas = Factura.findAllByEstadoFacturaAndDateCreatedBetween(EstadoFactura.findByCodigo(EstadoFactura.FACTURADA_COBRADA), lowerDate.getTime(), upperDate.getTime())

        def fs = []

        facturas.each {
            if (hrs.format(it.dateCreated) > hrs.format(lowerDate.getTime())) {
                if (hrs.format(it.dateCreated) < hrs.format(upperDate.getTime())) {
                    def map = [:]
                    map['id'] = it.id
                    map['usuario'] = it.usuario.nombre
                    map['fecha'] = sdf.format(it.dateCreated)
                    map['metodoPago'] = it.terminalTarjeta ? "Tarjeta" : "Efectivo"
                    map['monto'] = it.montoNeto
                    fs.add(map)
                }
            }
        }
        render fs as JSON
    }

    def verDetalleFactura(long id) {
        Factura factura1 = Factura.findById(id)
        [factura: factura1]
    }

    @Secured(["ROLE_ADMIN", "ROLE_FACTURADOR", "ROLE_SUPERVISOR_FACTURADOR", "ROLE_SUPERVISOR_CAMARERO"])
    def numeroPlatosPorFecha() {
        Calendar upperDate = Calendar.getInstance()
        Calendar lowerDate = Calendar.getInstance()

        int day = 26
        upperDate.set(Calendar.YEAR, 2018 as int)
        upperDate.set(Calendar.MONTH, 9 as int)
        upperDate.set(Calendar.DAY_OF_MONTH, day)
        upperDate.set(Calendar.HOUR_OF_DAY, 23)
        upperDate.set(Calendar.MINUTE, 59)
        upperDate.set(Calendar.SECOND, 59)

        lowerDate.set(Calendar.YEAR, 2018 as int)
        lowerDate.set(Calendar.MONTH, 9 as int)
        lowerDate.set(Calendar.DAY_OF_MONTH, day)
        lowerDate.set(Calendar.HOUR_OF_DAY, 0)
        lowerDate.set(Calendar.MINUTE, 0)
        lowerDate.set(Calendar.SECOND, 0)

        def y = sdf.parse(sdf.format(upperDate.getTime()))
        def x = sdf.parse(sdf.format(lowerDate.getTime()))
        def detalles = OrdenDetalle.findAllByDateCreatedBetween(x, y)

        def platoCantidad = [:]
        detalles.each {
            if (platoCantidad.containsKey(it.nombrePlato)) {
                platoCantidad[it.nombrePlato]++
            } else {
                platoCantidad[it.nombrePlato] = 1
            }
        }

        //def detalles =  FacturaDetalle.list()
        render platoCantidad as JSON
    }

    def descargarCuadre() {
        def (inicio, fin) = params.data.tokenize('_')
        def tanda = params.tanda as int

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte.xls"')
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Cuadre", 0)
        WritableSheet sheet2 = workbook.createSheet("Platos Despachados", 1)

        WritableFont cellFontTitulo = new WritableFont(WritableFont.TIMES, 12)
        cellFontTitulo.setBoldStyle(WritableFont.BOLD)

        WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12)
        WritableCellFormat cellFormatTitulo = new WritableCellFormat(cellFontTitulo)
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont)
        cellFormatTitulo.setAlignment(Alignment.CENTRE)
        cellFormatTitulo.setVerticalAlignment(VerticalAlignment.CENTRE)
        cellFormatTitulo.setBorder(Border.ALL, BorderLineStyle.THIN)
        cellFormat.setAlignment(Alignment.CENTRE)
        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE)
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN)

        sheet1.addCell(new Label(0, 0, "ID", cellFormatTitulo))
        sheet1.addCell(new Label(1, 0, "Camarero", cellFormatTitulo))
        sheet1.addCell(new Label(2, 0, "Método de Pago", cellFormatTitulo))
        sheet1.addCell(new Label(3, 0, "Fecha", cellFormatTitulo))
        sheet1.addCell(new Label(4, 0, "Monto", cellFormatTitulo))

        sheet1.addCell(new Label(7, 0, "ID", cellFormatTitulo))
        sheet1.addCell(new Label(8, 0, "Camarero", cellFormatTitulo))
        sheet1.addCell(new Label(9, 0, "Método de Pago", cellFormatTitulo))
        sheet1.addCell(new Label(10, 0, "Fecha", cellFormatTitulo))
        sheet1.addCell(new Label(11, 0, "Monto", cellFormatTitulo))

        def totalEfectivo = 0
        def totalTarjeta = 0
        def fs = getCuadre(tanda, inicio as String, fin as String)

        def fsEfectivo = []
        def fsTarjeta = []

        fs.each {
            if (it['metodoPago'] == 'Efectivo') {
                fsEfectivo.add(it)
            } else {
                fsTarjeta.add(it)
            }
        }

        for (int i = 0; i < fsEfectivo.size(); i++) {
            sheet1.addCell(new Label(0, i + 1, fsEfectivo[i]['id'] as String, cellFormat))
            sheet1.addCell(new Label(1, i + 1, fsEfectivo[i]['usuario'] as String, cellFormat))
            sheet1.addCell(new Label(2, i + 1, fsEfectivo[i]['metodoPago'] as String, cellFormat))
            sheet1.addCell(new Label(3, i + 1, fsEfectivo[i]['fecha'] as String, cellFormat))
            sheet1.addCell(new Label(4, i + 1, fsEfectivo[i]['monto'] as String, cellFormat))
            totalEfectivo += fsEfectivo[i]['monto']
        }
        sheet1.mergeCells(0, fsEfectivo.size() + 1, 3, fsEfectivo.size() + 1)
        sheet1.addCell(new Label(0, fsEfectivo.size() + 1, 'Total', cellFormatTitulo))
        sheet1.addCell(new Label(4, fsEfectivo.size() + 1, totalEfectivo as String, cellFormatTitulo))

        for (int i = 0; i < fsTarjeta.size(); i++) {
            sheet1.addCell(new Label(7, i + 1, fsTarjeta[i]['id'] as String, cellFormat))
            sheet1.addCell(new Label(8, i + 1, fsTarjeta[i]['usuario'] as String, cellFormat))
            sheet1.addCell(new Label(9, i + 1, fsTarjeta[i]['metodoPago'] as String, cellFormat))
            sheet1.addCell(new Label(10, i + 1, fsTarjeta[i]['fecha'] as String, cellFormat))
            sheet1.addCell(new Label(11, i + 1, fsTarjeta[i]['monto'] as String, cellFormat))
            totalTarjeta += fsTarjeta[i]['monto']
        }

        sheet1.mergeCells(7, fsTarjeta.size() + 1, 10, fsTarjeta.size() + 1)
        sheet1.addCell(new Label(7, fsTarjeta.size() + 1, 'Total', cellFormatTitulo))
        sheet1.addCell(new Label(11, fsTarjeta.size() + 1, totalTarjeta as String, cellFormatTitulo))

        //PLATOS DESPACHADOS
        def platos = getPlatos(inicio as String, fin as String)

        int i = 0
        platos.each { fecha, map ->
            sheet2.mergeCells(0, i , 1, i)
            sheet2.addCell(new Label(0, i, fecha as String, cellFormatTitulo))
            sheet2.addCell(new Label(0, i + 1, "Nombre Plato", cellFormatTitulo))
            sheet2.addCell(new Label(1, i + 1, "Cantidad", cellFormatTitulo))

            map.each { plato, cantidad ->
                sheet2.addCell(new Label(0, i+2, plato as String, cellFormat))
                sheet2.addCell(new Label(1, i+2, cantidad as String, cellFormat))
                i++
            }
            i += 3
        }

        workbook.write()
        workbook.close()
    }

    def getCuadre(int tanda, String inicio, String fin) {
        Calendar upperDate = Calendar.getInstance()
        Calendar lowerDate = Calendar.getInstance()

        upperDate.set(Calendar.YEAR, fin.tokenize('-')[0] as int)
        upperDate.set(Calendar.MONTH, ((fin.tokenize('-')[1] as int) - 1))
        upperDate.set(Calendar.DAY_OF_MONTH, fin.tokenize('-')[2] as int)

        lowerDate.set(Calendar.YEAR, inicio.tokenize('-')[0] as int)
        lowerDate.set(Calendar.MONTH, ((inicio.tokenize('-')[1] as int) - 1))
        lowerDate.set(Calendar.DAY_OF_MONTH, inicio.tokenize('-')[2] as int)

        switch (tanda) {
            case TANDA_DIA:
                upperDate.set(Calendar.HOUR_OF_DAY, 18)
                upperDate.set(Calendar.MINUTE, 29)
                upperDate.set(Calendar.SECOND, 59)

                lowerDate.set(Calendar.HOUR_OF_DAY, 0)
                lowerDate.set(Calendar.MINUTE, 0)
                lowerDate.set(Calendar.SECOND, 0)
                break
            case TANDA_NOCHE:
                upperDate.set(Calendar.HOUR_OF_DAY, 23)
                upperDate.set(Calendar.MINUTE, 59)
                upperDate.set(Calendar.SECOND, 59)

                lowerDate.set(Calendar.HOUR_OF_DAY, 18)
                lowerDate.set(Calendar.MINUTE, 30)
                lowerDate.set(Calendar.SECOND, 0)
                break
            case TANDA_DIA_COMPLETO:
                upperDate.set(Calendar.HOUR_OF_DAY, 23)
                upperDate.set(Calendar.MINUTE, 59)
                upperDate.set(Calendar.SECOND, 59)

                lowerDate.set(Calendar.HOUR_OF_DAY, 0)
                lowerDate.set(Calendar.MINUTE, 0)
                lowerDate.set(Calendar.SECOND, 0)
        }

        List<Factura> facturas = Factura.findAllByEstadoFacturaAndDateCreatedBetween(EstadoFactura.findByCodigo(EstadoFactura.FACTURADA_COBRADA), lowerDate.getTime(), upperDate.getTime())
        def fs = []
        facturas.each {
            if (hrs.format(it.dateCreated) > hrs.format(lowerDate.getTime())) {
                if (hrs.format(it.dateCreated) < hrs.format(upperDate.getTime())) {
                    def map = [:]
                    map['id'] = it.id
                    map['usuario'] = it.usuario.nombre
                    map['fecha'] = sdf.format(it.dateCreated)
                    map['metodoPago'] = it.terminalTarjeta ? "Tarjeta" : "Efectivo"
                    map['monto'] = it.montoNeto
                    fs.add(map)
                }
            }
        }

        return fs
    }


    def getPlatos(String inicio, String fin) {
        Calendar upperDate = Calendar.getInstance()
        Calendar lowerDate = Calendar.getInstance()

        upperDate.set(Calendar.YEAR, fin.tokenize('-')[0] as int)
        upperDate.set(Calendar.MONTH, ((fin.tokenize('-')[1] as int) - 1))
        upperDate.set(Calendar.DAY_OF_MONTH, fin.tokenize('-')[2] as int)
        upperDate.set(Calendar.HOUR_OF_DAY, 23)
        upperDate.set(Calendar.MINUTE, 59)
        upperDate.set(Calendar.SECOND, 59)

        lowerDate.set(Calendar.YEAR, inicio.tokenize('-')[0] as int)
        lowerDate.set(Calendar.MONTH, ((inicio.tokenize('-')[1] as int) - 1))
        lowerDate.set(Calendar.DAY_OF_MONTH, inicio.tokenize('-')[2] as int)
        lowerDate.set(Calendar.HOUR_OF_DAY, 0)
        lowerDate.set(Calendar.MINUTE, 0)
        lowerDate.set(Calendar.SECOND, 0)

        def platosCantidades = [:]
        (lowerDate..upperDate).each {
            Calendar upper = Calendar.getInstance()
            upper.set(Calendar.YEAR, it.get(Calendar.YEAR))
            upper.set(Calendar.MONTH, it.get(Calendar.MONTH))
            upper.set(Calendar.DAY_OF_MONTH, it.get(Calendar.DAY_OF_MONTH))
            upper.set(Calendar.HOUR_OF_DAY, 23)
            upper.set(Calendar.MINUTE, 59)
            upper.set(Calendar.SECOND, 59)

            Calendar lower = Calendar.getInstance()
            lower.set(Calendar.YEAR, it.get(Calendar.YEAR))
            lower.set(Calendar.MONTH, it.get(Calendar.MONTH))
            lower.set(Calendar.DAY_OF_MONTH, it.get(Calendar.DAY_OF_MONTH))
            lower.set(Calendar.HOUR_OF_DAY, 0)
            lower.set(Calendar.MINUTE, 0)
            lower.set(Calendar.SECOND, 0)

            def x = sdf.parse(sdf.format(lower.getTime()))
            def y = sdf.parse(sdf.format(upper.getTime()))

            def detalles = OrdenDetalle.findAllByDateCreatedBetween(x, y)
            def platoCantidad = [:]
            detalles.each {
                if (platoCantidad.containsKey(it.nombrePlato)) {
                    platoCantidad[it.nombrePlato]++
                } else {
                    platoCantidad[it.nombrePlato] = 1
                }
            }
            platosCantidades[sdf2.format(it.getTime())] = platoCantidad
        }
        return platosCantidades
    }

}