package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.BillerteraVirtual;
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
        billerteraVirtual.getListaUsuarios().add(usuario);

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Esteban Rojas");
        usuario1.setIdUsuario("5678");
        usuario1.setEmail("Esteban@gmail.com");
        usuario1.setTelefono("748796");
        usuario1.setSaldo(100000);
        billerteraVirtual.getListaUsuarios().add(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Sara Mora");
        usuario2.setIdUsuario("5987");
        usuario2.setEmail("sara@gmail.com");
        usuario2.setTelefono("314268");
        usuario2.setSaldo(150000);
        billerteraVirtual.getListaUsuarios().add(usuario2);

        return billerteraVirtual;
    }
}
