package org.example.proyectofinalprogramacion1dam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.example.proyectofinalprogramacion1dam.utils.SceneManager;

public class SignUpController {
    @FXML
    private void onOkButtonClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();

        SceneManager.cambiarEscena(scene, "/org/example/proyectofinalprogramacion1dam/view/Login.fxml");
    }
}
