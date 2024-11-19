package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;

public interface IPerfilUsuarioControllerService {

    boolean actualizarUsuario(String idActual, UsuarioDto usuarioDto);
}
