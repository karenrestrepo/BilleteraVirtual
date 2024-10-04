package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.TransaccionController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class TransaccionViewController {
    TransaccionController transaccionControllerService;
    ObservableList<TransaccionDto> listaTransaccionesDto = FXCollections.observableArrayList();
    TransaccionDto transaccionSeleccionada;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAgregarUsuario;

    @FXML
    private TableView<TransaccionDto> tableTransaccion;

    @FXML
    private TableColumn<TransaccionDto, String> tcCuentaDestinoTransaccion;

    @FXML
    private TableColumn<TransaccionDto, String> tcCuentaOrigen;

    @FXML
    private TableColumn<TransaccionDto, String> tcDescripcionTransaccion;

    @FXML
    private TableColumn<TransaccionDto, String> tcFechaTransaccion;

    @FXML
    private TableColumn<TransaccionDto, String> tcIdTransaccion;

    @FXML
    private TableColumn<TransaccionDto, String> tcMontoTransaccion;

    @FXML
    private TableColumn<TransaccionDto, String> tcTipoTransaccion;

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
    private TextField txtMontoTransaccion;

    @FXML
    private TextField txtTipoTransaccion;


    @FXML
    void initialize() {
        transaccionControllerService = new TransaccionController();
        initView();

    }

    private void initView() {
        initDataBinding();
        obtenerTransacciones();
        tableTransaccion.getItems().clear();
        tableTransaccion.setItems(listaTransaccionesDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tcIdTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idTransaccion()));
        tcFechaTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fecha()));
        tcTipoTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tipo()));
        tcMontoTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().monto())));
        tcDescripcionTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().descripcion()));
        tcCuentaOrigen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().cuentaOrigen()));
        tcCuentaDestinoTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().cuentaDestino()));

    }

    private void obtenerTransacciones() {
        listaTransaccionesDto.addAll(transaccionControllerService.obtenerTransacciones());
    }

    private void listenerSelection() {
        tableTransaccion.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            transaccionSeleccionada = newSelection;
            mostrarInformacionTransaccion(transaccionSeleccionada);
        });
    }

    private void mostrarInformacionTransaccion(TransaccionDto transaccionSeleccionada) {
        if(transaccionSeleccionada != null){
            txtIdTransaccion.setText(transaccionSeleccionada.idTransaccion());
            txtFechaTransaccion.setText(transaccionSeleccionada.fecha());
            txtTipoTransaccion.setText(transaccionSeleccionada.tipo());
            txtMontoTransaccion.setText(String.valueOf(transaccionSeleccionada.monto()));
            txtDescripcionTransaccion.setText(transaccionSeleccionada.descripcion());
            txtCuentaOrigenTransaccion.setText(transaccionSeleccionada.cuentaOrigen());
            txtCuentaDestinoTransaccion.setText(transaccionSeleccionada.cuentaDestino());

        }
    }

    @FXML
    void onCrearTransaccion(ActionEvent event) {
        crearTransaccion();

    }

    private void crearTransaccion() {
        TransaccionDto transaccionDto = construirTransaccionDto();
        if(datosValidos(transaccionDto)){
            if(transaccionControllerService.crearTransaccion(transaccionDto)){
                listaTransaccionesDto.add(transaccionDto);
                mostrarMensaje("Notificación empleado", "Empleado creado", "El empleado se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposTransaccion();
                registrarAcciones("Empleado agregado",1, "Agregar empleado");
            }else{
                mostrarMensaje("Notificación empleado", "Empleado no creado", "El empleado no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }
    }

    private boolean datosValidos(TransaccionDto transaccionDto) {
        String mensaje = "";
        if(transaccionDto.idTransaccion() == null || transaccionDto.idTransaccion().equals(""))
            mensaje += "El id es invalido \n" ;
        if(transaccionDto.fecha() == null || transaccionDto.fecha() .equals(""))
            mensaje += "La fecha es invalida \n" ;
        if(transaccionDto.tipo() == null || transaccionDto.tipo().equals(""))
            mensaje += "El tipo es invalido \n" ;
        if(transaccionDto.cuentaOrigen() == null || transaccionDto.cuentaOrigen().equals(""))
            mensaje += "La cuenta de origen es invalida \n" ;
        if(transaccionDto.cuentaDestino() == null || transaccionDto.cuentaDestino().equals(""))
            mensaje += "La cuenta destino es invalida \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación cliente","Datos invalidos",mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    private void mostrarMensaje(String notificaciónCliente, String datosInvalidos, String mensaje, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private TransaccionDto construirTransaccionDto() {
        return new TransaccionDto(
                txtIdTransaccion.getText(),
                txtFechaTransaccion.getText(),
                txtTipoTransaccion.getText(),
                Double.valueOf(txtMontoTransaccion.getText()),
                txtDescripcionTransaccion.getText(),
                txtCuentaOrigenTransaccion.getText(),
                txtCuentaDestinoTransaccion.getText()
        );
    }
    private void limpiarCamposTransaccion() {
        txtIdTransaccion.setText("");
        txtFechaTransaccion.setText("");
        txtTipoTransaccion.setText("");
        txtMontoTransaccion.setText("");
        txtDescripcionTransaccion.setText("");
        txtCuentaOrigenTransaccion.setText("");
        txtCuentaDestinoTransaccion.setText("");
    }
    private void registrarAcciones(String mensaje, int nivel, String accion) {
        transaccionControllerService.registrarAcciones(mensaje, nivel, accion);
    }

}

