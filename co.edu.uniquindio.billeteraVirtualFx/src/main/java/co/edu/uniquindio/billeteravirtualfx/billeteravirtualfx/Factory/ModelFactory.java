package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.UsuarioException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.Service.IModelFactoryService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Mappers.UsuarioMapper;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.BillerteraVirtual;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Service.IBilleteraVirtualService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Usuario;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils.BilleteraVirtualUtils;

import java.util.List;

public class ModelFactory implements IModelFactoryService {

    BillerteraVirtual billerteraVirtual;
    UsuarioMapper mapper = UsuarioMapper.INSTANCE;
    private static class SingletonHolder {
        private final static ModelFactory INSTANCE = new ModelFactory();
    }

    public static ModelFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ModelFactory() {
        cargarDatosBase();
    }

    private void cargarDatosBase() {
        billerteraVirtual = BilleteraVirtualUtils.inicializarDatos();
    }

    public BillerteraVirtual getBillerteraVirtual() {
        return billerteraVirtual;
    }

    public void setBillerteraVirtual(BillerteraVirtual billerteraVirtual) {
        this.billerteraVirtual = billerteraVirtual;
    }


    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return  mapper.getUsuariosDto(billerteraVirtual.getListaUsuarios());
    }

    @Override
    public boolean crearUsuario(UsuarioDto usuarioDto) {
        try{
            if(!billerteraVirtual.verificarUsuarioExistente(usuarioDto.idUsuario())) {
                Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
                getBillerteraVirtual().crearUsuario(usuario);
            }
            return true;
        }catch (UsuarioException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarUsuario(String id) {
        boolean idExiste = false;
        try {
            idExiste = getBillerteraVirtual().eliminarUsuario(id);
        } catch (UsuarioException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return idExiste;
    }

    @Override
    public boolean actualizarUsuario(String idActual, UsuarioDto usuarioDto) {
        try {
            Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
            getBillerteraVirtual().actualizarUsuario(idActual, usuario);
            return true;
        } catch (UsuarioException e) {
            e.printStackTrace();
            return false;
        }
    }
}

