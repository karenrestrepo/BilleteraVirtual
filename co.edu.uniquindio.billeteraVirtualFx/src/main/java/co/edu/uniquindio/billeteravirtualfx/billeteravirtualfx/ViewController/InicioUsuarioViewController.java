package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.PerfilUsuarioController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.TransaccionController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller.UsuarioController;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.PresupuestoDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.StackPane;

public class InicioUsuarioViewController {

    UsuarioController usuarioController;
    private UsuarioDto usuarioDto;
    ObservableList<UsuarioDto> listaUsuariosDto = FXCollections.observableArrayList();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuButton btnperfil;
    @FXML
    private Label nombreUsuario;

    @FXML
    private StackPane ctnContenido;

    @FXML
    void onInicio(ActionEvent event) {



    }

    @FXML
    void onEditarPerfil(ActionEvent event) {
        try {
            // Cargar el nuevo archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billeteravirtualfx/billeteravirtualfx/PerfilUsuario.fxml"));
            Parent fxml = loader.load(); PerfilUsuarioViewController perfilUsuarioController = loader.getController();
            perfilUsuarioController.setUser(usuarioDto);
            ctnContenido.getChildren().removeAll();
            ctnContenido.getChildren().setAll(fxml);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onTransaccion(ActionEvent event) {

        try {
             // Cargar el nuevo archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billeteravirtualfx/billeteravirtualfx/Transaccion.fxml"));
            Parent fxml = loader.load();
            TransaccionViewController transaccionViewController = loader.getController();
            transaccionViewController.setUser(usuarioDto);
            ctnContenido.getChildren().removeAll();
            ctnContenido.getChildren().setAll(fxml);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onPresupuesto(ActionEvent event) {
        try {
            // Cargar el nuevo archivo FXML
            Parent fxml = FXMLLoader.load(getClass().getResource("/co/edu/uniquindio/billeteravirtualfx/billeteravirtualfx/Presupuesto.fxml"));

            ctnContenido.getChildren().removeAll();
            ctnContenido.getChildren().setAll(fxml);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onCuenta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billeteravirtualfx/billeteravirtualfx/CuentaBancaria.fxml"));
            Parent fxml = loader.load();
            CuentaBancariaViewController cuentaBancariaViewController = loader.getController();
            cuentaBancariaViewController.setUser(usuarioDto);

            ctnContenido.getChildren().removeAll();
            ctnContenido.getChildren().setAll(fxml);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onCategoria(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/billeteravirtualfx/billeteravirtualfx/Categoria.fxml"));
            Parent fxml = loader.load();
            CategoriaViewController cateriaController = loader.getController();
            cateriaController.setUser(usuarioDto);

            ctnContenido.getChildren().removeAll();
            ctnContenido.getChildren().setAll(fxml);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void initialize() {

        setUsuario(usuarioDto);


    }

    private void obtenerUsuarios() {
        listaUsuariosDto.addAll(usuarioController.obtenerUsuarios());
    }



    public void setUsuario(UsuarioDto usuarioDto) {
        this.usuarioDto = usuarioDto;
        // Mostrar el nombre del usuario en la interfaz
        if (usuarioDto != null) {
            nombreUsuario.setText(usuarioDto.nombre());
        }
    }
}




