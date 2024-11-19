package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service.IPresupuestoControllerService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.PresupuestoDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;

import java.util.List;

public class PresupuestoController implements IPresupuestoControllerService {

    ModelFactory modelFactory;

    public PresupuestoController(){
        modelFactory = ModelFactory.getInstance();
    }

   @Override
    public List<PresupuestoDto> obtenerPresupuesto(){

       return modelFactory.obtenerPresupuestos();
   }

   @Override
   public boolean crearPresupuesto(PresupuestoDto presupuestoDto){
       return modelFactory.crearPresupuesto(presupuestoDto);
    }

    @Override
    public boolean eliminarPresupuesto(String id){
        return modelFactory.eliminarPresupuesto(id);
    }

    @Override
    public boolean actualizarPresupuesto(String idActual, PresupuestoDto presupuestoDto){
        return modelFactory.actualizarPresupuesto(idActual, presupuestoDto);
    }

    public void registrarAcciones(String mensaje, int nivel, String accion) {
        ModelFactory.registrarAccionesSistema(mensaje, nivel, accion);
    }
}
