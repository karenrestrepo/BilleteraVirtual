package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Confi;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeoutException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;

public class TransactionMessage {
    private String idTransaccion;
    private String fecha;
    private String tipo;
    private Double monto;
    private String descripcion;
    private String cuentaOrigen;
    private String cuentaDestino;
    private String categoria;

    // Constructor con TransaccionDto
    public TransactionMessage(TransaccionDto transaccion) {
        this.idTransaccion = transaccion.idTransaccion();
        this.fecha = transaccion.fecha();
        this.tipo = transaccion.tipo();
        this.monto = transaccion.monto();
        this.descripcion = transaccion.descripcion();
        this.cuentaOrigen = transaccion.cuentaOrigen();
        this.cuentaDestino = transaccion.cuentaDestino();
        this.categoria = transaccion.categoria();
    }

    // Serialización a JSON
    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
