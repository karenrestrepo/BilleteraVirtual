package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Cuenta;

public record TransaccionDto (
        String idTransaccion,
        String fecha,
        String tipo,
        double monto,
        String descripcion,
        String cuentaOrigen,
        String cuentaDestino
){

}

