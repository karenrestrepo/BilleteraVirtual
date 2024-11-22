package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.CuentaAdmController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.TransaccionAdmController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CuentaDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.TransaccionDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.TipoCuenta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleStringProperty;

public class CuentaAdmViewController {
    CuentaAdmController cuentaAdmController;
    ObservableList<CuentaDto> listaCuentasDto = FXCollections.observableArrayList();
    CuentaDto cuentaSeleccionada;

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
    private TextField txtCuentaEliminar;

    @FXML
    private TextField txtIdCuenta;

    @FXML
    private TextField txtNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    private TextField txtTipoCuenta;
    @FXML
    private TextField txtSaldo;

    @FXML
    void onActualizarCuenta(ActionEvent event) {

    }

    @FXML
    void onAgregarCuenta(ActionEvent event) {
        crearCuenta();
    }

    private void crearCuenta() {
        CuentaDto cuentaDto = construirCuentaDto();
        if(datosValidos(cuentaDto)) {
            if (mostrarMensajeConfirmacion("¿Estas seguro de la creación de la cuenta?")) {
                if (cuentaAdmController.crearCuenta(cuentaDto)) {
                    listaCuentasDto.add(cuentaDto);
                    mostrarMensaje("Notificación Cuenta", "Cuenta creada", "La cuenta se ha creado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposCuenta();
                    registrarAcciones(" Cuenta Creada ", 1, " La Cuenta se creo correctamente");
                } else {
                    mostrarMensaje("Notificación Cuenta", "Cuenta no creado", "La Cuenta no se ha creado con éxito", Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje("Notificación Cuenta", "Cuenta no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }
        }
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        cuentaAdmController.registrarAcciones(mensaje, nivel, accion);
    }

    private void limpiarCamposCuenta() {
        txtIdCuenta.setText("");
        txtNumeroCuenta.setText("");
        txtTipoCuenta.setText("");
        txtNombreBanco.setText("");
        txtSaldo.setText("");
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

    private boolean datosValidos(CuentaDto cuentaDto) {
        String mensaje = "";
        if(cuentaDto.idCuenta() == null || cuentaDto.idCuenta().equals(""))
            mensaje += "El id es invalido \n" ;
        if(cuentaDto.numeroCuenta() == null || cuentaDto.numeroCuenta() .equals(""))
            mensaje += "El número de cuenta es invalido \n" ;
        if(cuentaDto.tipoCuenta() == null || cuentaDto.tipoCuenta().equals(""))
            mensaje += "El tipo es invalido \n" ;
        if(cuentaDto.nombreBanco() == null || cuentaDto.nombreBanco().equals(""))
            mensaje += "La cuenta de origen es invalida \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación cuenta","Datos invalidos",mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    private CuentaDto construirCuentaDto() {
        String tipoCuentaString = txtTipoCuenta.getText().toUpperCase(); // Convertir a mayúsculas para evitar problemas con el ingreso

        TipoCuenta tipoCuenta = null;
        try {
            // Intentar convertir el String a un valor del enum
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
                tipoCuenta, // Pasa el enum
                Double.parseDouble(txtSaldo.getText())
        );
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarCuenta();
    }

    private void eliminarCuenta() {
        boolean cuentaEliminada = false;
        String idCuenta = txtCuentaEliminar.getText();
        if(idCuenta != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar la cuenta?")){
                cuentaEliminada = cuentaAdmController.eliminarCuenta(idCuenta);
                if(cuentaEliminada == true){
                    listaCuentasDto.remove(idCuenta);
                    limpiarCamposCuenta();
                    mostrarMensaje("Notificación cuenta", "Cuenta eliminada", "La cuenta se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación cuenta", "Cuenta no eliminada", "La cuenta no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    void initialize() {
        cuentaAdmController = new CuentaAdmController();
    }

}

