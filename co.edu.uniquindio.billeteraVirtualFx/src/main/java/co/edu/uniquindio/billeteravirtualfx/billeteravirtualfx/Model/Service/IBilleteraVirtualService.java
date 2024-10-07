package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Service;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.TransaccionException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.UsuarioException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Transaccion;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Usuario;

import java.util.ArrayList;

public interface IBilleteraVirtualService {
    public Usuario agregarUsuario(String nombre, String idUsuario, String email, String telefono, double saldo, String contrasena) throws UsuarioException;
    public Boolean eliminarUsuario(String id)throws UsuarioException;
    boolean actualizarUsuario(String idActual, Usuario usuario) throws UsuarioException;
    public boolean  verificarUsuarioExistente(String id) throws UsuarioException;
    public Usuario obtenerUsuario(String id);
    public ArrayList<Usuario> obtenerUsuario();

    boolean verificarCuentaExistente(String cuenta) throws TransaccionException;

    void crearTransaccion(Transaccion transaccion) throws TransaccionException;

    boolean verificarCredenciales(String correo, String contraseña);
}
