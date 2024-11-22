package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Exception.*;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Service.IBilleteraVirtualService;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils.Persistencia;

import java.io.Serializable;
import java.util.ArrayList;

public class BillerteraVirtual implements IBilleteraVirtualService, Serializable {

    private static final long serialVersionUID = 1L;

    ////// listas ///////////////
    ArrayList<Cuenta> listaCuentas = new ArrayList<>();
    ArrayList<Transaccion> listaTransacciones = new ArrayList<>();
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    ArrayList<Presupuesto> listaPresupuestos = new ArrayList<>();
    ArrayList<Categoria> listaCategorias = new ArrayList<>();

    Usuario usuarioSeleccionado;

    Persistencia persistencia;
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


    /////// Usuario//////////////////
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

    /////////////////// Eliminar ///////////////
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

    //////////// Transacción////////////////

    @Override
    public boolean verificarTransaccionExistente(String cuenta) throws TransaccionException {
        if (transaccionExiste(cuenta)) {
            throw new TransaccionException("La cuenta de origen: " + cuenta + " no existe");
        } else {
            return false;
        }
    }

    // Este método revisa si ya existe una transacción con el ID especificado
    public boolean transaccionExiste(String idTransaccion) {
        for (Transaccion transaccion : usuarioSeleccionado.listaTransacciones) {
            if (transaccion.getIdTransaccion().equalsIgnoreCase(idTransaccion)) {
                return true;  // Ya existe una transacción con el mismo ID
            }

        }
        return false;
    }


    @Override
    public void crearTransaccion(Transaccion nuevaTransaccion) throws TransaccionException {

        boolean cuentaOrigenValida = encontrarCuentaExistenteUsuario(nuevaTransaccion.getCuentaOrigen().getNumeroCuenta());
        boolean cuentaDestinoValida = encontrarCuentaExistente(nuevaTransaccion.getCuentaDestino().getNumeroCuenta());

        if (transaccionExiste(nuevaTransaccion.getIdTransaccion())) {
            throw new TransaccionException("Ya existe una transacción con el mismo ID: " + nuevaTransaccion.getIdTransaccion());
        }

        if (nuevaTransaccion.getTipo().equalsIgnoreCase("Transferir")) {

            if (cuentaOrigenValida && cuentaDestinoValida) {
                // Verificar que no exista una transacción con el mismo ID
                // Si las verificaciones pasan, agregar la transacción a las listas
                transferirMontoo(nuevaTransaccion);
            }
            else {
                throw new TransaccionException("Una o ambas cuentas no existen.");
            }

        }if (nuevaTransaccion.getTipo().equalsIgnoreCase("Agregar")){
            if (cuentaOrigenValida ) {
                agregarMontoo(nuevaTransaccion);
            }


        }if (nuevaTransaccion.getTipo().equalsIgnoreCase("Retirar")){

            if (cuentaOrigenValida) {
                retirarMontoo(nuevaTransaccion);
            }


        }else{
            throw new TransaccionException("Datos invalidos");

        }


    }


    public boolean encontrarCuentaExistenteUsuario(String transaccion) {

        // Recorrer la lista de cuentas
        for (Cuenta cuenta : usuarioSeleccionado.getListaCuentas()) {
            // Si la cuenta coincide con la ID de transacción
            if (cuenta.getNumeroCuenta().equalsIgnoreCase(transaccion)){
                System.out.println("La cuenta existe");
                return true;  // Retorna true si la cuenta se encuentra
            }
        }

        // Si no se encontró la cuenta, se retorna false
        System.out.println("La cuenta no existe");
        return false;
    }
    public boolean encontrarCuentaExistente(String transaccion) {

        // Recorrer la lista de cuentas
        for (Cuenta cuenta : listaCuentas) {
            // Si la cuenta coincide con la ID de transacción
            if (cuenta.getNumeroCuenta().equalsIgnoreCase(transaccion)) {
                System.out.println("La cuenta existe");
                return true;  // Retorna true si la cuenta se encuentra
            }

        }

        // Si no se encontró la cuenta, se retorna false
        System.out.println("La cuenta no existe");
        return false;
    }


