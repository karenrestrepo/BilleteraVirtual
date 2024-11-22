package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.TransaccionController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.Productor.ProductorController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.RabbitMQ.util.Constantes.QUEUE_NUEVA_TRANSACCION;

public class TransaccionViewController {
    UsuarioDto usuarioSeleccionado;
    TransaccionController transaccionControllerService;
    ObservableList<CategoriaDto> listaCategorias = FXCollections.observableArrayList();
    ObservableList<TransaccionDto> listaTransaccionesDto = FXCollections.observableArrayList();
    TransaccionDto transaccionSeleccionada;


    @FXML
    private ResourceBundle resources;

    @FXML
    private ComboBox<CategoriaDto> cmb;

    @FXML
    private URL location;

    @FXML
    private Button btnAgregarUsuario;
    @FXML
    private Button btnCategorizar;

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
    private TableColumn<TransaccionDto, String> tcCategoria;

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
    void onCategorizar(ActionEvent event) {
        CategoriaDto categoriaSeleccionada = cmb.getSelectionModel().getSelectedItem();


    }


    @FXML
    void initialize() {
        transaccionControllerService = new TransaccionController();
        initView();
        cargandoCategorias();

    }

    public void cargandoCategorias() {

        listaCategorias.addAll(transaccionControllerService.obtenerCategoria());

        cmb.setItems(listaCategorias);

        if (!listaCategorias.isEmpty()) {
            cmb.getSelectionModel().selectFirst();
        }
    }

    private void initView() {
        initDataBinding();
        obtenerTransacciones();
        tableTransaccion.getItems().clear();
        tableTransaccion.setItems(listaTransaccionesDto);
        listenerSelection();
        mostrarTransaccion();
    }
    public  void setUser(UsuarioDto usuarioDto) {
        usuarioSeleccionado = usuarioDto;

    }

    private void initDataBinding() {
        tcIdTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idTransaccion()));
        tcFechaTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fecha()));
        tcTipoTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tipo()));
        tcMontoTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().monto())));
        tcDescripcionTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().descripcion()));
        tcCuentaOrigen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().cuentaOrigen()));
        tcCuentaDestinoTransaccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().cuentaDestino()));
        tcCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().categoria()));

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
            cmb.setAccessibleText(transaccionSeleccionada.categoria());

        }
    }

    @FXML
    void onCrearTransaccion(ActionEvent event) {
        crearTransaccion();

    }

    private void crearTransaccion() {
        TransaccionDto transaccionDto = construirTransaccionDto();
        if(datosValidos(transaccionDto)) {
            if (mostrarMensajeConfirmacion("¿Estas seguro de la realización de la transacción?")) {
                    ProductorController modelFactoryController = ProductorController.getInstance();
                    String mensaje = "";
                    mensaje += "100;";
                    mensaje += "NUEVO_PRODUCTO";
                    modelFactoryController.procesarMensajeTransaccion(transaccionDto);
                    listaTransaccionesDto.add(transaccionDto);
                    mostrarMensaje("Notificación Transacción", "Transacción creado", "El Transacción se ha creado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposTransaccion();
                    registrarAcciones(" Transacción Creada ", 1, " La transacción se creo correctamente");
            }
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

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
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

    private TransaccionDto construirTransaccionDto() {
        // Obtener el nombre de la categoría seleccionada en el ComboBox
        String categoriaSeleccionada = cmb.getSelectionModel().getSelectedItem().nombre();

        return new TransaccionDto(
                txtIdTransaccion.getText(),
                txtFechaTransaccion.getText(),
                txtTipoTransaccion.getText(),
                Double.valueOf(txtMontoTransaccion.getText()),
                txtDescripcionTransaccion.getText(),
                txtCuentaOrigenTransaccion.getText(),
                txtCuentaDestinoTransaccion.getText(),
                categoriaSeleccionada

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

    private void mostrarTransaccion() {
        txtFiltrarTransaccion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarTablas(newValue.toLowerCase());
        });
    }

    private void filtrarTablas(String valorBusqueda) {
        filtrarRecursivo(listaTransaccionesDto, valorBusqueda, 0, FXCollections.observableArrayList());
    }

    private void filtrarRecursivo(List<TransaccionDto> lista, String valorBusqueda, int index, ObservableList<TransaccionDto> transaccionFiltrada) {
        if (index < lista.size()) {
            TransaccionDto transaccionDto = lista.get(index);

            // Comprobación de null antes de llamar a toLowerCase()
            String fecha = transaccionDto.fecha() != null ? transaccionDto.fecha().toLowerCase() : "";
            String tipo = transaccionDto.tipo() != null ? transaccionDto.tipo().toLowerCase() : "";
            String categoria = transaccionDto.categoria() != null ? transaccionDto.categoria().toLowerCase() : "";

            // Ahora puedes comparar con seguridad
            if (fecha.contains(valorBusqueda) || tipo.contains(valorBusqueda) || categoria.contains(valorBusqueda)) {
                transaccionFiltrada.add(transaccionDto);
            }

            // Llamada recursiva
            filtrarRecursivo(lista, valorBusqueda, index + 1, transaccionFiltrada);
        } else {
            // Usar Platform.runLater para asegurarse de que se actualice en el hilo de la UI
            javafx.application.Platform.runLater(() -> {
                tableTransaccion.setItems(transaccionFiltrada);  // Cuando termina la recursión, actualiza la tabla
            });
        }
    }

}

