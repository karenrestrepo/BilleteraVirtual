package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.BillerteraVirtual;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Usuario;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Persistencia {
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/BilleteraVirtualLog.txt";
    public static final String RUTA_ARCHIVO_MODELO_BILLETERAVIRTUAL_XML = "src/main/resources/persistencia/model.xml";

    public static final String RUTA_ARCHIVO_USUARIOS = "src/main/resources/persistencia/archivoUsiarios.txt";


    public static void cargarDatosArchivos(BillerteraVirtual billerteraVirtual) throws FileNotFoundException, IOException {
        //cargar archivo de clientes
        ArrayList<Usuario> usuariosCargados = cargarUsuarios();
        if(usuariosCargados.size() > 0)
            billerteraVirtual.getListaUsuarios().addAll(usuariosCargados);

        //cargar archivos empleados
       // ArrayList<Empleado> empleadosCargados = cargarEmpleados();
       // if(empleadosCargados.size() > 0)
        //    banco.getListaEmpleados().addAll(empleadosCargados);

        //cargar archivo transcciones

        //cargar archivo empleados

        //cargar archivo prestamo

    }

    //////////////////////////////Cargar//////////////////////////////
    private static ArrayList<Usuario> cargarUsuarios() throws FileNotFoundException, IOException
    {
        ArrayList<Usuario> usuarios =new ArrayList<Usuario>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_USUARIOS);
        String linea="";
        for (int i = 0; i < contenido.size(); i++)
        {
            linea = contenido.get(i);//juan,arias,125454,Armenia,uni1@,12454,125444
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(linea.split(",")[1]);
            usuario.setNombre(linea.split(",")[0]);
            usuario.setEmail(linea.split(",")[2]);
            usuario.setTelefono(linea.split(",")[3]);
            try {
                double saldo = Double.parseDouble(linea.split(",")[4].trim());
                usuario.setSaldo(saldo);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el saldo: " + e.getMessage());
            }
            usuario.setContrasena(linea.split(",")[5]);
            usuario.setTelefono(linea.split(",")[6]);
            usuarios.add(usuario);
        }
        return usuarios;
    }


//////////////////////guardar//////////////////////////////////


    public static void guardarUsuarios(ArrayList<Usuario> listaUsuarios) throws IOException {
        String contenido = "";
        for(Usuario usuario:listaUsuarios)
        {
            contenido+= usuario.getNombre()+","+usuario.getIdUsuario()+","+usuario.getEmail()
                    +","+usuario.getContrasena()+","+usuario.getTelefono()+","+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_USUARIOS, contenido, false);
    }

    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);

    }

    public static void guardarRecursoBilleteraXML(BillerteraVirtual billerteraVirtual) {
        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BILLETERAVIRTUAL_XML, billerteraVirtual);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
