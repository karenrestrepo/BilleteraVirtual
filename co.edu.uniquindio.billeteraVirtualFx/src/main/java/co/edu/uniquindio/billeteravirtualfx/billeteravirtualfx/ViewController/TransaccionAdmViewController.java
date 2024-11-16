package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TransaccionAdmViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAgregarUsuario;

    @FXML
    private TableView<?> tableTransaccion;

    @FXML
    private TableColumn<?, ?> tcCuentaDestinoTransaccion;

    @FXML
    private TableColumn<?, ?> tcCuentaOrigen;

    @FXML
    private TableColumn<?, ?> tcDescripcionTransaccion;

    @FXML
    private TableColumn<?, ?> tcFechaTransaccion;

    @FXML
    private TableColumn<?, ?> tcIdTransaccion;

    @FXML
    private TableColumn<?, ?> tcIdUsuario;

    @FXML
    private TableColumn<?, ?> tcMontoTransaccion;

    @FXML
    private TableColumn<?, ?> tcTipoTransaccion;

    @FXML
    private TextField txtCuentaDestinoTransaccion;

    @FXML
    private TextField txtCuentaOrigenTransaccion;

    @FXML
    private TextField txtDescripcionTransaccion;

    @FXML
    private TextField txtFechaTransaccion;

    @FXML
    private TextField txtFiltrarTransaccion;

    @FXML
    private TextField txtIdTransaccion;

    @FXML
    private TextField txtIdUsuario;

    @FXML
    private TextField txtMontoTransaccion;

    @FXML
    private TextField txtTipoTransaccion;

    @FXML
    void onCrearTransaccion(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
