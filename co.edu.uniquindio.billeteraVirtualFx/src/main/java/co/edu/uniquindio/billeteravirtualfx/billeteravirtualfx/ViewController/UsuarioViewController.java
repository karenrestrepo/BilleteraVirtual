package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.UsuarioController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class UsuarioViewController {
    UsuarioController usuarioControllerService;
    ObservableList<UsuarioDto> listaUsuarioDto = FXCollections.observableArrayList();
    UsuarioDto usuarioSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarUsuario;

    @FXML
    private Button btnAgregarUsuario;

    @FXML
    private Button btnEliminarUsuario;

    @FXML
    private TableView<UsuarioDto> tableUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcCedulaUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcCorreoUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcNombreUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcSaldoUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcTelefonoUsuario;

    @FXML
    private TextField txtCedulaUsuario;

    @FXML
    private TextField txtCorreoUsuario;

    @FXML
    private TextField txtFiltrarUsuario;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtSaldoUsuario;

    @FXML
    private TextField txtTelefonoUsuario;


    @FXML
    void initialize() {
        usuarioControllerService = new UsuarioController();
        initView();

    }

    private void initView() {
        initDataBinding();
        obtenerUsuarios();
        tableUsuario.getItems().clear();
        tableUsuario.setItems(listaUsuarioDto);
        listenerSelection();
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
            txtCedulaUsuario.setText(usuarioDto.idUsuario());
            txtCorreoUsuario.setText(usuarioDto.email());
            txtTelefonoUsuario.setText(usuarioDto.telefono());
            txtSaldoUsuario.setText(String.valueOf(usuarioDto.saldo()));

        }
    }

    private void obtenerUsuarios() {

        listaUsuarioDto.addAll(usuarioControllerService.obtenerUsuarios());
    }

    private void initDataBinding() {
        tcNombreUsuario.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().nombre())));
        tcCedulaUsuario.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().idUsuario())));
        tcCorreoUsuario.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().email())));
        tcTelefonoUsuario.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().telefono())));
        tcSaldoUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().saldo())));

    }

    @FXML
    void onActualizarUsuario(ActionEvent event) {
        actualizarUsuario();

    }

    @FXML
    void onAgregarUsuario(ActionEvent event) {
        agregarUsuario();
    }


    @FXML
    void onEliminararUsuario(ActionEvent event) {
        eliminararUsuario();

    }

    private void actualizarUsuario() {
        boolean usuarioActualizado = false;
        //1. Capturar los datos
        String idActual = usuarioSeleccionado.idUsuario();
        UsuarioDto usuarioDto = construirUsuarioDto();
        //2. verificar el empleado seleccionado
        if(usuarioSeleccionado != null){
            //3. Validar la información
            if(datosValidos(usuarioSeleccionado)){
                usuarioActualizado = usuarioControllerService.actualizarUsuario(idActual, usuarioDto);
                if(usuarioActualizado){
                    listaUsuarioDto.remove(usuarioSeleccionado);
                    listaUsuarioDto.add(usuarioDto);
                    tableUsuario.refresh();
                    mostrarMensaje("Notificación empleado", "Empleado actualizado", "El empleado se ha actualizado con éxito", AlertType.INFORMATION);
                    limpiarCamposUsuarios();
                }else{
                    mostrarMensaje("Notificación empleado", "Empleado no actualizado", "El empleado no se ha actualizado con éxito", AlertType.INFORMATION);
                }
            }else{
                mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son invalidos", AlertType.ERROR);
            }

        }
    }

    private void agregarUsuario() {
        UsuarioDto usuarioDto = construirUsuarioDto();
        //2. Validar la información
        if(datosValidos(usuarioDto)){
            if(usuarioControllerService.crearUsuario(usuarioDto)){
                listaUsuarioDto.add(usuarioDto);
                mostrarMensaje("Notificación usuario", "Usuario creado", "El usuario se ha creado con éxito", AlertType.INFORMATION);
                limpiarCamposUsuarios();
            }else{
                mostrarMensaje("Notificación usuario", "Usuario no creado", "El usuario no se ha creado con éxito", AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación usuario", "Usuario no creado", "Los datos ingresados son invalidos", AlertType.ERROR);
        }
    }

    private void eliminararUsuario() {
        boolean usuarioEliminado = false;
        if(usuarioSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar al usuario?")){
                usuarioEliminado = usuarioControllerService.eliminarUsuario(usuarioSeleccionado.idUsuario());
                if(usuarioEliminado == true){
                    listaUsuarioDto.remove(usuarioSeleccionado);
                    usuarioSeleccionado = null;
                    tableUsuario.getSelectionModel().clearSelection();
                    limpiarCamposUsuarios();
                    mostrarMensaje("Notificación usuario", "Usuario eliminado", "El usuario se ha eliminado con éxito", AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación usuario", "Usuario no eliminado", "El usuario no se puede eliminar", AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación usuario", "Usuario no seleccionado", "Seleccionado un usuario de la lista", AlertType.WARNING);
        }
    }

    private UsuarioDto construirUsuarioDto() {
        return new UsuarioDto(
                txtCedulaUsuario.getText(),
                txtNombreUsuario.getText(),
                txtCorreoUsuario.getText(),
                txtTelefonoUsuario.getText(),
                Double.valueOf(txtSaldoUsuario.getText())
        );
    }

    private void limpiarCamposUsuarios() {
        txtNombreUsuario.setText("");
        txtCedulaUsuario.setText("");
        txtCorreoUsuario.setText("");
        txtTelefonoUsuario.setText("");
        txtSaldoUsuario.setText("");

    }

    private boolean datosValidos(UsuarioDto usuarioDto) {
        String mensaje = "";
        if(usuarioDto.nombre() == null || usuarioDto.nombre().equals(""))
            mensaje += "El nombre es invalido \n" ;
        if(usuarioDto.idUsuario() == null || usuarioDto.idUsuario() .equals(""))
            mensaje += "El apellido es invalido \n" ;
        if(usuarioDto.email() == null || usuarioDto.email().equals(""))
            mensaje += "El correo es invalido \n" ;
        if(usuarioDto.telefono() == null || usuarioDto.telefono().equals(""))
            mensaje += "El teléfono es invalido \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación usuario","Datos invalidos",mensaje, AlertType.WARNING);
            return false;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

}

