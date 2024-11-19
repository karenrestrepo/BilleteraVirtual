package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.Service.ICategoriaControllerService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CategoriaDto;

import java.util.List;


public class CategoriaController implements ICategoriaControllerService {


    ModelFactory modelFactory;

    public CategoriaController() {
        modelFactory = ModelFactory.getInstance();
    }

    @Override
    public List<CategoriaDto> obtenerCategoria(){
        return modelFactory.obtenerCategorias();
    }

    @Override
    public boolean crearCategoria(CategoriaDto categoriaDto){
        return modelFactory.crearCategorias(categoriaDto);
    }

    @Override
    public boolean eliminarCategoria(String id){
        return modelFactory.eliminarCategoria(id);
    }

    @Override
    public boolean actualizarCategoria(String idActual, CategoriaDto categoriaDto){
        return modelFactory.actualizarCategoria(idActual, categoriaDto);

    }
}
