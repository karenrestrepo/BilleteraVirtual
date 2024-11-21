package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CuentaDto;

public interface ICuentaAdmControllerService {
    boolean eliminarCuenta(String idCuenta);

    void registrarAcciones(String mensaje, int nivel, String accion);

    boolean crearCuenta(CuentaDto cuentaDto);
}
