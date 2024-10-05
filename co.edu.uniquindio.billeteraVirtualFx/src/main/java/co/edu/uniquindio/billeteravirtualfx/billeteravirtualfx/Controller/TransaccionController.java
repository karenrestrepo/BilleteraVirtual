package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service.ITransaccionControllerService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.ModelFactory;
import java.util.List;

public class TransaccionController implements ITransaccionControllerService {
    ModelFactory modelFactory;

    public TransaccionController(){
        modelFactory = ModelFactory.getInstance();
    }
    @Override
    public List<TransaccionDto> obtenerTransacciones() {
        return modelFactory.obtenerTransacciones();
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
