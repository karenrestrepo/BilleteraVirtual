package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Mappers;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.PresupuestoDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Presupuesto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PresupuestoMapper {

    PresupuestoMapper INSTANCE = Mappers.getMapper(PresupuestoMapper.class);

    @Named("presupuestoToPresupuestoDTO")
    PresupuestoDto presupuestoToPresupuestoDTO(Presupuesto presupuesto);
    @Named("presupuestoDtoToPresupuesto")
    Presupuesto presupuestoDtoToPresupuesto(PresupuestoDto presupuestoDto);

    @IterableMapping(qualifiedByName = "presupuestoToPresupuestoDTO" )
    List<PresupuestoDto> getPresupuestoDto(List<Presupuesto> listaPresupuestos);

}
