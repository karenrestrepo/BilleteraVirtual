package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model;

public class Presupuesto {

    private String idPresupuesto;
    private String nombre;
    private double montoAsignado;
    private double montoGastado;
    private String categoria;

    public Presupuesto() {
    }

    public String getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(String idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public double getMontoAsignado() {
        return montoAsignado;
    }

    public void setMontoAsignado(double montoAsignado) {
        this.montoAsignado = montoAsignado;
    }

    public double getMontoGastado() {
        return montoGastado;
    }

    public void setMontoGastado(double montoGastado) {
        this.montoGastado = montoGastado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
