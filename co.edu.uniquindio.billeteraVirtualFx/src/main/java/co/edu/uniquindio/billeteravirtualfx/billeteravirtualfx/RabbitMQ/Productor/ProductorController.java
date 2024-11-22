package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Productor;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Confi.RabbitFactory;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Productor.service.IProductorService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

import static co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.util.Constantes.*;

public class ProductorController implements IProductorService {

    RabbitFactory rabbitFactory;
    ConnectionFactory connectionFactory;

    //------------------------------  Singleton ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        private final static ProductorController eINSTANCE = new ProductorController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ProductorController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ProductorController() {
        initRabbitConnection();
    }

    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("conexion de Transacción establecidad");
    }

    @Override
    public void producirMensaje(String queue, String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void procesarMensajeTransaccion(TransaccionDto transaccionDto){
        String mensaje = transaccionDto.idTransaccion() + ";" +
                transaccionDto.tipo() + ";" +
                transaccionDto.monto() + ";" +
                transaccionDto.descripcion() + ";" +
                transaccionDto.cuentaOrigen() + ";" +
                transaccionDto.cuentaDestino() + ";" +
                transaccionDto.categoria();

        switch (transaccionDto.tipo()) {
            case "AGREGAR":
                producirMensaje(QUEUE_AGREGAR_TRANSACCION, mensaje);
                break;
            case "RETIRAR":
                producirMensaje(QUEUE_RETIRAR_TRANSACCION, mensaje);
                break;
            case "TRANSFERIR":
                producirMensaje(QUEUE_TRANSFERIR_TRANSACCION, mensaje);
                break;
            default:
                producirMensaje(QUEUE_NUEVA_TRANSACCION, mensaje);
                break;
        }


    }
}