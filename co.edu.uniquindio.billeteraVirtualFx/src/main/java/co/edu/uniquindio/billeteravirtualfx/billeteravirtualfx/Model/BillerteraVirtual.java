package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.TransaccionException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.UsuarioException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Service.IBilleteraVirtualService;

import java.io.Serializable;
import java.util.ArrayList;

public class BillerteraVirtual implements IBilleteraVirtualService, Serializable {

    private static final long serialVersionUID = 1L;
    ArrayList<Cuenta> listaCuentas = new ArrayList<>();
    ArrayList<Transaccion> listaTransacciones = new ArrayList<>();
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    ArrayList<Presupuesto> listaPresupuestos = new ArrayList<>();
    ArrayList<Categoria> listaCategorias = new ArrayList<>();

    public BillerteraVirtual() {

    }

    public ArrayList<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(ArrayList<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public ArrayList<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }

    public void setListaTransacciones(ArrayList<Transaccion> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public ArrayList<Presupuesto> getListaPresupuestos() {
        return listaPresupuestos;
    }

    public void setListaPresupuestos(ArrayList<Presupuesto> listaPresupuestos) {
        this.listaPresupuestos = listaPresupuestos;
    }

    public ArrayList<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(ArrayList<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    @Override
    public Usuario agregarUsuario(String nombre, String idUsuario, String email, String telefono, double saldo, String contrasena) throws UsuarioException {
        Usuario nuevoUsuario = null;
        boolean usuarioExiste = verificarUsuarioExistente(idUsuario);
        if(usuarioExiste){
            throw new UsuarioException("El usuario con cedula: "+idUsuario+" ya existe");
        }else{
            nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setIdUsuario(idUsuario);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setTelefono(telefono);
            nuevoUsuario.setSaldo(0);
            nuevoUsuario.setContrasena(contrasena);
            getListaUsuarios().add(nuevoUsuario);
        }
        return nuevoUsuario;
    }

    public void crearUsuario(Usuario nuevoUsuario) throws UsuarioException{
        getListaUsuarios().add(nuevoUsuario);
    }

    @Override
    public Boolean eliminarUsuario(String id) throws UsuarioException {
        Usuario usuario = null;
        boolean idExiste = false;
        usuario = obtenerUsuario(id);
        if(usuario == null)
            throw new UsuarioException("El usuario a eliminar no existe");
        else{
            getListaUsuarios().remove(usuario);
            idExiste = true;
        }
        return idExiste;
    }

    @Override
    public boolean actualizarUsuario(String idActual, Usuario usuario) throws UsuarioException {
        Usuario usuarioExiste = obtenerUsuario(idActual);
        if(usuarioExiste == null)
            throw new UsuarioException("El usuario a actualizar no existe");
        else{
            usuarioExiste.setNombre(usuario.getNombre());
            usuarioExiste.setIdUsuario(usuario.getIdUsuario());
            usuarioExiste.setEmail(usuario.getEmail());
            usuarioExiste.setTelefono(usuario.getTelefono());
            usuarioExiste.setSaldo(usuario.getSaldo());
            usuarioExiste.setContrasena(usuario.getContrasena());
            return true;
        }
    }

    @Override
    public boolean verificarUsuarioExistente(String id) throws UsuarioException {
        if(usuarioExiste(id)){
            throw new UsuarioException("El usuario con cedula: "+id+" ya existe");
        }else{
            return false;
        }
    }

    private boolean usuarioExiste(String id) {
        boolean usuarioEncontrado = false;
        for (Usuario usuario : getListaUsuarios()) {
            if(usuario.getIdUsuario().equalsIgnoreCase(id)){
                usuarioEncontrado = true;
                break;
            }
        }
        return usuarioEncontrado;
    }

    @Override
    public Usuario obtenerUsuario(String id) {
        Usuario usuarioEncontrado = null;
        for (Usuario usuario : getListaUsuarios()) {
            if(usuario.getIdUsuario().equalsIgnoreCase(id)){
                usuarioEncontrado = usuario;
                break;
            }
        }
        return usuarioEncontrado;
    }

    @Override
    public ArrayList<Usuario> obtenerUsuario() {
        return getListaUsuarios();
    }

    @Override
    public boolean verificarCuentaExistente(String cuenta) throws TransaccionException {
        if(!transaccionExiste(cuenta)){
            throw new TransaccionException("La cuenta de origen: "+cuenta+" no existe");
        }else{
            return false;
        }
    }

    @Override
    public void crearTransaccion(Transaccion nuevaTransaccion) throws TransaccionException{
        getListaTransacciones().add(nuevaTransaccion);

    }

    @Override
    public boolean verificarCredenciales(String correo, String contraseña) {
        System.out.println("Intentando verificar credenciales para correo: " + correo);

        if (correo == null || contraseña == null) {
            System.out.println("Correo o contraseña es null");
            return false;
        }

        for (Usuario usuario : getListaUsuarios()) {
            System.out.println("Comprobando usuario: " + usuario.getEmail());
            System.out.println("Contraseña almacenada: " + usuario.getContrasena());

            if (usuario != null &&
                    usuario.getEmail() != null &&
                    usuario.getContrasena() != null &&
                    usuario.getEmail().equalsIgnoreCase(correo) &&
                    usuario.getContrasena().equals(contraseña)) {
                System.out.println("¡Coincidencia encontrada!");
                return true;
            }
        }
        System.out.println("No se encontró coincidencia");
        return false;
    }




    private boolean transaccionExiste(String cuenta) {
        boolean transaccionEncontrada = false;
        for (Cuenta cuenta1 : getListaCuentas()) {
            if(cuenta1.getNumeroCuenta().equalsIgnoreCase(cuenta)){
                transaccionEncontrada = true;
                break;
            }
        }
        return transaccionEncontrada;
    }
}
