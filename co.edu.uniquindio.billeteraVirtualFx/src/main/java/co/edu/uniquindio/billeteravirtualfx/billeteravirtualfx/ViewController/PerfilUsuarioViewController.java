package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PerfilUsuarioViewController {

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
    private TableView<?> tableUsuario;

    @FXML
    private TableColumn<?, ?> tcCedulaUsuario;

    @FXML
    private TableColumn<?, ?> tcCorreoUsuario;

    @FXML
    private TableColumn<?, ?> tcNombreUsuario;

    @FXML
    private TableColumn<?, ?> tcTelefonoUsuario;

    @FXML
    private TextField txtConsultaSaldo;

    @FXML
    private TextField txtCorreoUsuario;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtTelefonoUsuario;

    @FXML
    void onActualizarUsuario(ActionEvent event) {

    }

    @FXML
    void onConsultarSaldo(ActionEvent event) {

    }

    @FXML
    void onConsultarTransacciones(ActionEvent event) {

    }

    @FXML
    void initialize() {


    }

}

