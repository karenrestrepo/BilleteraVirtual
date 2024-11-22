package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Productor;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.BillerteraVirtual;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Cuenta;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Confi.TransactionMessage;
import com.google.gson.Gson;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TransactionService {
    private static final String QUEUE_TRANSACTION = "transaccion";
    private ConnectionFactory connectionFactory;
    BillerteraVirtual billerteraVirtual;

    public TransactionService() {
        initRabbitConnection();
    }

    private void initRabbitConnection() {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
    }

    public void processTransaction(TransactionMessage transaction) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            // Declarar cola duradera
            channel.queueDeclare(QUEUE_TRANSACTION, true, false, false, null);

            // Convertir mensaje a JSON
            String jsonMessage = new Gson().toJson(transaction);

            // Publicar mensaje con persistencia
            channel.basicPublish("",
                    QUEUE_TRANSACTION,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    jsonMessage.getBytes());

            // Registro de transacción procesada
            System.out.println("Transacción procesada: " + jsonMessage);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    // Consumidor de mensajes de transacción
    public void startTransactionConsumer() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_TRANSACTION, true, false, false, null);
            channel.basicQos(1);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");

                try {
                    // Convertir mensaje JSON a objeto de transacción
                    TransactionMessage transaction =
                            new Gson().fromJson(message, TransactionMessage.class);

                    // Lógica de procesamiento de transacción
                    procesarTransaccionDetallada(transaction);

                    // Confirmar procesamiento
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                } catch (Exception e) {
                    // Manejar errores
                    channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
                    e.printStackTrace();
                }
            };

            // Consumir mensajes
            channel.basicConsume(QUEUE_TRANSACTION, false, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    // Método de procesamiento detallado de transacción
    private void procesarTransaccionDetallada(TransactionMessage transaction) {
        // Implementar lógica de negocio
        switch(transaction.getTipo()) {
            case "DEPOSITO":
                realizarDeposito(transaction);
                break;
            case "RETIRO":
                realizarRetiro(transaction);
                break;
            case "TRANSFERENCIA":
                realizarTransferencia(transaction);
                break;
        }

        // Notificar usuario
        notificarUsuario(transaction);
    }

    private void realizarDeposito(TransactionMessage transaction) {
        // Validar que el monto sea positivo
        if (transaction.getMonto() > 0) {
            // Buscar cuenta por cuentaDestino
            Cuenta cuenta = buscarCuenta(transaction.getCuentaDestino());

            if (cuenta != null) {
                // Incrementar saldo
                cuenta.setSaldo(cuenta.getSaldo() + transaction.getMonto());

                // Registrar movimiento
                registrarMovimiento(cuenta, "DEPOSITO", transaction.getMonto());

                System.out.println("Depósito realizado: $" + transaction.getMonto());
            } else {
                System.out.println("Cuenta de destino no encontrada");
            }
        }
    }

    private void realizarRetiro(TransactionMessage transaction) {
        // Validar que el monto sea positivo
        if (transaction.getMonto() > 0) {
            // Buscar cuenta por cuentaOrigen
            Cuenta cuenta = buscarCuenta(transaction.getCuentaOrigen());

            if (cuenta != null) {
                // Verificar fondos suficientes
                if (cuenta.getSaldo() >= transaction.getMonto()) {
                    // Decrementar saldo
                    cuenta.setSaldo(cuenta.getSaldo() - transaction.getMonto());

                    // Registrar movimiento
                    registrarMovimiento(cuenta, "RETIRO", transaction.getMonto());

                    System.out.println("Retiro realizado: $" + transaction.getMonto());
                } else {
                    System.out.println("Saldo insuficiente");
                }
            } else {
                System.out.println("Cuenta de origen no encontrada");
            }
        }
    }

    private void realizarTransferencia(TransactionMessage transaction) {
        // Validar que el monto sea positivo
        if (transaction.getMonto() > 0) {
            // Buscar cuentas de origen y destino
            Cuenta cuentaOrigen = buscarCuenta(transaction.getCuentaOrigen());
            Cuenta cuentaDestino = buscarCuenta(transaction.getCuentaDestino());

            if (cuentaOrigen != null && cuentaDestino != null) {
                // Verificar fondos suficientes
                if (cuentaOrigen.getSaldo() >= transaction.getMonto()) {
                    // Decrementar saldo en cuenta origen
                    cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - transaction.getMonto());

                    // Incrementar saldo en cuenta destino
                    cuentaDestino.setSaldo(cuentaDestino.getSaldo() + transaction.getMonto());

                    // Registrar movimientos
                    registrarMovimiento(cuentaOrigen, "TRANSFERENCIA_ENVIADA", transaction.getMonto());
                    registrarMovimiento(cuentaDestino, "TRANSFERENCIA_RECIBIDA", transaction.getMonto());

                    System.out.println("Transferencia realizada: $" + transaction.getMonto());
                } else {
                    System.out.println("Saldo insuficiente para transferencia");
                }
            } else {
                System.out.println("Cuenta de origen o destino no encontrada");
            }
        }
    }

    // Métodos auxiliares (necesitarás implementarlos según tu modelo de datos)
    private Cuenta buscarCuenta(String numeroCuenta) {
        return billerteraVirtual.obtenerCuentaA(numeroCuenta);
    }

    private void registrarMovimiento(Cuenta cuenta, String tipoMovimiento, Double monto) {
        // Implementar registro de movimiento
        // Puede ser guardando en base de datos, generando un log, etc.
    }

    private void notificarUsuario(TransactionMessage transaction) {
        // Método de notificación al usuario
        System.out.println("Notificación: Transacción " +
                transaction.getTipo() +
                " procesada exitosamente");
    }
}
