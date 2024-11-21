package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service.ICuentaAdmControllerService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CuentaDto;

public class CuentaAdmController implements ICuentaAdmControllerService {
    ModelFactory modelFactory;

    public CuentaAdmController(){
        modelFactory = ModelFactory.getInstance();
    }

    @Override
    public boolean eliminarCuenta(String idCuenta) {
        return modelFactory.eliminarCuentaA(idCuenta);
    }

    @Override
    public void registrarAcciones(String mensaje, int nivel, String accion) {
        modelFactory.registrarAccionesSistema(mensaje, nivel, accion);
    }

    @Override
    public boolean crearCuenta(CuentaDto cuentaDto) {
        return modelFactory.crearCuentaAdm(cuentaDto);
    }
}
