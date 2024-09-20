package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UsuarioApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UsuarioApplication.class.getResource("Usuario.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Usuario");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}