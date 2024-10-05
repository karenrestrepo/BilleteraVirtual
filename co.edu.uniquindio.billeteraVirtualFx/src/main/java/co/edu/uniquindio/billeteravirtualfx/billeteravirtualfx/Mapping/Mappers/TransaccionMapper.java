package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Mappers;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Transaccion;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Usuario;
import org.mapstruct.IterableMapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface TransaccionMapper {
    TransaccionMapper INSTANCE = Mappers.getMapper(TransaccionMapper.class);;

    @Named("transaccionToTransaccionDto")
    TransaccionDto transaccionToTransaccionDto(Transaccion transaccion);

    Transaccion transaccionDtoToTransaccion(TransaccionDto transaccionDto);

    @IterableMapping(qualifiedByName = "transaccionToTransaccionDto")
    List<TransaccionDto> getTransaccionesDto (List<Transaccion> listaTransacciones);

}
