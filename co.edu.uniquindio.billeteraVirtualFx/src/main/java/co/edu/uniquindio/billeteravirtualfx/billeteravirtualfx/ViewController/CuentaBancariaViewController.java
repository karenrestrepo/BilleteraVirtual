package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.CuentaBancariaController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.PresupuestoController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CuentaDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.PresupuestoDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.BillerteraVirtual;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.TipoCuenta;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CuentaBancariaViewController {

    CuentaBancariaController cuentaControllerSevice;
    ObservableList<CuentaDto> listaCuentasDto = FXCollections.observableArrayList();
    BillerteraVirtual billerteraVirtual;
    CuentaDto cuentaSellecionada;
    UsuarioDto usuarioSeleccionado;

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
    private TableView<CuentaDto> tableCuentas;

    @FXML
    private TableColumn<CuentaDto, String> tcIdCuenta;

    @FXML
    private TableColumn<CuentaDto, String> tcNombreBanco;

    @FXML
    private TableColumn<CuentaDto, String> tcNumeroCuenta;

    @FXML
    private TableColumn<CuentaDto, String> tcTipoCuenta;
    @FXML
    private TableColumn<CuentaDto, String> tcSaldoCuenta;


    @FXML
    private TextField txtFiltrarCuentas;

    @FXML
    private TextField txtIdCuenta;

    @FXML
    private TextField txtSaldoCuenta;

    @FXML
    private TextField txtNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    private TextField txtTipoCuenta;

    @FXML
    void onActualizarCuenta(ActionEvent event) {
        actualizarCuenta();

    }

    @FXML
    void onAgregarCuenta(ActionEvent event) {
        agregarCuenta();

    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarCuenta();

    }

    @FXML
    void initialize() {
        cuentaControllerSevice = new CuentaBancariaController();
        initView();


    }

    private void initView() {
        initDataBinding();
        obtenerCuenta();
        tableCuentas.getItems().clear();
        tableCuentas.setItems(listaCuentasDto);
        listenerSelection();
        mostrarCuentas();
    }

    private void obtenerCuenta() {
        listaCuentasDto.addAll(cuentaControllerSevice.obtenercuentas());
    }

    private void mostrarCuentas() {
        txtFiltrarCuentas.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarTablas(newValue.toLowerCase());
        });
    }

    private void filtrarTablas(String valorBusqueda) {
        ObservableList<CuentaDto> cuentaFiltrado = FXCollections.observableArrayList();
        for (CuentaDto cuentaDto : listaCuentasDto) {
            if (cuentaDto.idCuenta().toLowerCase().contains(valorBusqueda.toLowerCase()) ||
                    cuentaDto.numeroCuenta().toLowerCase().contains(valorBusqueda.toLowerCase()))
            {
                cuentaFiltrado.add(cuentaDto);
            }
        }
        tableCuentas.setItems(cuentaFiltrado);
    }

    private void listenerSelection() {
        tableCuentas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            cuentaSellecionada = newSelection;
            mostrarInformacionCuenta(cuentaSellecionada);
        });
    }

    private void mostrarInformacionCuenta(CuentaDto cuentaSellecionada) {
        if(cuentaSellecionada != null){
            txtIdCuenta.setText(cuentaSellecionada.idCuenta());
            txtNombreBanco.setText(cuentaSellecionada.nombreBanco());
            txtNumeroCuenta.setText(cuentaSellecionada.numeroCuenta());
            txtTipoCuenta.setText(cuentaSellecionada.tipoCuenta().name());
            txtSaldoCuenta.setText(String.valueOf(cuentaSellecionada.saldo()));


        }
    }

    private void initDataBinding() {
        tcIdCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idCuenta()));
        tcNombreBanco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreBanco()));
        tcNumeroCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().numeroCuenta()));
        tcTipoCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tipoCuenta().name()));
        tcSaldoCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().saldo())));

    }

    private void actualizarCuenta() {
        boolean CuentaActualizada= false;
        //1. Capturar los datos
        String idActual = cuentaSellecionada.idCuenta();
        CuentaDto cuentaDto = construirCuentaDto();
        //2. verificar el empleado seleccionado
        if(cuentaSellecionada != null){
            //3. Validar la información
            if(datosValidos(cuentaSellecionada)){
                CuentaActualizada = cuentaControllerSevice.actualizarCuenta(idActual, cuentaSellecionada);
                if(CuentaActualizada){
                    listaCuentasDto.remove(cuentaSellecionada);
                    listaCuentasDto.add(cuentaDto);
                    tableCuentas.refresh();
                    mostrarMensaje("Notificación Cuenta", "Cuenta actualizado", "El Cuenta se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposCuenta();
                }else{
                    mostrarMensaje("Notificación Cuenta", "Cuenta no actualizado", "El Cuenta no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                }
            }else{
                mostrarMensaje("Notificación Cuenta", "Cuenta no actualizado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private void agregarCuenta() {
        CuentaDto cuentaDto = construirCuentaDto();
        if(datosValidos(cuentaDto)){
            if(cuentaControllerSevice.crearCuenta(cuentaDto)){
                listaCuentasDto.add(cuentaDto);
                mostrarMensaje("Notificación Cuenta", "Cuenta creado", "La Cuenta se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposCuenta();
                registrarAcciones(" Cuenta Creado " + cuentaDto.idCuenta(), 1, " La Cuenta se creo correctamente");
            }else{
                mostrarMensaje("Notificación Cuenta", "Cuenta no creado", "La Cuenta no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación Cuenta", "Cuenta no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        cuentaControllerSevice.registrarAcciones(mensaje, nivel, accion);
    }

    private void eliminarCuenta() {
        boolean cuentaEliminado = false;
        if(cuentaSellecionada != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar La cuenta?")){
                cuentaEliminado = cuentaControllerSevice.eliminarCuenta(cuentaSellecionada.idCuenta());
                if(cuentaEliminado == true){
                    listaCuentasDto.remove(cuentaSellecionada);
                    cuentaSellecionada = null;
                    tableCuentas.getSelectionModel().clearSelection();
                    limpiarCamposCuenta();
                    mostrarMensaje("Notificación Cuenta", "Cuenta eliminada", "La Cuenta se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación Cuenta", "Cuenta eliminada", "La Cuenta no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación Cuenta", "Cuenta no seleccionado", "Seleccionada una Cuenta de la lista", Alert.AlertType.WARNING);
        }
    }

    private CuentaDto construirCuentaDto() {
        // Capturar el valor ingresado en el campo de texto
        String tipoCuentaString = txtTipoCuenta.getText().toUpperCase(); // Convertir a mayúsculas para evitar problemas con el ingreso

        TipoCuenta tipoCuenta = null;
        try {

            tipoCuenta = TipoCuenta.valueOf(tipoCuentaString);
        } catch (IllegalArgumentException e) {
            // Si ocurre un error (por ejemplo, el valor no es válido), se maneja aquí
            mostrarMensaje("Error", "Tipo de cuenta inválido", "El tipo de cuenta ingresado no es válido.", Alert.AlertType.ERROR);
            return null; // Si el tipo no es válido, devolver null o manejarlo según lo desees
        }

        // Ahora que tienes un valor de tipo TipoCuenta, construimos el DTO
        return new CuentaDto(
                txtIdCuenta.getText(),
                txtNombreBanco.getText(),
                txtNumeroCuenta.getText(),
                tipoCuenta ,// Pasa el enum
                Double.valueOf(txtSaldoCuenta.getText())
        );
    }


    private void limpiarCamposCuenta() {
        txtIdCuenta.setText("");
        txtNombreBanco.setText("");
        txtNumeroCuenta.setText("");
        txtTipoCuenta.setText("");



    }

    private boolean datosValidos(CuentaDto cuentaDto) {
        String mensaje = "";
        if (cuentaDto.idCuenta() == null || cuentaDto.idCuenta().equals(""))
            mensaje += "El id cuenta es invalido \n";
        if (cuentaDto.nombreBanco() == null || cuentaDto.nombreBanco().equals(""))
            mensaje += "El nombre de banco es invalido \n";
        if (cuentaDto.numeroCuenta() == null || cuentaDto.numeroCuenta().equals(""))
            mensaje += "El numero de cuenta es invalido \n";
        if (cuentaDto.tipoCuenta() == null || cuentaDto.tipoCuenta().equals(""))
            mensaje += "El tipo cuenta es invalido \n";

        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación Cuenta","Datos invalidos",mensaje, Alert.AlertType.WARNING);
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


    public void setUser(UsuarioDto usuarioDto) {
        usuarioSeleccionado = usuarioDto;
    }
}

