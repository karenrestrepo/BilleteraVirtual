package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Productor.service;

public interface IProductorService {
    void producirMensaje(String queue, String message);
}
