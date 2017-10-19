package com.triclinio.services

import com.triclinio.commands.restaurant.UpdateOrdenDetalleCuenta
import com.triclinio.domains.configuracion.Parametro
import com.triclinio.domains.restaurante.ClienteCuenta
import com.triclinio.domains.restaurante.Cuenta
import com.triclinio.domains.restaurante.OrdenDetalle
import com.triclinio.domains.restaurante.Plato
import com.triclinio.domains.venta.Factura
import grails.gorm.transactions.Transactional
import org.apache.commons.lang.StringUtils

import java.awt.Stroke
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Transactional
class MatricialService {

    def brokerJmsService

    final Integer CANTIDAD_LINEAS_MEDIA_PAGINA = 32
    final Integer CANTIDAD_LINEAS_PAGINA_COMPLETA = 66
    final Integer SALTO_DE_PAGINA = 12
    final Integer CANTIDAD_COLUMNAS_COMPLETA_LETRA_NORMAL = 80
    final Integer CANTIDAD_COLUMNAS_COMPLETA_LETRA_CONDENSADA = 132
    final Integer CANTIDAD_COLUMNAS_POS = 40
    final Integer CANTIDAD_COLUMNAS_POS_42 = 42

    //Caracteres especiales para la impresora
    final String ESC = Character.toString((char) 27)
    final String TAMANO_PAGINA = Character.toString((char) 67)
    final String LETRAS_GRANDES = Character.toString((char) 14)
    final String LETRAS_CONDENSADAS = Character.toString((char) 15)
    final String LETRAS_NORMALES = Character.toString((char) 18)
    final String ABRIR_LETRAS_NEGRITAS = ESC + Character.toString((char) 69)
    final String CERRAR_LETRAS_NEGRITAS = ESC + Character.toString((char) 70)
    final String CORTAR_PAGINA = ESC + Character.toString((char) 105)


    public void generarPreCuenta(long facturaid) {

        Factura factura = Factura.get(facturaid)

        String nombreRest = Parametro.findByCodigo(Parametro.APP_NOMBRE_RESTAURANTE).valor
        try {
            File file = File.createTempFile("salida-reimpresion-ticket", ".txt")
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
            bufferedWriter.write(StringUtils.center(nombreRest, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.TICKET_ENCABEZADO_1).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.TICKET_ENCABEZADO_2).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.PRECUENTA_ENCABEZADO_3).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write("------------------------------------------")
            bufferedWriter.newLine()
            bufferedWriter.write("No. Factura" + factura.id)
            bufferedWriter.newLine()
            bufferedWriter.write(""+factura.cliente.nombre)
            bufferedWriter.newLine()
            bufferedWriter.write(""+factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.creadoPor)
            bufferedWriter.newLine()
            bufferedWriter.write("Fecha: " + factura.dateCreated.format("dd-MM-yyyy HH:mm:ss"))
            bufferedWriter.newLine()
            bufferedWriter.write("------------------------------------------")
            bufferedWriter.newLine()
            bufferedWriter.write("Artículo    Cantidad    Valor   ")
            bufferedWriter.newLine()
            bufferedWriter.write("------------------------------------------")
            bufferedWriter.newLine()

            factura.listaFacturaDetalle.each {
                bufferedWriter.write(StringUtils.rightPad(it.ordenDetalle.plato.nombre, CANTIDAD_COLUMNAS_POS_42))
                bufferedWriter.newLine()
                bufferedWriter.write(StringUtils.center(it.ordenDetalle.cantidad+"\t"+it.ordenDetalle.importe, CANTIDAD_COLUMNAS_POS_42))
                bufferedWriter.newLine()
            }

           

            bufferedWriter.write("------------------------------------------")
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.rightPad("Total: " + factura.montoNeto, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.TICKET_PIEPAG_1).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.TICKET_PIEPAG_2).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.write(CORTAR_PAGINA)

            bufferedWriter.close()
            fileWriter.close()

            Path wiki_path = Paths.get(file.getPath());
            byte[] arregloByte = Files.readAllBytes(wiki_path);
            String tmp = new String(arregloByte, Charset.forName("UTF-8"));
            //El nombre de la cola sera la caja.
            //TODO: parametrizar cola
            brokerJmsService.enviarMensaje(Parametro.findByCodigo(Parametro.JMS_COLA).valor, tmp);

