package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Consumidor.controller;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.TransaccionController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Cuenta;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Transaccion;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Confi.RabbitFactory;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Consumidor.controller.service.IConsumidorService;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

import static co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.util.Constantes.*;


public class ConsumidorController implements IConsumidorService, Runnable {

    private static final String RABBIT_HOST = "localhost";


    RabbitFactory rabbitFactory;
    ConnectionFactory connectionFactory;
    Thread hiloServicioConsumer1;

    private TransaccionController transaccionControllerService;

    //------------------------------ Singleton ------------------------------------------------
    private static class SingletonHolder {
        private final static ConsumidorController eINSTANCE = new ConsumidorController();
    }

    public static ConsumidorController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ConsumidorController() {
        initRabbitConnection();
        transaccionControllerService = new TransaccionController();
    }

    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("Conexión establecida con RabbitMQ");
    }

    public void consumirMensajesServicio1() {
        hiloServicioConsumer1 = new Thread(this);
        hiloServicioConsumer1.start();
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        if (currentThread == hiloServicioConsumer1) {
            consumirMensajes();
        }
    }

    private void consumirMensajes() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            // Declarar las colas
            channel.queueDeclare(QUEUE_AGREGAR_TRANSACCION, false, false, false, null);
            channel.queueDeclare(QUEUE_RETIRAR_TRANSACCION, false, false, false, null);
            channel.queueDeclare(QUEUE_TRANSFERIR_TRANSACCION, false, false, false, null);
            channel.queueDeclare(QUEUE_NUEVA_TRANSACCION, false, false, false, null);

            // Consumir los mensajes
            consumirMensajes(channel, QUEUE_AGREGAR_TRANSACCION);
            consumirMensajes(channel, QUEUE_RETIRAR_TRANSACCION);
            consumirMensajes(channel, QUEUE_TRANSFERIR_TRANSACCION);
            consumirMensajes(channel, QUEUE_NUEVA_TRANSACCION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void consumirMensajes(Channel channel, String queue) throws Exception {
        channel.basicConsume(queue, true, (consumerTag, delivery) -> {
            String mensaje = new String(delivery.getBody(), StandardCharsets.UTF_8);
            procesarTransaccion(mensaje);
        }, consumerTag -> {});
    }

    private void procesarTransaccion(String mensaje) {
        // Suponemos que el mensaje tiene el formato adecuado para generar un TransaccionDto
        // Por ejemplo: "id;fecha;tipo;monto;descripcion;cuentaOrigen;cuentaDestino;categoria"
        String[] partes = mensaje.split(";");
        if (partes.length == 8) {
            String id = partes[0];
            String fecha = partes[1];
            String tipo = partes[2];
            double monto = Double.parseDouble(partes[3]);
            String descripcion = partes[4];
            String cuentaOrigen = partes[5];
            String cuentaDestino = partes[6];
            String categoria = partes[7];

            // Crear un TransaccionDto
            TransaccionDto transaccionDto = new TransaccionDto(id, fecha, tipo, monto, descripcion, cuentaOrigen, cuentaDestino, categoria);

            // Enviar la transacción al controlador para su procesamiento
            procesarTransaccionDto(transaccionDto);
        } else {
            System.err.println("El mensaje recibido no tiene el formato correcto.");
        }
    }

    private void procesarTransaccionDto(TransaccionDto transaccionDto) {
        // Aquí podemos agregar lógica para realizar la acción correspondiente según el tipo de transacción
        switch (transaccionDto.tipo()) {
            case "AGREGAR", "RETIRAR", "TRANSFERIR":

                transaccionControllerService.crearTransaccion(transaccionDto);

                break;

            default:
                System.out.println("Tipo de transacción no reconocido");
        }
    }



}
