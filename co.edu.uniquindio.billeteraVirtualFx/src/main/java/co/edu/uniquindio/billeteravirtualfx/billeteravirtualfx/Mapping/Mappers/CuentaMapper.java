package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Mappers;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CuentaDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Cuenta;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CuentaMapper {
    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);
    @Named("cuentaTocuentaDTO")
    CuentaDto cuentaTocuentaDTO(Cuenta cuenta);
    @Named("cuentaDtoToCuenta")
    Cuenta cuentaDtoToCuenta(CuentaDto cuentaDto);
    @IterableMapping(qualifiedByName = "cuentaTocuentaDTO")
    List<CuentaDto> getCuentaDto(List<Cuenta> listaCuentas);



}
