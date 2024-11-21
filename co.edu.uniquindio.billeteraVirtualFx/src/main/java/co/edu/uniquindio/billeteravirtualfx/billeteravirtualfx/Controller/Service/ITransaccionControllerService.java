package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;

import java.util.List;

public interface ITransaccionControllerService {


    public List<CategoriaDto> obtenerCategoria();
    public List<TransaccionDto> obtenerTransacciones();

    boolean crearTransaccion(TransaccionDto transaccionDto);

    void registrarAcciones(String mensaje, int nivel, String accion);
}
