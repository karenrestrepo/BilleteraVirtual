package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service.IUsuaruoControllerService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;

import java.util.List;

public class UsuarioController implements IUsuaruoControllerService {
    ModelFactory modelFactory;

    public UsuarioController(){
        modelFactory = ModelFactory.getInstance();
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return modelFactory.obtenerUsuarios();
    }


    @Override
    public boolean crearUsuario(UsuarioDto usuarioDto) {
        return modelFactory.crearUsuario(usuarioDto);
    }

    @Override
    public boolean eliminarUsuario(String id){
        return modelFactory.eliminarUsuario(id);
    }

    @Override
    public boolean actualizarUsuario(String idActual, UsuarioDto usuarioDto){
        return modelFactory.actualizarUsuario(idActual, usuarioDto);
    }
}
