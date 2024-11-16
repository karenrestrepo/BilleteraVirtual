package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CategoriaViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<?> tableCategorias;

    @FXML
    private TableColumn<?, ?> tcDescripcion;

    @FXML
    private TableColumn<?, ?> tcIdCategoria;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtFiltrarCategoria;

    @FXML
    private TextField txtIdCategoria;

    @FXML
    private TextField txtNombre;

    @FXML
    void onActualizar(ActionEvent event) {

    }

    @FXML
    void onAgregar(ActionEvent event) {

    }

    @FXML
    void onEliminarar(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
