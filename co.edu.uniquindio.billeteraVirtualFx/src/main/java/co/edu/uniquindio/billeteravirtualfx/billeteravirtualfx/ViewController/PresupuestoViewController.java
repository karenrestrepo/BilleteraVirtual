package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.PresupuestoController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.PresupuestoDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.BillerteraVirtual;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Categoria;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PresupuestoViewController {

    PresupuestoController presupuestoControllerSevice;
    ObservableList<PresupuestoDto> listaPresupuestosDto = FXCollections.observableArrayList();
    BillerteraVirtual billerteraVirtual;

    PresupuestoDto presupeuestoSeleccioando;


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
    private ComboBox<Categoria> comboCategoria;

    @FXML
    private TableView<PresupuestoDto> tablePresupuesto;

    @FXML
    private TableColumn<PresupuestoDto, String> tcCategoria;

    @FXML
    private TableColumn<PresupuestoDto, String> tcEstado;

    @FXML
    private TableColumn<PresupuestoDto, String> tcIdPresupuesto;

    @FXML
    private TableColumn<PresupuestoDto, String> tcMontoAsignado;

    @FXML
    private TableColumn<PresupuestoDto, String> tcMontoGastado;

    @FXML
    private TableColumn<PresupuestoDto, String> tcNombre;

    @FXML
    private TextField txtFiltrarPresupuesto;

    @FXML
    private TextField txtIdPresupuesto;

    @FXML
    private TextField txtMontoAsignado;

    @FXML
    private TextField txtNombre;

    @FXML
    void onCategoriabox(ActionEvent event) {
        Categoria categoriaSeleccionada = comboCategoria.getSelectionModel().getSelectedItem();

    }

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarPresupuesto();

    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregarPresupuesto();

    }

    @FXML
    void onEliminarar(ActionEvent event) {
        eliminarPresupuesto();

    }

    @FXML
    void initialize() {
        presupuestoControllerSevice = new PresupuestoController();
        initView();


    }

    private void initView() {
        initDataBinding();
        obtenerPresupuestos();
        tablePresupuesto.getItems().clear();
        tablePresupuesto.setItems(listaPresupuestosDto);
        listenerSelection();
        mostrarPresupuestos();
    }

    private void mostrarPresupuestos() {
        txtFiltrarPresupuesto.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarTablas(newValue.toLowerCase());
        });
    }

    private void filtrarTablas(String valorBusqueda) {
        ObservableList<PresupuestoDto> presupuestoFiltrado = FXCollections.observableArrayList();
        for (PresupuestoDto presupuestoDto : listaPresupuestosDto) {
            if (presupuestoDto.idPresupuesto().toLowerCase().contains(valorBusqueda.toLowerCase()) ||
                    presupuestoDto.nombre().toLowerCase().contains(valorBusqueda.toLowerCase()))
            {
                presupuestoFiltrado.add(presupuestoDto);
            }
        }
        tablePresupuesto.setItems(presupuestoFiltrado);
    }


    private void initDataBinding() {
        tcIdPresupuesto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idPresupuesto()));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcMontoAsignado.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().montoAsignado())));
        tcMontoGastado.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().montoGastado())));
    }

    private void obtenerPresupuestos() {
        listaPresupuestosDto.addAll(presupuestoControllerSevice.obtenerPresupuesto());
    }


    private void listenerSelection() {
        tablePresupuesto.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            presupeuestoSeleccioando = newSelection;
            mostrarInformacionPresupuesto(presupeuestoSeleccioando);
        });
    }

    private void mostrarInformacionPresupuesto(PresupuestoDto presupeuestoSeleccioando) {
        if(presupeuestoSeleccioando != null){
            txtIdPresupuesto.setText(presupeuestoSeleccioando.idPresupuesto());
            txtNombre.setText(presupeuestoSeleccioando.nombre());
            txtMontoAsignado.setText(String.valueOf(presupeuestoSeleccioando.montoAsignado()));


        }
    }

    private void cargarCategorias() {
        // Ejemplo: obtener las categorías desde algún servicio
        ObservableList<Categoria> categorias = FXCollections.observableArrayList(billerteraVirtual.getListaCategorias());

        System.out.println("Categorias cargadas: " + categorias.size());  // Verifica cuántas categorías se cargan
        /// Asignar las categorías al ComboBox
        comboCategoria.setItems(categorias);


        // (Opcional) Seleccionar una categoría por defecto
        if (!categorias.isEmpty()) {
            comboCategoria.getSelectionModel().selectFirst(); // Selecciona la primera categoría por defecto
        }
    }

    private void actualizarPresupuesto() {
        boolean presupuestoActualizado= false;
        //1. Capturar los datos
        String idActual = presupeuestoSeleccioando.idPresupuesto();
        PresupuestoDto presupuestoDto = construirPresupuestoDto();
        //2. verificar el empleado seleccionado
        if(presupeuestoSeleccioando != null){
            //3. Validar la información
            if(datosValidos(presupeuestoSeleccioando)){
                presupuestoActualizado = presupuestoControllerSevice.actualizarPresupuesto(idActual, presupuestoDto);
                if(presupuestoActualizado){
                    listaPresupuestosDto.remove(presupeuestoSeleccioando);
                    listaPresupuestosDto.add(presupuestoDto);
                    tablePresupuesto.refresh();
                    mostrarMensaje("Notificación presupuesto", "presupuesto actualizado", "El presupuesto se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposPresupuesto();
                }else{
                    mostrarMensaje("Notificación presupuesto", "presupuesto no actualizado", "El presupuesto no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                }
            }else{
                mostrarMensaje("Notificación presupuesto", "presupuesto no actualizado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private void agregarPresupuesto() {
        PresupuestoDto presupuestoDto = construirPresupuestoDto();
        //2. Validar la información
        if(datosValidos(presupuestoDto)){
            if(presupuestoControllerSevice.crearPresupuesto(presupuestoDto)){
                listaPresupuestosDto.add(presupuestoDto);
                mostrarMensaje("Notificación Presupuesto", "Presupuesto creado", "El Presupuesto se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposPresupuesto();
                registrarAcciones(" Presupuesto Creado " + presupuestoDto.idPresupuesto(), 1, " El presupuesto se creo correctamente");
            }else{
                mostrarMensaje("Notificación Presupuesto", "Presupuesto no creado", "El Presupuesto no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación Presupuesto", "Presupuesto no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        presupuestoControllerSevice.registrarAcciones(mensaje, nivel, accion);
    }

    private void eliminarPresupuesto() {
        boolean presupuestoEliminado = false;
        if(presupeuestoSeleccioando != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar el presupuesto?")){
                presupuestoEliminado = presupuestoControllerSevice.eliminarPresupuesto(presupeuestoSeleccioando.idPresupuesto());
                if(presupuestoEliminado == true){
                    listaPresupuestosDto.remove(presupeuestoSeleccioando);
                    presupeuestoSeleccioando = null;
                    tablePresupuesto.getSelectionModel().clearSelection();
                    limpiarCamposPresupuesto();
                    mostrarMensaje("Notificación Presupuesto", "Usuario Presupuesto", "El Presupuesto se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación Presupuesto", "Presupuesto no eliminado", "El Presupuesto no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación Presupuesto", "Presupuesto no seleccionado", "Seleccionado un Presupuesto de la lista", Alert.AlertType.WARNING);
        }
    }

    private PresupuestoDto construirPresupuestoDto() {
        return new PresupuestoDto(
                txtIdPresupuesto.getText(),
                txtNombre.getText(),
                Double.valueOf(txtMontoAsignado.getText()),
                Double.valueOf(0)

        );
    }

    private void limpiarCamposPresupuesto() {
        txtIdPresupuesto.setText("");
        txtNombre.setText("");
        txtMontoAsignado.setText("");



    }

    private boolean datosValidos(PresupuestoDto presupuestoDto) {
        String mensaje = "";
        if(presupuestoDto.nombre() == null || presupuestoDto.nombre().equals(""))
            mensaje += "El nombre es invalido \n" ;
        if(presupuestoDto.idPresupuesto()== null || presupuestoDto.idPresupuesto() .equals(""))
            mensaje += "El apellido es invalido \n" ;
        if (presupuestoDto.montoAsignado() <= 0) {
            mensaje += "El monto asignado debe ser mayor que 0.\n";
        }
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación usuario","Datos invalidos",mensaje, Alert.AlertType.WARNING);
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


}

