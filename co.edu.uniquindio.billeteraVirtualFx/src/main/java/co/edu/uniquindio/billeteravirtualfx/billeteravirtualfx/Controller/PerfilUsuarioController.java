package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service.IPerfilUsuarioControllerService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;

public class PerfilUsuarioController implements IPerfilUsuarioControllerService {

    ModelFactory modelFactory;

    public PerfilUsuarioController(){
        modelFactory = ModelFactory.getInstance();
    }
    @Override
    public boolean actualizarUsuario(String idActual, UsuarioDto usuarioDto){
        return modelFactory.actualizarUsuario(idActual, usuarioDto);
    }

}
