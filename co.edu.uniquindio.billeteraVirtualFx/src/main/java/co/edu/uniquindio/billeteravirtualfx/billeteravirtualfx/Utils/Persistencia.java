package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.BillerteraVirtual;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Persistencia {
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/BilleteraVirtualLog.txt";
    public static final String RUTA_ARCHIVO_MODELO_BILLETERAVIRTUAL_XML = "src/main/resources/persistencia/model.xml";
    private static final String RUTA_ARCHIVO_MODELO_BILLETERA_BINARIO = "src/main/resources/persistencia/model.dat";
    private static final String RUTA_DIRECTORIO_RESPALDO = "src/main/resources/persistencia/Respaldo/";
    private static final String RUTA_DIRECTORIO_USUARIOS = "src/main/resources/persistencia/archivos/";
    private static final String PREFIJO_ARCHIVO_USUARIOS = "archivoUsuarios";
    private static final String EXTENSION_ARCHIVO = ".txt";


    private static void verificarDirectorios() {
        File directorioUsuarios = new File(RUTA_DIRECTORIO_USUARIOS);
        File directorioRespaldo = new File(RUTA_DIRECTORIO_RESPALDO);

        if (!directorioUsuarios.exists()) {
            directorioUsuarios.mkdirs();
        }
        if (!directorioRespaldo.exists()) {
            directorioRespaldo.mkdirs();
        }
    }

    public static String obtenerRutaArchivoMasReciente() {
        File directorio = new File(RUTA_DIRECTORIO_USUARIOS);
        File[] archivos = directorio.listFiles((dir, name) ->
                name.startsWith(PREFIJO_ARCHIVO_USUARIOS) && name.endsWith(EXTENSION_ARCHIVO));

        if (archivos == null || archivos.length == 0) {
            return RUTA_DIRECTORIO_USUARIOS + PREFIJO_ARCHIVO_USUARIOS + EXTENSION_ARCHIVO;
        }

        Arrays.sort(archivos, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
        return archivos[0].getPath();
    }

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
    public static ArrayList<Usuario> cargarUsuarios() throws FileNotFoundException, IOException
    {
        String rutaArchivo = obtenerRutaArchivoMasReciente();
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(rutaArchivo);

        for (String linea : contenido) {
            String[] datos = linea.split("@@");
            Usuario usuario = new Usuario();
            usuario.setNombre(datos[0]);
            usuario.setIdUsuario(datos[1]);
            usuario.setEmail(datos[2]);
            usuario.setSaldo(Double.parseDouble(datos[3]));
            usuario.setTelefono(datos[4]);
            if (datos.length > 5) {
                usuario.setContrasena(datos[5]);
            }
            usuarios.add(usuario);
        }
        return usuarios;

    }


//////////////////////guardar//////////////////////////////////


    public static void guardarUsuarios(ArrayList<Usuario> listaUsuarios) throws IOException {
        verificarDirectorios();
        String contenido = "";
        for(Usuario usuario:listaUsuarios)
        {
            contenido+= usuario.getNombre()+"@@"+
                    usuario.getIdUsuario()+"@@"+
                    usuario.getEmail()+"@@"+
                    usuario.getSaldo()+"@@"+
                    usuario.getTelefono()+"@@"+
                    usuario.getContrasena()+"\n";
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = PREFIJO_ARCHIVO_USUARIOS + "_" + timestamp + EXTENSION_ARCHIVO;

        // Rutas para el archivo principal y el respaldo
        String rutaArchivoPrincipal = RUTA_DIRECTORIO_USUARIOS + nombreArchivo;
        String rutaArchivoRespaldo = RUTA_DIRECTORIO_RESPALDO + nombreArchivo;

        // Guardar el archivo principal
        ArchivoUtil.guardarArchivo(rutaArchivoPrincipal, contenido, false);

        // Guardar la copia de respaldo
        ArchivoUtil.guardarArchivo(rutaArchivoRespaldo, contenido, false);

        // Eliminar archivos antiguos (tanto en la carpeta principal como en la de respaldo)
        limpiarArchivosAntiguos(RUTA_DIRECTORIO_USUARIOS);
        limpiarArchivosAntiguos(RUTA_DIRECTORIO_RESPALDO);
    }

    private static void limpiarArchivosAntiguos(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);
        File[] archivos = directorio.listFiles((dir, name) ->
                name.startsWith(PREFIJO_ARCHIVO_USUARIOS) && name.endsWith(EXTENSION_ARCHIVO));

        if (archivos != null && archivos.length > 1) {
            Arrays.sort(archivos, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

            // Mantener solo el archivo más reciente
            for (int i = 1; i < archivos.length; i++) {
                archivos[i].delete();
            }
        }
    }

    public static void guardarRecursoBancoBinario(BillerteraVirtual billerteraVirtual) {
        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_BILLETERA_BINARIO, billerteraVirtual);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);

    }


    public static BillerteraVirtual cargarRecursoBilleteraBinario() {
        BillerteraVirtual billerteraVirtual = null;

        try {
            billerteraVirtual = (BillerteraVirtual)ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_BILLETERA_BINARIO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return billerteraVirtual;
    }

    public static void guardarRecursoBilleteraXML(BillerteraVirtual billerteraVirtual) {
        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BILLETERAVIRTUAL_XML, billerteraVirtual);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static BillerteraVirtual cargarRecursoBilleteraXML() {
        BillerteraVirtual billerteraVirtual= null;
        try {
            billerteraVirtual =(BillerteraVirtual)ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BILLETERAVIRTUAL_XML);

        }catch (Exception e){
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return billerteraVirtual;
    }
}
