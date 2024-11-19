package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Productor;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Confi.RabbitFactory;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Productor.service.IProductorService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

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
        System.out.println("conexion establecidad");
    }

    @Override
    public void producirMensaje(String queue, String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}