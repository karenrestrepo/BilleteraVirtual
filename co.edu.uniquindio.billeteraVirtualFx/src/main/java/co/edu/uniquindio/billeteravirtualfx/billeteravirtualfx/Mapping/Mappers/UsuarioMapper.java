package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Mappers;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Usuario;

import java.util.List;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Named("usuarioToUsuarioDto")

    UsuarioDto usuarioToUsuarioDto(Usuario usuario);

    @Named("usuarioDtoToUsuario")
    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);

    @IterableMapping(qualifiedByName = "usuarioToUsuarioDto")
    List<UsuarioDto> getUsuariosDto (List<Usuario> listaUsuarios);

    public static Usuario dtoToEntity(UsuarioDto usuarioDto) {
        return new Usuario(
                usuarioDto.idUsuario(),
                usuarioDto.nombre(),
                usuarioDto.email(),
                usuarioDto.telefono(),
                usuarioDto.saldo(),
                usuarioDto.contrasena()
        );
    }

    public static UsuarioDto entityToDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getSaldo(),
                usuario.getContrasena()
        );
    }

}
