package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Categoria;

public record PresupuestoDto(

        String idPresupuesto,
        String nombre,
        double montoAsignado,
        double montoGastado,
        String categoria

){

}
