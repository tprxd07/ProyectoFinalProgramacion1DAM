package org.example.proyectofinalprogramacion1dam.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Sesion.setStagePrincipal(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/org/example/proyectofinalprogramacion1dam/view/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 600);
        stage.setTitle("Crank V1.0");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}