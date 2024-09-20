package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;

import java.util.List;

public interface IUsuaruoControllerService {
    List<UsuarioDto> obtenerUsuarios();

    boolean crearUsuario(UsuarioDto usuarioDto);

    boolean eliminarUsuario(String id);

    boolean actualizarUsuario(String idActual, UsuarioDto usuarioDto);

}
