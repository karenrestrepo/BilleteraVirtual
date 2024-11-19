package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service;
import java.util.List;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;

public interface ITransaccionAdmControllerService {
    void registrarAcciones(String mensaje, int nivel, String accion);

    boolean crearTransaccion(TransaccionDto transaccionDto);

    List<TransaccionDto> obtenerTransacciones();
}
