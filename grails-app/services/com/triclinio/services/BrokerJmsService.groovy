package com.triclinio.services

import com.triclinio.domains.configuracion.Parametro
import grails.gorm.transactions.Transactional
import org.apache.activemq.ActiveMQConnectionFactory

import javax.jms.Connection
import javax.jms.JMSException
import javax.jms.MessageProducer
import javax.jms.Queue
import javax.jms.Session
import javax.jms.TextMessage

@Transactional
class BrokerJmsService {

    /**
     *
     * @param cola
     * @param mensajeEnviar
     * @throws javax.jms.JMSException
     */
    public void enviarMensaje(String cola, String mensajeEnviar) throws JMSException {

        String habilitado = Parametro.findByCodigo(Parametro.JMS_HABILITAR).valor
        if(habilitado != "1"){
            println("Parametro JMS HABILITADO NO DISPONIBLE")
           return;
        }

        /*String url = Parametro.encontrarParametroPorEmpresa(Parametro.JMS_URL, null)?.valor;
        String usuario = Parametro.encontrarParametroPorEmpresa(Parametro.JMS_USUARIO, null)?.valor;
        String password = Parametro.encontrarParametroPorEmpresa(Parametro.JMS_PASSWORD, null)?.valor;*/

        String url = Parametro.findByCodigo(Parametro.JMS_URL).valor
        String usuario = Parametro.findByCodigo(Parametro.JMS_USUARIO).valor
        String password = Parametro.findByCodigo(Parametro.JMS_PASSWORD).valor

        //Creando el connection factory indicando el host y puerto, en la trama el failover indica que reconecta de manera
        // automatica
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);

        //Crea un nuevo hilo cuando hacemos a conexión, que no se detiene cuando
        // aplicamos el metodo stop(), para eso tenemos que cerrar la JVM o
        // realizar un close().
        Connection connection = factory.createConnection(usuario, password);
        connection.start();

        // Creando una sesión no transaccional y automatica.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Creamos o nos connectamos a la una cola, por defecto ActiveMQ permite
        // la creación si no existe. Si la cola es del tipo Queue es acumula los mensajes, si es
        // del tipo topic es en el momento.
        Queue queue = session.createQueue(cola);
        //Topic topic = session.createTopic(cola);


        // Creando el objeto de referencia para enviar los mensajes.
        MessageProducer producer = session.createProducer(queue);


        String mensaje = mensajeEnviar;


        // Creando un simple mensaje de texto y enviando.
        TextMessage message = session.createTextMessage(mensaje);
        producer.send(message);

        //Desconectando la referencia.
        producer.close();
        session.close();
        connection.close();
    }
}
