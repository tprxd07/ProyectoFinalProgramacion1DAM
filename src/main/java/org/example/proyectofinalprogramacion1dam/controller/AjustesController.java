package org.example.proyectofinalprogramacion1dam.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AjustesController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}
