package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Mappers;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Transaccion;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransaccionMapper {
    TransaccionMapper INSTANCE = Mappers.getMapper(TransaccionMapper.class);

    @Named("transaccionToTransaccionDto")
    @Mapping(source = "cuentaOrigen.numeroCuenta ",target = "cuentaOrigen")
    @Mapping(source = "cuentaDestino.numeroCuenta", target = "cuentaDestino")
    TransaccionDto transaccionToTransaccionDto(Transaccion transaccion);

    @Named("transaccionDtoToTransaccion")

    @Mapping(source = "cuentaOrigen",target = "cuentaOrigen.numeroCuenta")
    @Mapping(source = "cuentaDestino", target = "cuentaDestino.numeroCuenta")
    Transaccion transaccionDtoToTransaccion(TransaccionDto transaccionDto);

    @IterableMapping(qualifiedByName = "transaccionToTransaccionDto")
    List<TransaccionDto> getTransaccionesDto (List<Transaccion> listaTransacciones);

}
