package org.example.proyectofinalprogramacion1dam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import org.example.proyectofinalprogramacion1dam.utils.SceneManager;

public class LoginController {
    @FXML
    private AnchorPane contenedorSesion;

    @FXML
    private void mostrarLogin() {
        SceneManager.inyectarEscena(contenedorSesion, "LoginInfo.fxml");
    }

    @FXML
    private void mostrarSignUp() {
        SceneManager.inyectarEscena(contenedorSesion, "SignUp.fxml");
    }

    @FXML
    public void initialize(){
        mostrarLogin();
    }
}
