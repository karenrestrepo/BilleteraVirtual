package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.CategoriaController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.UsuarioController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Categoria;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CategoriaViewController {

    UsuarioDto usuarioDtoSeleccionado;

    CategoriaController categoriaControllerService;
    ObservableList<CategoriaDto> listaCategoriaDto = FXCollections.observableArrayList();
    CategoriaDto categoriaSeleccionada;

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
    private TableView<CategoriaDto> tablaCategoria;

    @FXML
    private TableColumn<CategoriaDto, String> tpCategoria;

    @FXML
    private TableColumn<CategoriaDto, String> tpIdCategoria;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtIsCategoria;

    @FXML
    private TextField txtNombreCategoria;

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarCategoria();

    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregarCategoria();

    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminararCategoria();

    }

    @FXML
    void initialize() {
        categoriaControllerService = new CategoriaController();
        initView();

    }

    private void initView() {
        initDataBinding();
        obtenerCategoria();
        tablaCategoria.getItems().clear();
        tablaCategoria.setItems(listaCategoriaDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tpIdCategoria.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().idCategoria())));
        tpCategoria.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().nombre())));

    }

    private void listenerSelection() {
        tablaCategoria.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            categoriaSeleccionada = newSelection;
            mostrarInformacionCategoria(categoriaSeleccionada);
        });
    }

    private void mostrarInformacionCategoria(CategoriaDto categoriaSeleccionada) {
        if(categoriaSeleccionada != null){
            txtIsCategoria.setText(categoriaSeleccionada.idCategoria());
            txtNombreCategoria.setText(categoriaSeleccionada.nombre());
            txtDescripcion.setText(categoriaSeleccionada.descripcion());


        }
    }

    private void obtenerCategoria() {

        listaCategoriaDto.addAll(categoriaControllerService.obtenerCategoria());
    }

    private void actualizarCategoria() {
        boolean categoriaActualizado = false;
        //1. Capturar los datos
        String idActual = categoriaSeleccionada.idCategoria();
        CategoriaDto categoriaDto = construirCategoriaDto();
        //2. verificar el empleado seleccionado
        if(categoriaSeleccionada != null){
            //3. Validar la información
            if(datosValidos(categoriaSeleccionada)){
                categoriaActualizado = categoriaControllerService.actualizarCategoria(idActual, categoriaDto);
                if(categoriaActualizado){
                    listaCategoriaDto.remove(categoriaSeleccionada);
                    listaCategoriaDto.add(categoriaDto);
                    tablaCategoria.refresh();
                    mostrarMensaje("Notificación Categoria", "Categoria actualizado", "El Categoria se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposCategoria();
                }else{
                    mostrarMensaje("Notificación Categoria", "Categoria no actualizado", "El Categoria no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                }
            }else{
                mostrarMensaje("Notificación Categoria", "Categoria no actualizado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private void agregarCategoria() {
        CategoriaDto categoriaDto = construirCategoriaDto();
        //2. Validar la información
        if(datosValidos(categoriaDto)){
            if(categoriaControllerService.crearCategoria(categoriaDto)){
                listaCategoriaDto.add(categoriaDto);
                mostrarMensaje("Notificación categoria", "categoria creado", "El categoria se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposCategoria();
            }else{
                mostrarMensaje("Notificación categoria", "categoria no creado", "El categoria no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación categoria", "categoria no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }
    }

    private void eliminararCategoria() {
        boolean categoriaEliminado = false;
        if(categoriaSeleccionada != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar al usuario?")){
                categoriaEliminado = categoriaControllerService.eliminarCategoria(categoriaSeleccionada.idCategoria());
                if(categoriaEliminado == true){
                    listaCategoriaDto.remove(categoriaSeleccionada);
                    categoriaSeleccionada = null;
                    tablaCategoria.getSelectionModel().clearSelection();
                    limpiarCamposCategoria();
                    mostrarMensaje("Notificación Categoria", "Categoria eliminado", "La Categoria se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación Categoria", "Categoria no eliminado", "El Categoria no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación Categoria", "Categoria no seleccionado", "Seleccionado un Categoria de la lista", Alert.AlertType.WARNING);
        }
    }

    private CategoriaDto construirCategoriaDto() {
        return new CategoriaDto(
                txtIsCategoria.getText(),
                txtNombreCategoria.getText(),
                txtDescripcion.getText()

        );
    }

    private void limpiarCamposCategoria() {
        txtIsCategoria.setText("");
        txtNombreCategoria.setText("");
        txtDescripcion.setText("");



    }

    private boolean datosValidos(CategoriaDto categoriaDto) {
        String mensaje = "";
        if(categoriaDto.idCategoria()== null || categoriaDto.idCategoria().equals(""))
            mensaje += "El id es invalido \n" ;
        if(categoriaDto.nombre() == null || categoriaDto.nombre().equals(""))
            mensaje += "El nombre es invalido \n" ;
        if(categoriaDto.descripcion()== null || categoriaDto.descripcion().equals(""))
            mensaje += "La descripción es invalido \n" ;

        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación categoria","Datos invalidos",mensaje, Alert.AlertType.WARNING);
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
        usuarioDtoSeleccionado = usuarioDto;

    }
}
