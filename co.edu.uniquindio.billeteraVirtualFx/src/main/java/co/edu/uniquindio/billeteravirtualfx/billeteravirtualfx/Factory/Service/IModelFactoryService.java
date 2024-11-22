package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Factory.Service;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.*;

import java.util.List;

public interface IModelFactoryService {
    List<UsuarioDto> obtenerUsuarios();
    boolean crearUsuario(UsuarioDto usuarioDto);

    boolean eliminarUsuario(String id);

    boolean actualizarUsuario(String idActual, UsuarioDto usuarioDto);
    boolean crearTransaccion(TransaccionDto transaccionDto);

    boolean ingresar(String correo, String contraseña);

    boolean verificarAdmin(String correo, String contraseña);
    boolean crearPresupuesto(PresupuestoDto presupuestoDto);

    boolean eliminarPresupuesto(String id);

    boolean actualizarPresupuesto(String idActual, PresupuestoDto presupuestoDto);

    List<CategoriaDto> obtenerCategorias();
    boolean crearCategorias( CategoriaDto categoriaDto);

    boolean eliminarCategoria(String id);

    boolean actualizarCategoria(String idActual, CategoriaDto categoriaDto);

    List<CuentaDto> obtenerCuenta();
    boolean crearCuenta( CuentaDto cuentaDto);

    boolean eliminarCuenta(String id);

    boolean actualizarCuenta(String idActual, CuentaDto cuentaDto);

    boolean crearCuentaAdm(CuentaDto cuentaDto);

    boolean eliminarCuentaA(String idCuenta);

    boolean crearTransaccionA(TransaccionDto transaccionDto);
}