            file.delete()
        } catch (IOException e) {
            e.printStackTrace()
        }
    }
    public void generarFactura(long facturaid) {

        Factura factura = Factura.get(facturaid)

        String nombreRest = Parametro.findByCodigo(Parametro.APP_NOMBRE_RESTAURANTE).valor
        try {
            File file = File.createTempFile("salida-reimpresion-ticket", ".txt")
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
            bufferedWriter.write(StringUtils.center(nombreRest, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.TICKET_ENCABEZADO_1).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.TICKET_ENCABEZADO_2).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.TICKET_ENCABEZADO_3).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write("------------------------------------------")
            bufferedWriter.newLine()
            bufferedWriter.write("No. Factura" + factura.id)
            bufferedWriter.newLine()
            bufferedWriter.write(""+factura.cliente.nombre)
            bufferedWriter.newLine()
            bufferedWriter.write(""+factura.listaFacturaDetalle.first().ordenDetalle.clienteCuenta.creadoPor)
            bufferedWriter.newLine()
            bufferedWriter.write("Fecha: " + factura.dateCreated.format("dd-MM-yyyy HH:mm:ss"))
            bufferedWriter.newLine()
            bufferedWriter.write("------------------------------------------")
            bufferedWriter.newLine()
            bufferedWriter.write("Artículo    Cantidad    Valor   ")
            bufferedWriter.newLine()
            bufferedWriter.write("------------------------------------------")
            bufferedWriter.newLine()

            factura.listaFacturaDetalle.each {
                bufferedWriter.write(StringUtils.rightPad(it.ordenDetalle.plato.nombre, CANTIDAD_COLUMNAS_POS_42))
                bufferedWriter.newLine()
                bufferedWriter.write(StringUtils.center(it.ordenDetalle.cantidad+"\t"+it.ordenDetalle.importe, CANTIDAD_COLUMNAS_POS_42))
                bufferedWriter.newLine()
            }



            bufferedWriter.write("------------------------------------------")
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.rightPad("Total: " + factura.montoNeto, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.TICKET_PIEPAG_1).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.TICKET_PIEPAG_2).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.write(CORTAR_PAGINA)

            bufferedWriter.close()
            fileWriter.close()

            Path wiki_path = Paths.get(file.getPath());
            byte[] arregloByte = Files.readAllBytes(wiki_path);
            String tmp = new String(arregloByte, Charset.forName("UTF-8"));
            //El nombre de la cola sera la caja.
            //TODO: parametrizar cola
            brokerJmsService.enviarMensaje(Parametro.findByCodigo(Parametro.JMS_COLA).valor, tmp);

            file.delete()
        } catch (IOException e) {
            e.printStackTrace()
        }
    }



    public void generarComandaCocina(long cuentaId) {

        Cuenta cuenta = Cuenta.get(cuentaId)
        boolean clienteNotieneNuevoItem = true;

        String nombreRest = Parametro.findByCodigo(Parametro.APP_NOMBRE_RESTAURANTE).valor
        try {
            File file = File.createTempFile("salida-reimpresion-ticket", ".txt")
            FileWriter fileWriter = new FileWriter(file)
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
            bufferedWriter.write(StringUtils.center(nombreRest, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.TICKET_ENCABEZADO_1).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.TICKET_ENCABEZADO_2).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center(Parametro.findByCodigo(Parametro.COMANDA_ENCABEZADO_3).valor, CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.write("------------------------------------------")
            bufferedWriter.newLine()
            bufferedWriter.write("No. Cuenta " + cuenta.id)
            bufferedWriter.newLine()
            bufferedWriter.write("Mesas: ")
            bufferedWriter.newLine()
            cuenta.listaMesa.each {
                if(it.habilitado){
                    bufferedWriter.write("("+it.mesa.nombre+")")
                }
            }
            bufferedWriter.newLine()
            bufferedWriter.write("Atendido por: "+cuenta.usuario.nombre)
            bufferedWriter.newLine()
            bufferedWriter.write("Fecha: " + cuenta.dateCreated.format("dd-MM-yyyy HH:mm:ss"))
            bufferedWriter.newLine()
            cuenta.listaClienteCuenta.each {
                bufferedWriter.write("------------------------------------------")
                bufferedWriter.newLine()
                bufferedWriter.write("Cliente: "+it.nombre)
                bufferedWriter.newLine()
                bufferedWriter.write("------------------------------------------")
                bufferedWriter.newLine()
                bufferedWriter.write("Articulo      Cantidad")
                bufferedWriter.newLine()
                bufferedWriter.write("------------------------------------------")
                bufferedWriter.newLine()
                OrdenDetalle.findAllByClienteCuenta(it).each {
                    if(!it.impreso){
                        bufferedWriter.write(StringUtils.rightPad(it.plato.nombre, CANTIDAD_COLUMNAS_POS_42))
                        bufferedWriter.newLine()
                        bufferedWriter.write(StringUtils.rightPad("                "+it.cantidad as String, CANTIDAD_COLUMNAS_POS_42))
                        bufferedWriter.newLine()
                        it.impreso=true
                        it.save(flush:true,failOnError:true)
                        clienteNotieneNuevoItem = false
                    }

                }
                if(clienteNotieneNuevoItem){
                    bufferedWriter.newLine()
                    bufferedWriter.write("Este cliente no realizo un nuevo pedido!")
                    bufferedWriter.newLine()
                }
                clienteNotieneNuevoItem = true
                bufferedWriter.newLine()
                if(it.comentario!=null){
                    bufferedWriter.write("Comentario: "+it.comentario)
                }
                else{
                    bufferedWriter.write("Comentario: No existen comentarios.")

                }

                bufferedWriter.newLine()


            }
            bufferedWriter.write("------------------------------------------")
            bufferedWriter.newLine()
            bufferedWriter.write("------------------------------------------")
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.write(StringUtils.center("Vamo vamo!!!", CANTIDAD_COLUMNAS_POS_42))
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.newLine()
            bufferedWriter.write(CORTAR_PAGINA)

            bufferedWriter.close()
            fileWriter.close()

            Path wiki_path = Paths.get(file.getPath());
            byte[] arregloByte = Files.readAllBytes(wiki_path);
            String tmp = new String(arregloByte, Charset.forName("UTF-8"));
            //El nombre de la cola sera la caja.
            //TODO: parametrizar cola
            brokerJmsService.enviarMensaje(Parametro.findByCodigo(Parametro.JMS_COLA).valor, tmp);

            file.delete()
        } catch (IOException e) {
            e.printStackTrace()
        }
    }


}
