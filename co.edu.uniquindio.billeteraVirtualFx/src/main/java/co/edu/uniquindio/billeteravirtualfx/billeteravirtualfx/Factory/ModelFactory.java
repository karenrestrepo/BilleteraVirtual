package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.*;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.Service.IModelFactoryService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.*;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Mappers.*;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.*;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils.Persistencia;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils.BilleteraVirtualUtils;

import java.io.IOException;
import java.util.List;

public class ModelFactory implements IModelFactoryService {

    BillerteraVirtual billerteraVirtual;
    Usuario usuario;
    Transaccion transaccion;

    UsuarioMapper mapper = UsuarioMapper.INSTANCE;
    TransaccionMapper transaccionMapper = TransaccionMapper.INSTANCE;
    PresupuestoMapper presupuestoMapper = PresupuestoMapper.INSTANCE;
    CategoriaMapper categoriaMapper = CategoriaMapper.INSTANCE;
    CuentaMapper cuentaMapper = CuentaMapper.INSTANCE;

    public static void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }

    public List<TransaccionDto> obtenerTransacciones() {
        System.out.println(usuario.getListaTransacciones());
        return  transaccionMapper.getTransaccionesDto(usuario.getListaTransacciones());

    }

    public List<PresupuestoDto> obtenerPresupuestos() {
        return  presupuestoMapper.getPresupuestoDto(usuario.getListaPresupuestos());
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
    	//cargarResourceBinario();
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
        Persistencia.guardarRecursoBilleteraBinario(billerteraVirtual);
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
        try {
            // Verificar si la cuenta de origen existe
            if (!billerteraVirtual.verificarTransaccionExistente(transaccionDto.cuentaOrigen())) {
                // Verificar si la transacción ya existe
                if (billerteraVirtual.transaccionExiste(transaccionDto.idTransaccion())) {
                    throw new TransaccionException("La transacción con el ID: " + transaccionDto.idTransaccion() + " ya existe.");
                }

                // Si las verificaciones pasaron, crear la transacción
                Transaccion transaccion = transaccionMapper.transaccionDtoToTransaccion(transaccionDto);

                // Iniciar la transacción para crear la nueva transacción en la billetera
                getBillerteraVirtual().crearTransaccion(transaccion);

                // Registrar acción en el sistema
                registrarAccionesSistema("Transacción realizada: " + transaccion.getIdTransaccion(), 1, "crearTransaccion");

                // Guardar cambios en la persistencia y los archivos
                Persistencia.guardarTransacciones(getBillerteraVirtual().getListaTransacciones());
                guardarResourceXML();
            }
            return true;
        } catch (TransaccionException e) {
            // Log de error
            registrarAccionesSistema(e.getMessage(), 3, "crearTransaccion");
            return false;
        } catch (IOException e) {
            // Si ocurre un error en la persistencia, revertir cambios y lanzar error
            registrarAccionesSistema("Error al guardar las transacciones: " + e.getMessage(), 3, "crearTransaccion");
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean ingresar(String correo, String contraseña) {
        try {
            Usuario currentUser = billerteraVirtual.verificarCredenciales(correo, contraseña);
            this.usuario = currentUser;
            billerteraVirtual.setUsuarioSeleccionado(currentUser);
            if (currentUser != null) {
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

    @Override
    public boolean verificarAdmin(String correo, String contraseña) {
        try {
            boolean credencialesValidas = billerteraVirtual.verificarAdmin(correo, contraseña);
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


    @Override
    public boolean crearPresupuesto(PresupuestoDto presupuestoDto){
        try{
            if(!billerteraVirtual.verificarPresupuestoExistente(presupuestoDto.idPresupuesto())) {
                Presupuesto presupuesto = presupuestoMapper.presupuestoDtoToPresupuesto(presupuestoDto);
                getBillerteraVirtual().crearPresupuesto(presupuesto);
                registrarAccionesSistema("Presupuesto realizada: "+ presupuesto.getIdPresupuesto(),1,"crear Presupuesto");
                Persistencia.guardarPresupuesto(getBillerteraVirtual().getListaPresupuestos());
                guardarResourceXML();
            }
            return true;
        }catch (PresupuestoException e){
            e.getMessage();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean eliminarPresupuesto(String id){
        boolean idExiste = false;
        try {
            idExiste = getBillerteraVirtual().eliminarPresupuesto(id);
            guardarResourceXML();
        } catch (PresupuestoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return idExiste;

    }

    @Override
    public boolean actualizarPresupuesto(String idActual, PresupuestoDto presupuestoDto){
        try {
            Presupuesto presupuesto = presupuestoMapper.presupuestoDtoToPresupuesto(presupuestoDto);
            getBillerteraVirtual().actualizarPresupuesto(idActual, presupuesto);
            guardarResourceXML();
            return true;
        } catch (PresupuestoException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<CategoriaDto> obtenerCategorias(){
        return  categoriaMapper.getCategoriaDto(usuario.getListaCategorias());

    }
    @Override
   public  boolean crearCategorias( CategoriaDto categoriaDto){
        try{
            if(!billerteraVirtual.verificarCategoriaExistente(categoriaDto.idCategoria())) {;
                Categoria categoria = categoriaMapper.categoriaDtoToCategoria(categoriaDto);
                getBillerteraVirtual().crearCategoria(categoria);
                Persistencia.guardarCategorias(getBillerteraVirtual().getListaCategorias());
                guardarResourceXML();
            }
            return true;
        }catch (CategoriaException e){
            e.getMessage();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean eliminarCategoria(String id){

        boolean idExiste = false;
        try {
            idExiste = getBillerteraVirtual().eliminarCategoria(id);
            guardarResourceXML();
        } catch (CategoriaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return idExiste;

    }

    @Override
    public boolean actualizarCategoria(String idActual, CategoriaDto categoriaDto){
        try {
            Categoria categoria = categoriaMapper.categoriaDtoToCategoria(categoriaDto);
            getBillerteraVirtual().actualizarCategoria(idActual, categoria);
            guardarResourceXML();
            return true;
        } catch (CategoriaException e) {
            e.printStackTrace();
            return false;
        }

    }
    public List<TransaccionDto> obtenerTransaccionesAdm() {
        return  transaccionMapper.getTransaccionesDto(billerteraVirtual.getListaTransacciones());
    }
    public List<CategoriaDto> obtenerCategoriasAdm() {
        return  categoriaMapper.getCategoriaDto(billerteraVirtual.getListaCategorias());
    }


    public List<CuentaDto> obtenerCuenta(){
        return  cuentaMapper.getCuentaDto(usuario.getListaCuentas());
    }
    public boolean crearCuenta( CuentaDto cuentaDto){
        try{
            if(!billerteraVirtual.verificarCuentaExistente(cuentaDto.idCuenta())) {;
                Cuenta cuenta = cuentaMapper.cuentaDtoToCuenta(cuentaDto);
                getBillerteraVirtual().crearCuenta(cuenta);
                Persistencia.guardarCuenta(getBillerteraVirtual().getListaCuentas());
                guardarResourceXML();
            }
            return true;
        }catch (CuentaException e){
            e.getMessage();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean eliminarCuenta(String id){
        boolean idExiste = false;
        try {
            idExiste = getBillerteraVirtual().eliminarCuenta(id);
            guardarResourceXML();
        } catch (CuentaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return idExiste;

    }

    public boolean actualizarCuenta(String idActual, CuentaDto cuentaDto){
        try {
            Cuenta cuenta = cuentaMapper.cuentaDtoToCuenta(cuentaDto);
            getBillerteraVirtual().actualizarCuenta(idActual, cuenta);
            guardarResourceXML();
            return true;
        } catch (CuentaException e) {
            e.printStackTrace();
            return false;
        }

    }


    private void guardarResourceXML() {
        Persistencia.guardarRecursoBilleteraXML(billerteraVirtual);
    }
}

