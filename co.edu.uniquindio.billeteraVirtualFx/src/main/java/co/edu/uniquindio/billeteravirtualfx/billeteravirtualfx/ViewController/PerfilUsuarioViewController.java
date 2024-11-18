package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;

public class PerfilUsuarioViewController {
    private UsuarioDto usuarioActual;
    private ObservableList<UsuarioDto> listaUsuarios = FXCollections.observableArrayList();

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

    // Método para recibir el usuario que inició sesión
    public void setUsuarioActual(UsuarioDto usuario) {
        this.usuarioActual = usuario;
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        if (usuarioActual != null) {
            // Cargar datos en los campos de texto
            txtNombreUsuario.setText(usuarioActual.nombre());
            txtCorreoUsuario.setText(usuarioActual.email());
            txtTelefonoUsuario.setText(usuarioActual.telefono());
            txtConsultaSaldo.setText(String.valueOf(usuarioActual.saldo()));

            // Cargar datos en la tabla
            listaUsuarios.clear();
            listaUsuarios.add(usuarioActual);
            tableUsuario.setItems(listaUsuarios);
        }
    }

    @FXML
    void onActualizarUsuario(ActionEvent event) {
        if (validarCampos()) {
            actualizarDatosUsuario();
            mostrarMensaje("Éxito", "Actualización exitosa",
                    "Los datos han sido actualizados correctamente", Alert.AlertType.INFORMATION);
        }
    }

    private void actualizarDatosUsuario() {
        // Aquí implementarías la lógica para actualizar el archivo txt
        usuarioActual = new UsuarioDto(
                usuarioActual.idUsuario(),
                txtNombreUsuario.getText(),
                txtCorreoUsuario.getText(),
                txtTelefonoUsuario.getText(),
                usuarioActual.saldo(),
                usuarioActual.contrasena()
        );
        // Actualizar el archivo txt con los nuevos datos
        // TODO: Implementar la actualización en el archivo
    }

    @FXML
    void onConsultarSaldo(ActionEvent event) {
        if (usuarioActual != null) {
            txtConsultaSaldo.setText(String.format("%.2f", usuarioActual.saldo()));
        }
    }

    @FXML
    void onConsultarTransacciones(ActionEvent event) {
        // TODO: Implementar la consulta de transacciones desde el archivo de transacciones
        // Filtrar solo las transacciones del usuario actual
    }

    private boolean validarCampos() {
        String mensaje = "";
        if (txtNombreUsuario.getText().trim().isEmpty()) {
            mensaje += "El nombre es requerido\n";
        }
        if (txtCorreoUsuario.getText().trim().isEmpty()) {
            mensaje += "El correo es requerido\n";
        }
        if (txtTelefonoUsuario.getText().trim().isEmpty()) {
            mensaje += "El teléfono es requerido\n";
        }

        if (!mensaje.isEmpty()) {
            mostrarMensaje("Error", "Campos inválidos", mensaje, Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        // Configurar las columnas de la tabla
        tcCedulaUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        tcNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcCorreoUsuario.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcTelefonoUsuario.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }
}
