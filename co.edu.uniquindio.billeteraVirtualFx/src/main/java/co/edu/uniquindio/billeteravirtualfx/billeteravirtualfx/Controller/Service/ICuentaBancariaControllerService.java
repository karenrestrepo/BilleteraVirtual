package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CuentaDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.PresupuestoDto;

import java.util.List;

public interface ICuentaBancariaControllerService {
    public List<CuentaDto> obtenercuentas();

    public boolean crearCuenta(CuentaDto cuentaDto);

    public boolean eliminarCuenta(String id);

    public boolean actualizarCuenta(String idActual, CuentaDto cuentaDto);
}
