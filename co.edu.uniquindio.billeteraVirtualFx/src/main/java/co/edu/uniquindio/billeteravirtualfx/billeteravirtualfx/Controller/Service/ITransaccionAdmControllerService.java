package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;

import java.util.List;

public interface ITransaccionAdmControllerService {

        void registrarAcciones(String mensaje, int nivel, String accion);

        boolean crearTransaccion(TransaccionDto transaccionDto);

        List<TransaccionDto> obtenerTransacciones();

}
