package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import java.util.List;
public interface ILoginController {
    List<UsuarioDto> obtenerUsuarios();

    boolean crearUsuario(UsuarioDto usuarioDto);

    boolean ingresar(String correo, String contraseña);
}
