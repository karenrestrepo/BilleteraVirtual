package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Service;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.CategoriaException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.PresupuestoException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.TransaccionException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.UsuarioException;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.*;

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

    Usuario verificarCredenciales(String correo, String contraseña);

    Cuenta obtenerCuentaPorNumero(String dato);

    boolean verificarAdmin(String correo, String contraseña);

    public Presupuesto agregarPresupuesto(String idPresupuesto, String nombre, double montoAsignado, double montoGastado, String categoria) throws PresupuestoException;

    public Boolean eliminarPresupuesto(String id)throws PresupuestoException;
    boolean actualizarPresupuesto(String idActual, Presupuesto presupuesto) throws PresupuestoException;
    public boolean  verificarPresupuestoExistente(String id) throws PresupuestoException;
    public Presupuesto obtenerPresupuesto(String id);

    void crearCategoria(Categoria categoria) throws CategoriaException;
    public Boolean eliminarCategoria(String id)throws CategoriaException;
    boolean actualizarCategoria(String idActual, Categoria categoria) throws CategoriaException;
    public boolean  verificarCategoriaExistente(String id) throws CategoriaException;
    public Categoria obtenerCategoria(String id);
}
