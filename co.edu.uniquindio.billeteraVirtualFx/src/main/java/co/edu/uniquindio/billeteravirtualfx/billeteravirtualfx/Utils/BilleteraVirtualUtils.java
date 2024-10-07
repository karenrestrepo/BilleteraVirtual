package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.BillerteraVirtual;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Cuenta;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Usuario;

public class BilleteraVirtualUtils {

    public static BillerteraVirtual inicializarDatos(){
        BillerteraVirtual billerteraVirtual = new BillerteraVirtual();

        Usuario usuario = new Usuario();
        usuario.setNombre("Katherine Serna");
        usuario.setIdUsuario("1234");
        usuario.setEmail("kathe@gmail.com");
        usuario.setTelefono("300627");
        usuario.setSaldo(100000);
        usuario.setContrasena("katherine01");
        billerteraVirtual.getListaUsuarios().add(usuario);

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Esteban Rojas");
        usuario1.setIdUsuario("5678");
        usuario1.setEmail("Esteban@gmail.com");
        usuario1.setTelefono("748796");
        usuario1.setSaldo(100000);
        usuario1.setContrasena("esteban01");
        billerteraVirtual.getListaUsuarios().add(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Sara Mora");
        usuario2.setIdUsuario("5987");
        usuario2.setEmail("sara@gmail.com");
        usuario2.setTelefono("314268");
        usuario2.setSaldo(150000);
        usuario2.setContrasena("sara01");
        billerteraVirtual.getListaUsuarios().add(usuario2);

        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta("0110");
        cuenta.setNumeroCuenta("4566555");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setNombreBanco("Davivienda");
        usuario.getListaCuentas().add(cuenta);

        Cuenta cuenta1 = new Cuenta();
        cuenta1.setIdCuenta("0111");
        cuenta1.setNumeroCuenta("9654445");
        cuenta1.setTipoCuenta("Ahorros");
        cuenta1.setNombreBanco("Bancolombia");
        usuario1.getListaCuentas().add(cuenta1);

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setIdCuenta("0112");
        cuenta2.setNumeroCuenta("4500555");
        cuenta2.setTipoCuenta("Corriente");
        cuenta2.setNombreBanco("Davivienda");
        usuario2.getListaCuentas().add(cuenta2);

        return billerteraVirtual;
    }
}
