package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;

import java.util.List;

public interface ITransaccionControllerService {
    List<TransaccionDto> obtenerTransacciones();

    boolean crearTransaccion(TransaccionDto transaccionDto);

    void registrarAcciones(String mensaje, int nivel, String accion);
}
