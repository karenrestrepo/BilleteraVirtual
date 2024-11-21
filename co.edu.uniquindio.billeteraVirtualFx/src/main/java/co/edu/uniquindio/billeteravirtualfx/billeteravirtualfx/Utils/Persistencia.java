package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.*;

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
    private static final String RUTA_ARCHIVO_TRANSACCION_BILLETERA_BINARIO = "src/main/resources/persistencia/transaccion.dat";
    private static final String RUTA_DIRECTORIO_RESPALDO = "src/main/resources/persistencia/Respaldo/";
    private static final String RUTA_DIRECTORIO_USUARIOS = "src/main/resources/persistencia/archivos/";
    private static final String PREFIJO_ARCHIVO_USUARIOS = "archivoUsuarios";
    private static final String RUTA_DIRECTORIO_CATEGORIA= "src/main/resources/persistencia/archivos/";
    private static final String PREFIJO_ARCHIVO_CATEGORIA = "archivoCategoria";
    private static final String RUTA_DIRECTORIO_PRESUPUESTO = "src/main/resources/persistencia/archivos/";
    private static final String PREFIJO_ARCHIVO_PRESUPUESTO = "archivoPresupuesto";
    private static final String RUTA_DIRECTORIO_TRANSACCION = "src/main/resources/persistencia/archivos/";
    private static final String PREFIJO_ARCHIVO_TRANSACCION = "archivoTransacciones";
    private static final String RUTA_DIRECTORIO_CUENTA = "src/main/resources/persistencia/archivos/";
    private static final String PREFIJO_ARCHIVO_CUENTA = "archivoCuentas";
    private static final String EXTENSION_ARCHIVO = ".txt";


    private static void verificarDirectoriosUsuarios() {
        File directorioUsuarios = new File(RUTA_DIRECTORIO_USUARIOS);
        File directorioRespaldo = new File(RUTA_DIRECTORIO_RESPALDO);

        if (!directorioUsuarios.exists()) {
            directorioUsuarios.mkdirs();
        }
        if (!directorioRespaldo.exists()) {
            directorioRespaldo.mkdirs();
        }
    }

    public static String obtenerRutaArchivoMasRecienteUsuario() {
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
       if (usuariosCargados.size() > 0) {
            billerteraVirtual.getListaUsuarios().addAll(usuariosCargados);
       }
        ArrayList<Transaccion> transaccionesCargadas = cargarTransacciones();
        if (transaccionesCargadas.size() > 0) {
            billerteraVirtual.getListaTransacciones().addAll(transaccionesCargadas);
        }

        ArrayList<Presupuesto> presupuestosCargados = cargarPresupuesto();
        if (presupuestosCargados.size() > 0) {
            billerteraVirtual.getListaPresupuestos().addAll(presupuestosCargados);
        }

        ArrayList<Categoria> categoriasCargados = cargarCategoria();
        if (categoriasCargados.size() > 0) {
            billerteraVirtual.getListaCategorias().addAll(categoriasCargados);
        }
        ArrayList<Cuenta> cuentasCargados = cargarCuenta();
        if (cuentasCargados.size() > 0) {
            billerteraVirtual.getListaCuentas().addAll(cuentasCargados);
        }



        // Cargar archivo de transacciones


        //cargar archivo transcciones

        //cargar archivo empleados

        //cargar archivo prestamo

    }





    //////////////////////////////Cargar//////////////////////////////
    public static ArrayList<Usuario> cargarUsuarios() throws FileNotFoundException, IOException
    {
        String rutaArchivo = obtenerRutaArchivoMasRecienteUsuario();
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
        verificarDirectoriosUsuarios();
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
        limpiarArchivosAntiguosUsuarios(RUTA_DIRECTORIO_USUARIOS);
        limpiarArchivosAntiguosUsuarios(RUTA_DIRECTORIO_RESPALDO);
    }

    private static void limpiarArchivosAntiguosUsuarios(String rutaDirectorio) {
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

    public static void guardarRecursoBilleteraBinario(BillerteraVirtual billerteraVirtual) {
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

    public static void guardarTransacciones(ArrayList<Transaccion> listaTransacciones) throws IOException {
        verificarDirectoriosTransacciones();
        String contenido = "";
        for(Transaccion transaccion:listaTransacciones)
        {
            contenido+= transaccion.getIdTransaccion()+"@@"+
                    transaccion.getFecha()+"@@"+
                    transaccion.getTipo()+"@@"+
                    transaccion.getMonto()+"@@"+
                    transaccion.getDescripcion()+"@@"+
                    transaccion.getCuentaOrigen()+"@@"+
                    transaccion.getCuentaDestino()+"\n";
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = PREFIJO_ARCHIVO_TRANSACCION + "_" + timestamp + EXTENSION_ARCHIVO;

        // Rutas para el archivo principal y el respaldo
        String rutaArchivoPrincipal = RUTA_DIRECTORIO_TRANSACCION + nombreArchivo;
        String rutaArchivoRespaldo = RUTA_DIRECTORIO_RESPALDO + nombreArchivo;

        // Guardar el archivo principal
        ArchivoUtil.guardarArchivo(rutaArchivoPrincipal, contenido, false);

        // Guardar la copia de respaldo
        ArchivoUtil.guardarArchivo(rutaArchivoRespaldo, contenido, false);

        // Eliminar archivos antiguos (tanto en la carpeta principal como en la de respaldo)
        limpiarArchivosAntiguosTransaccion(RUTA_DIRECTORIO_TRANSACCION);
        limpiarArchivosAntiguosTransaccion(RUTA_DIRECTORIO_RESPALDO);
    }

    private static void limpiarArchivosAntiguosTransaccion(String rutaDirectorioTransaccion) {
        File directorio = new File(rutaDirectorioTransaccion);
        File[] archivos = directorio.listFiles((dir, name) ->
                name.startsWith(PREFIJO_ARCHIVO_TRANSACCION) && name.endsWith(EXTENSION_ARCHIVO));

        if (archivos != null && archivos.length > 1) {
            Arrays.sort(archivos, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

            // Mantener solo el archivo más reciente
            for (int i = 1; i < archivos.length; i++) {
                archivos[i].delete();
            }
        }
    }

    private static void verificarDirectoriosTransacciones() {
        File directorioTransaccion = new File(RUTA_DIRECTORIO_TRANSACCION);
        File directorioRespaldo = new File(RUTA_DIRECTORIO_RESPALDO);

        if (!directorioTransaccion.exists()) {
            directorioTransaccion.mkdirs();
        }
        if (!directorioRespaldo.exists()) {
            directorioRespaldo.mkdirs();
        }
    }

    public static ArrayList<Transaccion> cargarTransacciones() throws IOException {
        String rutaArchivo = obtenerRutaArchivoMasRecienteTransaccion();
        ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(rutaArchivo);

        for (String linea : contenido) {
            String[] datos = linea.split("@@");
            Transaccion transaccion = new Transaccion();
            transaccion.setIdTransaccion(datos[0]);
            transaccion.setFecha(datos[1]);
            transaccion.setTipo(datos[2]);
            transaccion.setMonto(Double.parseDouble(datos[3]));
            transaccion.setDescripcion(datos[4]);
            Cuenta cuentaOrigen = obtenerCuentaPorNumero(datos[5]);
            transaccion.setCuentaOrigen(cuentaOrigen);
            Cuenta cuentaDestino = obtenerCuentaPorNumero(datos[6]);
            transaccion.setCuentaDestino(cuentaDestino);
            if (datos.length > 7) {
                transaccion.setIdTransaccion(datos[5]);
            }
            transacciones.add(transaccion);
        }
        return transacciones;
    }

    private static String obtenerRutaArchivoMasRecienteTransaccion() {
        File directorio = new File(RUTA_DIRECTORIO_TRANSACCION);
        File[] archivos = directorio.listFiles((dir, name) ->
                name.startsWith(PREFIJO_ARCHIVO_TRANSACCION) && name.endsWith(EXTENSION_ARCHIVO));

        if (archivos == null || archivos.length == 0) {
            return RUTA_DIRECTORIO_TRANSACCION + PREFIJO_ARCHIVO_TRANSACCION + EXTENSION_ARCHIVO;
        }

        Arrays.sort(archivos, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
        return archivos[0].getPath();
    }

    private static Cuenta obtenerCuentaPorNumero(String dato) {
        BillerteraVirtual billeteraVirtual = new BillerteraVirtual();
        return billeteraVirtual.obtenerCuentaPorNumero(dato);
    }
    public static ArrayList<Presupuesto> cargarPresupuesto() throws FileNotFoundException, IOException
    {
        String rutaArchivo = obtenerRutaArchivoMasRecientePresupuesto();
        ArrayList<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(rutaArchivo);

        for (String linea : contenido) {
            String[] datos = linea.split("@@");
            Presupuesto presupuesto = new Presupuesto();
            presupuesto.setIdPresupuesto(datos[0]);
            presupuesto.setNombre(datos[1]);
            presupuesto.setMontoAsignado(Double.parseDouble(datos[2]));
            presupuesto.setMontoGastado(Double.parseDouble(datos[3]));
            if (datos.length > 4) {
                presupuesto.setIdPresupuesto(datos[5]);
            }
            presupuestos.add(presupuesto);
        }
        return presupuestos;

    }

    private static String obtenerRutaArchivoMasRecientePresupuesto() {
        File directorio = new File(RUTA_DIRECTORIO_PRESUPUESTO);
        File[] archivos = directorio.listFiles((dir, name) ->
                name.startsWith(PREFIJO_ARCHIVO_PRESUPUESTO) && name.endsWith(EXTENSION_ARCHIVO));

        if (archivos == null || archivos.length == 0) {
            return RUTA_DIRECTORIO_PRESUPUESTO + PREFIJO_ARCHIVO_PRESUPUESTO + EXTENSION_ARCHIVO;
        }

        Arrays.sort(archivos, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
        return archivos[0].getPath();
    }
    public static void guardarPresupuesto(ArrayList<Presupuesto> listaPresupuestos) throws IOException {
        verificarDirectoriosPresupuestos();
        String contenido = "";
        for(Presupuesto presupuesto:listaPresupuestos)
        {
            contenido+= presupuesto.getIdPresupuesto()+"@@"+
                    presupuesto.getNombre()+"@@"+
                    presupuesto.getMontoAsignado()+"@@"+
                    presupuesto.getMontoGastado()+"\n";
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = PREFIJO_ARCHIVO_PRESUPUESTO + "_" + timestamp + EXTENSION_ARCHIVO;

        // Rutas para el archivo principal y el respaldo
        String rutaArchivoPrincipal = RUTA_DIRECTORIO_PRESUPUESTO + nombreArchivo;
        String rutaArchivoRespaldo = RUTA_DIRECTORIO_RESPALDO + nombreArchivo;

        // Guardar el archivo principal
        ArchivoUtil.guardarArchivo(rutaArchivoPrincipal, contenido, false);

        // Guardar la copia de respaldo
        ArchivoUtil.guardarArchivo(rutaArchivoRespaldo, contenido, false);

        // Eliminar archivos antiguos (tanto en la carpeta principal como en la de respaldo)
        limpiarArchivosAntiguosPresupuestos(RUTA_DIRECTORIO_PRESUPUESTO);
        limpiarArchivosAntiguosPresupuestos(RUTA_DIRECTORIO_RESPALDO);
    }

    private static void verificarDirectoriosPresupuestos() {
        File directorioPresupuesto = new File(RUTA_DIRECTORIO_PRESUPUESTO);
        File directorioRespaldo = new File(RUTA_DIRECTORIO_RESPALDO);


        if (!directorioPresupuesto.exists()) {
            directorioPresupuesto.mkdirs();
        }
        if (!directorioRespaldo.exists()) {
            directorioRespaldo.mkdirs();
        }
    }

    private static void limpiarArchivosAntiguosPresupuestos(String rutaDirectorioPresupuesto) {
        File directorio = new File(rutaDirectorioPresupuesto);
        File[] archivos = directorio.listFiles((dir, name) ->
                name.startsWith(PREFIJO_ARCHIVO_PRESUPUESTO) && name.endsWith(EXTENSION_ARCHIVO));

        if (archivos != null && archivos.length > 1) {
            Arrays.sort(archivos, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

            // Mantener solo el archivo más reciente
            for (int i = 1; i < archivos.length; i++) {
                archivos[i].delete();
            }
        }
    }

    private static void verificarDirectoriosCategoria() {
        File directorioCategoria = new File(RUTA_DIRECTORIO_CATEGORIA);
        File directorioRespaldo = new File(RUTA_DIRECTORIO_RESPALDO);

        if (!directorioCategoria.exists()) {
            directorioCategoria.mkdirs();
        }

        if (!directorioRespaldo.exists()) {
            directorioRespaldo.mkdirs();
        }
    }

    public static String obtenerRutaArchivoMasRecienteCategoria() {
        File directorio = new File(RUTA_DIRECTORIO_CATEGORIA);
        File[] archivos = directorio.listFiles((dir, name) ->
                name.startsWith(PREFIJO_ARCHIVO_CATEGORIA) && name.endsWith(EXTENSION_ARCHIVO));

        if (archivos == null || archivos.length == 0) {
            return RUTA_DIRECTORIO_CATEGORIA + PREFIJO_ARCHIVO_CATEGORIA + EXTENSION_ARCHIVO;
        }

        Arrays.sort(archivos, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
        return archivos[0].getPath();
    }
    public static ArrayList<Categoria> cargarCategoria() throws FileNotFoundException, IOException
    {
        String rutaArchivo = obtenerRutaArchivoMasRecienteCategoria();
        ArrayList<Categoria> categorias = new ArrayList<Categoria>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(rutaArchivo);

        for (String linea : contenido) {
            String[] datos = linea.split("@@");
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(datos[0]);
            categoria.setNombre(datos[1]);
            categoria.setDescripcion(datos[2]);

            if (datos.length > 3) {
                categoria.setIdCategoria(datos[3]);
            }
            categorias.add(categoria);
        }
        return categorias;

    }


//////////////////////guardar//////////////////////////////////


    public static void guardarCategorias(ArrayList<Categoria> listaCategorias) throws IOException {
        verificarDirectoriosCategoria();
        String contenido = "";
        for(Categoria categoria:listaCategorias)
        {
            contenido+= categoria.getIdCategoria()+"@@"+
                    categoria.getNombre()+"@@"+
                    categoria.getDescripcion()+"\n";
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = PREFIJO_ARCHIVO_CATEGORIA + "_" + timestamp + EXTENSION_ARCHIVO;

        // Rutas para el archivo principal y el respaldo
        String rutaArchivoPrincipal = RUTA_DIRECTORIO_CATEGORIA + nombreArchivo;
        String rutaArchivoRespaldo = RUTA_DIRECTORIO_RESPALDO + nombreArchivo;

        // Guardar el archivo principal
        ArchivoUtil.guardarArchivo(rutaArchivoPrincipal, contenido, false);

        // Guardar la copia de respaldo
        ArchivoUtil.guardarArchivo(rutaArchivoRespaldo, contenido, false);

        // Eliminar archivos antiguos (tanto en la carpeta principal como en la de respaldo)
        limpiarArchivosAntiguosCategoria(RUTA_DIRECTORIO_CATEGORIA);
        limpiarArchivosAntiguosCategoria(RUTA_DIRECTORIO_RESPALDO);
    }

    private static void limpiarArchivosAntiguosCategoria(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);
        File[] archivos = directorio.listFiles((dir, name) ->
                name.startsWith(PREFIJO_ARCHIVO_CATEGORIA) && name.endsWith(EXTENSION_ARCHIVO));

        if (archivos != null && archivos.length > 1) {
            Arrays.sort(archivos, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

            // Mantener solo el archivo más reciente
            for (int i = 1; i < archivos.length; i++) {
                archivos[i].delete();
            }
        }
    }

    private static void verificarDirectoriosCuenta() {
        File directorioCuenta= new File(RUTA_DIRECTORIO_CUENTA);
        File directorioRespaldo = new File(RUTA_DIRECTORIO_RESPALDO);

        if (!directorioCuenta.exists()) {
            directorioCuenta.mkdirs();
        }
        if (!directorioRespaldo.exists()) {
            directorioRespaldo.mkdirs();
        }
    }

    public static String obtenerRutaArchivoMasRecienteCuenta() {
        File directorio = new File(RUTA_DIRECTORIO_CUENTA);
        File[] archivos = directorio.listFiles((dir, name) ->
                name.startsWith(PREFIJO_ARCHIVO_CUENTA) && name.endsWith(EXTENSION_ARCHIVO));

        if (archivos == null || archivos.length == 0) {
            return RUTA_DIRECTORIO_CUENTA+ PREFIJO_ARCHIVO_CUENTA+ EXTENSION_ARCHIVO;
        }

        Arrays.sort(archivos, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
        return archivos[0].getPath();
    }
    public static ArrayList<Cuenta> cargarCuenta() throws FileNotFoundException, IOException
    {
        String rutaArchivo = obtenerRutaArchivoMasRecienteCuenta();
        ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(rutaArchivo);

        for (String linea : contenido) {
            String[] datos = linea.split("@@");
            Cuenta cuenta = new Cuenta();
            cuenta.setIdCuenta(datos[0]);
            cuenta.setNombreBanco(datos[1]);
            cuenta.setNumeroCuenta(datos[2]);
            cuenta.setTipoCuenta(TipoCuenta.valueOf(datos[3]));
            if (datos.length > 4) {
                cuenta.setIdCuenta(datos[4]);
            }
            cuentas.add(cuenta);
        }
        return cuentas;

    }


//////////////////////guardar//////////////////////////////////


    public static void guardarCuenta(ArrayList<Cuenta> listaCuentas) throws IOException {
        verificarDirectoriosCuenta();
        String contenido = "";
        for(Cuenta cuenta:listaCuentas)
        {
            contenido+= cuenta.getIdCuenta()+"@@"+
                    cuenta.getNombreBanco()+"@@"+
                    cuenta.getNumeroCuenta()+"@@"+
                    cuenta.getTipoCuenta()+"\n";

        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = PREFIJO_ARCHIVO_CUENTA + "_" + timestamp + EXTENSION_ARCHIVO;

        // Rutas para el archivo principal y el respaldo
        String rutaArchivoPrincipal = RUTA_DIRECTORIO_CUENTA + nombreArchivo;
        String rutaArchivoRespaldo = RUTA_DIRECTORIO_RESPALDO + nombreArchivo;

        // Guardar el archivo principal
        ArchivoUtil.guardarArchivo(rutaArchivoPrincipal, contenido, false);

        // Guardar la copia de respaldo
        ArchivoUtil.guardarArchivo(rutaArchivoRespaldo, contenido, false);

        // Eliminar archivos antiguos (tanto en la carpeta principal como en la de respaldo)
        limpiarArchivosAntiguosCuenta(RUTA_DIRECTORIO_CUENTA);
        limpiarArchivosAntiguosCuenta(RUTA_DIRECTORIO_RESPALDO);
    }

    private static void limpiarArchivosAntiguosCuenta(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);
        File[] archivos = directorio.listFiles((dir, name) ->
                name.startsWith(PREFIJO_ARCHIVO_CUENTA) && name.endsWith(EXTENSION_ARCHIVO));

        if (archivos != null && archivos.length > 1) {
            Arrays.sort(archivos, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

            // Mantener solo el archivo más reciente
            for (int i = 1; i < archivos.length; i++) {
                archivos[i].delete();
            }
        }
    }
}