    @Override
    public Usuario verificarCredenciales(String correo, String contraseña) {
        System.out.println("Intentando verificar credenciales para correo: " + correo);

        if (correo == null || contraseña == null) {
            System.out.println("Correo o contraseña es null");
            return null;
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

                return usuario;
            }
        }
        System.out.println("No se encontró coincidencia");
        return null;
    }


    @Override
    public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
        for (Cuenta cuenta : listaCuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return cuenta;
            }
        }
        return null;
    }

    @Override
    public boolean verificarAdmin(String correo, String contraseña) {
        System.out.println("Intentando verificar credenciales para correo: " + correo);

        if (correo == null || contraseña == null) {
            System.out.println("Correo o contraseña es null");
            return false;
        }

        for (Usuario usuario : getListaUsuarios()) {
            System.out.println("Comprobando usuario: " + usuario.getEmail());
            System.out.println("Contraseña almacenada: " + usuario.getContrasena());

            if (correo.equals("administrador@gmail.com") &&
                    contraseña.equals("admin01")) {
                System.out.println("¡Coincidencia encontrada!");
                return true;
            }
        }
        System.out.println("No se encontró coincidencia");
        return false;
    }



/////////////// presupuesto //////////////////


    @Override
    public Presupuesto agregarPresupuesto(String idPresupuesto, String nombre, double montoAsignado, double montoGastado, String categoria) throws PresupuestoException{

        Presupuesto nuevoPresupuesto = null;
        boolean presupuestoExiste = verificarPresupuestoExistente(idPresupuesto);
        if(presupuestoExiste){
            throw new PresupuestoException("El usuario con cedula: "+idPresupuesto+" ya existe");
        }else{
            nuevoPresupuesto = new Presupuesto();
            nuevoPresupuesto.setIdPresupuesto(idPresupuesto);
            nuevoPresupuesto.setNombre(nombre);
            nuevoPresupuesto.setMontoAsignado(montoAsignado);
            nuevoPresupuesto.setMontoGastado(montoGastado);

            usuarioSeleccionado.getListaPresupuestos().add(nuevoPresupuesto);
        }
        return nuevoPresupuesto;

    }

    @Override
    public Boolean eliminarPresupuesto(String id)throws PresupuestoException{
        Presupuesto presupuesto = null;
        boolean idExiste = false;
        System.out.println("hola");
        presupuesto = obtenerPresupuesto(id);
        if(presupuesto == null)
            throw new PresupuestoException("El Presupuesto a eliminar no existe");
        else{
            getListaPresupuestos().remove(presupuesto);
            usuarioSeleccionado.getListaPresupuestos().remove(presupuesto);
            idExiste = true;
        }
        return idExiste;

    }

    @Override
    public boolean actualizarPresupuesto(String idActual, Presupuesto presupuesto) throws PresupuestoException{
        Presupuesto presupuestoExiste = obtenerPresupuesto(idActual);
        if(presupuestoExiste == null)
            throw new PresupuestoException("El presupuesto a actualizar no existe");
        else{

            presupuestoExiste.setIdPresupuesto(presupuesto.getIdPresupuesto());
            presupuestoExiste.setNombre(presupuesto.getNombre());
            presupuestoExiste.setMontoAsignado(presupuesto.getMontoAsignado());
            presupuestoExiste.setMontoGastado(presupuesto.getMontoGastado());
            presupuestoExiste.setCategoria(presupuesto.getCategoria());

            return true;
        }

    }

    @Override
    public boolean  verificarPresupuestoExistente(String presupuesto) throws PresupuestoException{

        if(presupuestoExiste(presupuesto)){
            throw new PresupuestoException("La cuenta de origen: "+presupuesto+" no existe");
        }else{
            return false;
        }

    }

    private boolean presupuestoExiste(String presupuesto) {

        boolean presupuestoEncontrado= false;
        for (Presupuesto presupuesto1 : getListaPresupuestos()) {
            if(presupuesto1.getIdPresupuesto().equalsIgnoreCase(presupuesto)){
                presupuestoEncontrado = true;
                break;
            }
        }
        return presupuestoEncontrado;
    }

    @Override
    public Presupuesto obtenerPresupuesto(String id){
        Presupuesto presupuestoEncontrado = null;
        for (Presupuesto presupuesto : getListaPresupuestos()) {
            if(presupuesto.getIdPresupuesto().equalsIgnoreCase(id)){
                presupuestoEncontrado = presupuesto;
                break;
            }
        }
        return presupuestoEncontrado;

    }

    public void crearPresupuesto(Presupuesto presupuesto) {
        getListaPresupuestos().add(presupuesto);
        usuarioSeleccionado.getListaPresupuestos().add(presupuesto);
    }


    ///////////// categoria ///////////////////

    @Override
    public void crearCategoria(Categoria categoria) throws CategoriaException{
        getListaCategorias().add(categoria);
        usuarioSeleccionado.getListaCategorias().add(categoria);

    }
    @Override
    public Boolean eliminarCategoria(String id)throws CategoriaException{
        Categoria categoria = null;
        boolean idExiste = false;
        categoria = obtenerCategoria(id);
        if(categoria == null)
            throw new CategoriaException("El usuario a eliminar no existe");
        else{
            getListaCategorias().remove(categoria);
            usuarioSeleccionado.getListaCategorias().remove(categoria);
            idExiste = true;
        }
        return idExiste;

    }
    @Override
    public boolean actualizarCategoria(String idActual, Categoria categoria) throws CategoriaException{
        Categoria categoriaExistente = obtenerCategoria(idActual);
        if(categoriaExistente == null)
            throw new CategoriaException("La categoria a actualizar no existe");
        else{

            categoriaExistente.setIdCategoria(categoria.getIdCategoria());
            categoriaExistente.setNombre(categoria.getNombre());
            categoriaExistente.setDescripcion(categoria.getDescripcion());



            return true;
        }

    }
    @Override
    public boolean  verificarCategoriaExistente(String id) throws CategoriaException{
        if(categoriaExistente(id)){
            throw new CategoriaException("La categoria de origen: "+id+" no existe");
        }else{
            return false;
        }

    }

    private boolean categoriaExistente(String categoria) {
        boolean categoriaEncontrada = false;
        for (Categoria categoria1 : usuarioSeleccionado.getListaCategorias()) {
            if(categoria1.getIdCategoria().equalsIgnoreCase(categoria)){
                categoriaEncontrada = true;
                break;
            }
        }
        return categoriaEncontrada;
    }

    @Override
    public Categoria obtenerCategoria(String id){
        Categoria categoria = null;
        for (Categoria categoria1 : usuarioSeleccionado.getListaCategorias() ) {
            if(categoria1.getIdCategoria().equalsIgnoreCase(id)){
                categoria = categoria1;
                break;
            }
        }
        return categoria;

    }

    /////////////// Cuenta//////////////////

    @Override
    public void crearCuenta(Cuenta cuenta) throws CuentaException{
        System.out.println(usuarioSeleccionado.listaCuentas);
        getListaCuentas().add(cuenta);
        usuarioSeleccionado.listaCuentas.add(cuenta);

    }
    @Override
    public Boolean eliminarCuenta(String id)throws CuentaException{
        Cuenta cuenta = null;
        boolean idExiste = false;
        cuenta = obtenerCuenta(id);
        if(cuenta == null)
            throw new CuentaException("La Cuenta a eliminar no existe");
        else{
            getListaCategorias().remove(cuenta);
            usuarioSeleccionado.getListaCategorias().remove(cuenta);
            idExiste = true;
        }
        return idExiste;

    }
    @Override
    public boolean actualizarCuenta(String idActual, Cuenta cuenta) throws CuentaException{

        Cuenta cuentaExistente = obtenerCuenta(idActual);
        if(cuentaExistente == null)
            throw new CuentaException("La Cuenta a actualizar no existe");
        else{

            cuentaExistente.setIdCuenta(cuenta.getIdCuenta());
            cuentaExistente.setNombreBanco(cuenta.getNombreBanco());
            cuentaExistente.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaExistente.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaExistente.setSaldo(cuenta.getSaldo());



            return true;
        }


    }
    @Override
    public boolean  verificarCuentaExistente(String id) throws CuentaException{
        if(cuentaExiste(id)){
            throw new CuentaException("La cuenta de origen: "+id+" no existe");
        }else{
            return false;
        }

    }

    private boolean cuentaExiste(String cuenta) {
        boolean cuentaEncontrada = false;
        for (Cuenta cuenta1 : usuarioSeleccionado.getListaCuentas()) {
            if(cuenta1.getIdCuenta().equalsIgnoreCase(cuenta)){
                cuentaEncontrada = true;
                break;
            }
        }
        return cuentaEncontrada;
    }

    @Override
    public Cuenta obtenerCuenta(String id){
        System.out.println(usuarioSeleccionado.listaCuentas);
        Cuenta cuenta = null;
        for (Cuenta cuenta1 : usuarioSeleccionado.listaCuentas ) {
            if(cuenta1.getIdCuenta().equalsIgnoreCase(id)){
                cuenta = cuenta1;
                break;
            }
        }
        return cuenta;

    }

    /////// operaciones logicas de una transacción /////////////////

    @Override
    public void agregarMontoo(Transaccion nuevaTransaccion) throws TransaccionException {

        Cuenta cuentaOrigen = nuevaTransaccion.getCuentaOrigen();

        // verificar saldo suficiente
        if (cuentaOrigen.getSaldo() < nuevaTransaccion.getMonto()) {
            throw new TransaccionException("Saldo insuficiente en la cuenta de origen.");
        }

        double saldoActualizadoOrigen = cuentaOrigen.getSaldo() + nuevaTransaccion.getMonto();
        cuentaOrigen.setSaldo(saldoActualizadoOrigen);


        getListaTransacciones().add(nuevaTransaccion);
        usuarioSeleccionado.getListaTransacciones().add(nuevaTransaccion);


    }

    @Override
    public void retirarMontoo(Transaccion nuevaTransaccion) throws TransaccionException {

        Cuenta cuentaOrigen = nuevaTransaccion.getCuentaOrigen();
        // verificar saldo suficiente
        if (cuentaOrigen.getSaldo() < nuevaTransaccion.getMonto()) {
            throw new TransaccionException("Saldo insuficiente en la cuenta de origen.");
        }

        double saldoActualizadoOrigen = cuentaOrigen.getSaldo() - nuevaTransaccion.getMonto();
        cuentaOrigen.setSaldo(saldoActualizadoOrigen);

        // Registrar la transacción en las listas correspondientes
        getListaTransacciones().add(nuevaTransaccion);
        usuarioSeleccionado.getListaTransacciones().add(nuevaTransaccion);


    }

    @Override
    public void transferirMontoo(Transaccion nuevaTransaccion) throws TransaccionException {

        Cuenta cuentaOrigen = nuevaTransaccion.getCuentaOrigen();
        Cuenta cuentaDestino = nuevaTransaccion.getCuentaDestino();
        // verificar saldo suficiente
        if (cuentaOrigen.getSaldo() < nuevaTransaccion.getMonto()) {
            throw new TransaccionException("Saldo insuficiente en la cuenta de origen.");
        }

        double saldoActualizadoOrigen = cuentaOrigen.getSaldo() - nuevaTransaccion.getMonto();
        cuentaOrigen.setSaldo(saldoActualizadoOrigen);

        // Actualizar el saldo de la cuenta de destino (sumando el monto)
        double saldoActualizadoDestino = cuentaDestino.getSaldo() + nuevaTransaccion.getMonto();
        cuentaDestino.setSaldo(saldoActualizadoDestino);

        // Registrar la transacción en las listas correspondientes
        getListaTransacciones().add(nuevaTransaccion);
        usuarioSeleccionado.getListaTransacciones().add(nuevaTransaccion);
    }









    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
}
