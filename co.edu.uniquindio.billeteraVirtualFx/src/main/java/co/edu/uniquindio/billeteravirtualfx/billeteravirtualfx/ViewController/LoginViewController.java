package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.LoginController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.UsuarioController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController {
    LoginController loginControllerService;
    ObservableList<UsuarioDto> listaUsuarioDto = FXCollections.observableArrayList();
    UsuarioDto usuarioSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnRegistro;

    @FXML
    private TextField txtCedulaUsuario;

    @FXML
    private TextField txtContraseñaLogin;

    @FXML
    private TextField txtContraseñaUsuario;

    @FXML
    private TextField txtCorreoLogin;

    @FXML
    private TextField txtCorreoUsuario;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtSaldoUsuario;

    @FXML
    private TextField txtTelefonoUsuario;

    @FXML
    void onIngresar(ActionEvent event) {
        ingresar();

    }

    private void ingresar() {
        String correo = txtCorreoLogin.getText();
        String contraseña = txtContraseñaLogin.getText();

        if (correo == null || correo.trim().isEmpty() ||
                contraseña == null || contraseña.trim().isEmpty()) {
            mostrarMensaje("Error", "Campos vacíos",
                    "Por favor, complete todos los campos", Alert.AlertType.ERROR);
            return;
        }

        try {
            if (loginControllerService.ingresar(correo, contraseña)) {
                mostrarMensaje("Notificación ingreso", "Ingreso exitoso",
                        "Ha ingresado exitosamente como: " + correo, Alert.AlertType.INFORMATION);
                ingresarBilletera();
            } else {
                mostrarMensaje("Notificación ingreso", "Ingreso no exitoso",
                        "Correo o contraseña inválidos", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarMensaje("Error", "Error de sistema",
                    "Ocurrió un error al intentar ingresar: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void ingresarBilletera() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billeteravirtualfx/billeteravirtualfx/BilleteraView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtCorreoLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onRegistrar(ActionEvent event) {
        registrarUsuario();
    }

    private void registrarUsuario() {
        UsuarioDto usuarioDto = construirUsuarioDto();
        //2. Validar la información
        if(datosValidos(usuarioDto)){
            if(loginControllerService.crearUsuario(usuarioDto)){
                listaUsuarioDto.add(usuarioDto);
                mostrarMensaje("Notificación registro", "Usuario creado", "El usuario se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposUsuarios();
                ingresarBilletera();
            }else{
                mostrarMensaje("Notificación registro", "Usuario no creado", "El usuario no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación registro", "Usuario no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }
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
            mostrarMensaje("Notificación registro","Datos invalidos",mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    private UsuarioDto construirUsuarioDto() {
        return new UsuarioDto(
                txtCedulaUsuario.getText(),
                txtNombreUsuario.getText(),
                txtCorreoUsuario.getText(),
                txtTelefonoUsuario.getText(),
                Double.valueOf(txtSaldoUsuario.getText()),
                txtContraseñaUsuario.getText()
        );
    }

    @FXML
    void initialize() {
        loginControllerService = new LoginController();
        initView();

    }

    private void initView() {
        obtenerUsuarios();
    }

    private void obtenerUsuarios() {
        listaUsuarioDto.addAll(loginControllerService.obtenerUsuarios());
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private void limpiarCamposUsuarios() {
        txtNombreUsuario.setText("");
        txtCedulaUsuario.setText("");
        txtCorreoUsuario.setText("");
        txtTelefonoUsuario.setText("");
        txtSaldoUsuario.setText("");
        txtContraseñaUsuario.setText("");


    }


}

