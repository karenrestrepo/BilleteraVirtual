package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service.ICuentaBancariaControllerService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CuentaDto;

import java.util.List;

public class CuentaBancariaController implements ICuentaBancariaControllerService {

    ModelFactory modelFactory;

    public CuentaBancariaController(){
        modelFactory = ModelFactory.getInstance();
    }

    @Override
    public List<CuentaDto> obtenercuentas(){
        return modelFactory.obtenerCuenta();
    }

   @Override
   public boolean crearCuenta(CuentaDto cuentaDto){
       return modelFactory.crearCuenta(cuentaDto);

    }

    @Override
    public boolean eliminarCuenta(String id){
        return modelFactory.eliminarCuenta(id);

    }

    @Override
    public boolean actualizarCuenta(String idActual, CuentaDto cuentaDto){
        return modelFactory.actualizarCuenta(idActual, cuentaDto);
    }
    public void registrarAcciones(String mensaje, int nivel, String accion) {
        ModelFactory.registrarAccionesSistema(mensaje, nivel, accion);
    }

}
