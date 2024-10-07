package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;

import java.util.List;

public interface IModelFactoryService {
    List<UsuarioDto> obtenerUsuarios();
    boolean crearUsuario(UsuarioDto usuarioDto);

    boolean eliminarUsuario(String id);

    boolean actualizarUsuario(String idActual, UsuarioDto usuarioDto);
    boolean crearTransaccion(TransaccionDto transaccionDto);

    boolean ingresar(String correo, String contraseña);

}
