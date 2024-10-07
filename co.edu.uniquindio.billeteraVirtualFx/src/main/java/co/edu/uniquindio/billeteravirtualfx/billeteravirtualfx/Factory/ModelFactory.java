package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.TransaccionException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.UsuarioException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.Service.IModelFactoryService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Mappers.TransaccionMapper;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Mappers.UsuarioMapper;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.BillerteraVirtual;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Transaccion;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils.Persistencia;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Usuario;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils.BilleteraVirtualUtils;

import java.io.IOException;
import java.util.List;

public class ModelFactory implements IModelFactoryService {

    BillerteraVirtual billerteraVirtual;
    UsuarioMapper mapper = UsuarioMapper.INSTANCE;
    TransaccionMapper transaccionMapper = TransaccionMapper.INSTANCE;

    public static void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }

    public List<TransaccionDto> obtenerTransacciones() {
        return  transaccionMapper.getTransaccionesDto(billerteraVirtual.getListaTransacciones());
    }



    private static class SingletonHolder {
        private final static ModelFactory INSTANCE = new ModelFactory();
    }

    public static ModelFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ModelFactory() {

        //1. inicializar datos y luego guardarlo en archivos
        System.out.println("invocación clase singleton");
       //cargarDatosBase();
       //salvarDatosPrueba();

        //2. Cargar los datos de los archivos
		//cargarDatosDesdeArchivos();

        //3. Guardar y Cargar el recurso serializable binario
    	cargarResourceBinario();
		//guardarResourceBinario();

        //4. Guardar y Cargar el recurso serializable XML
        //guardarResourceXML();
        cargarResourceXML();

        //Siempre se debe verificar si la raiz del recurso es null

        if(billerteraVirtual == null){
            cargarDatosBase();
            guardarResourceXML();
        }
        registrarAccionesSistema("Inicio de sesión", 1, "inicioSesión");

    }

    private void cargarResourceXML() {
        billerteraVirtual = Persistencia.cargarRecursoBilleteraXML();
    }

    private void cargarResourceBinario() {
        billerteraVirtual = Persistencia.cargarRecursoBilleteraBinario();
    }

    private void guardarResourceBinario() {
        Persistencia.guardarRecursoBancoBinario(billerteraVirtual);
    }


    private void cargarDatosDesdeArchivos() {
        billerteraVirtual = new BillerteraVirtual();
        try {
            Persistencia.cargarDatosArchivos(billerteraVirtual);
        }catch (IOException e){
            throw new RuntimeException(e);

        }

    }

    private void salvarDatosPrueba() {
        try {
            Persistencia.guardarUsuarios(getBillerteraVirtual().getListaUsuarios());

        }catch (IOException e){
          throw new RuntimeException(e);
       }
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
                Persistencia.guardarUsuarios(getBillerteraVirtual().getListaUsuarios());
                guardarResourceXML();
            }
            return true;
        }catch (UsuarioException e){
            e.getMessage();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean eliminarUsuario(String id) {
        boolean idExiste = false;
        try {
            idExiste = getBillerteraVirtual().eliminarUsuario(id);
            guardarResourceXML();
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
            guardarResourceXML();
            return true;
        } catch (UsuarioException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean crearTransaccion(TransaccionDto transaccionDto) {
        try{
            if(!billerteraVirtual.verificarCuentaExistente(transaccionDto.cuentaOrigen())) {
                Transaccion transaccion = transaccionMapper.transaccionDtoToTransaccion(transaccionDto);
                getBillerteraVirtual().crearTransaccion(transaccion);
                registrarAccionesSistema("Transacción realizada: "+ transaccion.getIdTransaccion(),1,"crearTransaccion");
                Persistencia.guardarTransacciones(getBillerteraVirtual().getListaTransacciones());
                guardarResourceXML();
            }
            return true;
        }catch (TransaccionException e){
            e.getMessage();
            registrarAccionesSistema(e.getMessage(),3,"crearTransaccion");
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean ingresar(String correo, String contraseña) {
        try {
            boolean credencialesValidas = billerteraVirtual.verificarCredenciales(correo, contraseña);
            if (credencialesValidas) {
                registrarAccionesSistema("Inicio de sesión exitoso para: " + correo, 1, "ingresar");
                return true;
            } else {
                registrarAccionesSistema("Intento de inicio de sesión fallido para: " + correo, 2, "ingresar");
                return false;
            }
        } catch (Exception e) {
            registrarAccionesSistema("Error durante el inicio de sesión: " + e.getMessage(), 3, "ingresar");
            return false;
        }
    }

    private void guardarResourceXML() {
        Persistencia.guardarRecursoBilleteraXML(billerteraVirtual);
    }
}

