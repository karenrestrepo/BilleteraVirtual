package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service.ITransaccionAdmControllerService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;

import java.util.List;

public class TransaccionAdmController implements ITransaccionAdmControllerService {
    ModelFactory modelFactory;

    public TransaccionAdmController() {
        modelFactory = ModelFactory.getInstance();
    }

    @Override
    public List<TransaccionDto> obtenerTransacciones() {
        return modelFactory.obtenerTransaccionesAdm();
    }

    @Override
    public boolean crearTransaccion(TransaccionDto transaccionDto) {

        return modelFactory.crearTransaccion(transaccionDto);
    }

    @Override
    public void registrarAcciones(String mensaje, int nivel, String accion) {
        ModelFactory.registrarAccionesSistema(mensaje, nivel, accion);
    }
}
