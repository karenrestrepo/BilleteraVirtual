package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.PresupuestoDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;

import java.util.List;

public interface IPresupuestoControllerService {
   public List<PresupuestoDto> obtenerPresupuesto();

    List<CategoriaDto> obtenerCategoria();

    public boolean crearPresupuesto(PresupuestoDto presupuestoDto);

    public boolean eliminarPresupuesto(String id);

    public boolean actualizarPresupuesto(String idActual, PresupuestoDto presupuestoDto);
}
