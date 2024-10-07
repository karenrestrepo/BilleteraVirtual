package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils.*;
import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class BilleteraVirtualApplication extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Billetera Virtual");
        mostrarVentanaLogin();
        inicializarAplicacion();
    }

    public void mostrarVentanaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BilleteraVirtualApplication.class.getResource("Login.fxml"));
            AnchorPane rootLayout = loader.load();
//            usuarioViewController.setAplicacion(this);
            //Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            //scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inicializarAplicacion() {
        BillerteraVirtual billerteraVirtual = BilleteraVirtualUtils.inicializarDatos();

        try {
            // Guarda los usuarios inicializados
            Persistencia.guardarUsuarios(billerteraVirtual.getListaUsuarios());

            // Carga los usuarios desde el archivo
            ArrayList<Usuario> usuariosCargados = Persistencia.cargarUsuarios();
            ArrayList<Transaccion> transaccionesCargadas = Persistencia.cargarTransacciones();

            // Actualiza la lista de usuarios en la billetera
            billerteraVirtual.getListaUsuarios().clear();
            billerteraVirtual.getListaUsuarios().addAll(usuariosCargados);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch();
    }
}