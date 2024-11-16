package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CuentaBancariaViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarCuenta;

    @FXML
    private Button btnAgregarCuenta;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<?> tableCuentas;

    @FXML
    private TableColumn<?, ?> tcIdCuenta;

    @FXML
    private TableColumn<?, ?> tcNombreBanco;

    @FXML
    private TableColumn<?, ?> tcNumeroCuenta;

    @FXML
    private TableColumn<?, ?> tcTipoCuenta;

    @FXML
    private TextField txtFiltrarCuentas;

    @FXML
    private TextField txtIdCuenta;

    @FXML
    private TextField txtNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    private TextField txtTipoCuenta;

    @FXML
    void onActualizarCuenta(ActionEvent event) {

    }

    @FXML
    void onAgregarCuenta(ActionEvent event) {

    }

    @FXML
    void onEliminar(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}

