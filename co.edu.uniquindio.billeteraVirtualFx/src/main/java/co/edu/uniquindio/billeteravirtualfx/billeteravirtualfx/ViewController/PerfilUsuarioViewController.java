package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.PerfilUsuarioController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.UsuarioController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PerfilUsuarioViewController {

    UsuarioController usuarioControllerService;
    PerfilUsuarioController perfilUsuarioController;
    ObservableList<UsuarioDto> listaUsuarioDto = FXCollections.observableArrayList();
    UsuarioDto usuarioSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarUsuario;

    @FXML
    private Button btnConsultarSaldo;

    @FXML
    private Button btnConsultarTransacciones;

    @FXML
    private TableView<UsuarioDto> tableUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcCedulaUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcCorreoUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcNombreUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcTelefonoUsuario;

    @FXML
    private TextField txtConsultaSaldo;

    @FXML
    private TextArea txtConsultaTransaccion;

    @FXML
    private TextField txtCorreoUsuario;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtTelefonoUsuario;

    @FXML
    void onActualizarUsuario(ActionEvent event) {
        actualizarUsuario();

    }

    @FXML
    void onConsultarSaldo(ActionEvent event) {
        // Verificar que haya un usuario seleccionado
        if (usuarioSeleccionado != null) {
            // Obtener el saldo del usuario
            Double saldo = usuarioSeleccionado.saldo();

            // Mostrar el saldo en el TextField correspondiente
            txtConsultaSaldo.setText(String.format("Saldo actual: $%.2f", saldo));
        } else {
            mostrarMensaje("Error", "No hay usuario seleccionado", "Por favor, seleccione un usuario para consultar el saldo.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onConsultarTransacciones(ActionEvent event) {

    }

    @FXML
    void initialize() {
        usuarioControllerService = new UsuarioController();
        perfilUsuarioController = new PerfilUsuarioController();
        initView();


    }
    private void initView() {
        initDataBinding();
        obtenerUsuario();
        tableUsuario.getItems().clear();
        tableUsuario.setItems(listaUsuarioDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tcNombreUsuario.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().nombre())));
        tcCedulaUsuario.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().idUsuario())));
        tcCorreoUsuario.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().email())));
        tcTelefonoUsuario.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().telefono())));

    }



    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(UsuarioDto usuarioDto){
        if (this.usuarioSeleccionado != null){

            txtNombreUsuario.setText(usuarioDto.nombre());
            txtCorreoUsuario.setText(usuarioDto.email());
            txtTelefonoUsuario.setText(usuarioDto.telefono());


        }
    }
    public void setUser(UsuarioDto usuarioDto) {
        usuarioSeleccionado = usuarioDto;
        obtenerUsuario();

    }


    private void obtenerUsuario() {

        if (usuarioSeleccionado != null) {
            listaUsuarioDto.add(usuarioSeleccionado);
        }

        tableUsuario.setItems(listaUsuarioDto); // Actualizar la tabla con el usuario autenticado
    }

    private void actualizarUsuario() {
        boolean usuarioActualizado = false;

        // 1. Capturar los datos
        String idActual = usuarioSeleccionado.idUsuario(); // Se toma el ID del usuario seleccionado

        // 2. Crear un nuevo DTO pero solo con los campos modificados
        UsuarioDto usuarioDto = construirUsuarioDto(); // Solo con los campos modificados

        // 3. Verificar si el usuario está seleccionado
        if (usuarioSeleccionado != null) {
            // 4. Validar los campos ingresados
            if (datosValidos(usuarioDto)) {
                // Aquí se actualiza el usuario utilizando el perfilUsuarioController
                usuarioActualizado = perfilUsuarioController.actualizarUsuario(idActual, usuarioDto);

                if (usuarioActualizado) {
                    listaUsuarioDto.remove(usuarioSeleccionado); // Eliminar el usuario antiguo
                    listaUsuarioDto.add(usuarioDto); // Agregar el nuevo usuario con solo los campos actualizados
                    tableUsuario.refresh(); // Refrescar la tabla
                    mostrarMensaje("Notificación usuario", "Usuario actualizado", "El usuario se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposUsuarios(); // Limpiar los campos después de actualizar
                } else {
                    mostrarMensaje("Notificación usuario", "Usuario no actualizado", "El usuario no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                }
            } else {
                mostrarMensaje("Notificación usuario", "Usuario no actualizado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }
        }
    }

    private UsuarioDto construirUsuarioDto() {
        // Solo modificamos los campos relevantes (nombre, correo y telefono)
        String id = usuarioSeleccionado.idUsuario();
        String nombre = txtNombreUsuario.getText().isEmpty() ? usuarioSeleccionado.nombre() : txtNombreUsuario.getText();
        String correo = txtCorreoUsuario.getText().isEmpty() ? usuarioSeleccionado.email() : txtCorreoUsuario.getText();
        String telefono = txtTelefonoUsuario.getText().isEmpty() ? usuarioSeleccionado.telefono() : txtTelefonoUsuario.getText();
        // Mantener el saldo y la contraseña actuales
        Double saldo = usuarioSeleccionado.saldo();
        String contraseña = usuarioSeleccionado.contrasena();


        return new UsuarioDto(
                usuarioSeleccionado.idUsuario(), // ID no cambia
                nombre,
                correo,
                telefono,
                saldo, // No cambiamos el saldo
                contraseña// No cambiamos la contraseña

        );
    }


    private void limpiarCamposUsuarios() {
        txtNombreUsuario.setText("");
        txtCorreoUsuario.setText("");
        txtTelefonoUsuario.setText("");
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean datosValidos(UsuarioDto usuarioSeleccionado) {
        String mensaje = "";
        if(usuarioSeleccionado.nombre() == null || usuarioSeleccionado.nombre().equals(""))
            mensaje += "El nombre es invalido \n" ;
        if(usuarioSeleccionado.idUsuario() == null || usuarioSeleccionado.idUsuario() .equals(""))
            mensaje += "El apellido es invalido \n" ;
        if(usuarioSeleccionado.email() == null || usuarioSeleccionado.email().equals(""))
            mensaje += "El correo es invalido \n" ;
        if(usuarioSeleccionado.telefono() == null || usuarioSeleccionado.telefono().equals(""))
            mensaje += "El teléfono es invalido \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación usuario","Datos invalidos",mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }




}
