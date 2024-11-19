package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;

import java.util.List;

public interface ICategoriaControllerService {

    List<CategoriaDto> obtenerCategoria();

    boolean crearCategoria(CategoriaDto categoriaDto);

    boolean eliminarCategoria(String id);

    boolean actualizarCategoria(String idActual, CategoriaDto categoriaDto);

}
