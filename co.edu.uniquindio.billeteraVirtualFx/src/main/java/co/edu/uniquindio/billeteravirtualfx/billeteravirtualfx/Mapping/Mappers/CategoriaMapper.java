package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Mappers;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Categoria;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoriaMapper {

    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);
    @Named("categoriaToCategoriaDTO")
    CategoriaDto categoriaToCategoriaDTO(Categoria categoria);

    @Named("categoriaDtoToCategoria")
    Categoria categoriaDtoToCategoria(CategoriaDto categoriaDto);

    @IterableMapping(qualifiedByName = "categoriaToCategoriaDTO")
    List<CategoriaDto> getCategoriaDto(List<Categoria> listaCategorias);

}
