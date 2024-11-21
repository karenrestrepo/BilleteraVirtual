package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.TipoCuenta;

public record CuentaDto (

        String idCuenta,
        String nombreBanco,
        String numeroCuenta,
        TipoCuenta tipoCuenta


){
}
