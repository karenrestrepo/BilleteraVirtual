package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service.ILoginController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;

import java.util.List;

public class LoginController implements ILoginController {
    ModelFactory modelFactory;

    public LoginController(){
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
    public boolean ingresar(String correo, String contraseña) {
        return modelFactory.ingresar(correo, contraseña);
    }
}
