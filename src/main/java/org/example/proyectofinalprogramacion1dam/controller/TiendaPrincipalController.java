package org.example.proyectofinalprogramacion1dam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.proyectofinalprogramacion1dam.utils.Util;

import java.util.Arrays;
import java.util.List;

public class TiendaPrincipalController {
    @FXML
    private Button botonAjustes;
    @FXML
    private ImageView imagenEngranaje;

    @FXML
    private Button botonApps;

    @FXML
    private Button botonInicio;

    @FXML
    private Button botonJuegos;

    @FXML
    private TextField busqueda;

    @FXML
    private VBox panelAjustes;

    private boolean ajustesAbierto = false;

    private void actualizarEstadoMenu(Button botonPulsado) {
        List<Button> botones = Arrays.asList(botonApps, botonJuegos, botonInicio);

        for (Button b : botones) {
            b.getStyleClass().remove("nav-button-active");
            b.setViewOrder(0.0);
        }
        botonPulsado.getStyleClass().add("nav-button-active");
        botonPulsado.setViewOrder(-1.0);
    }

    @FXML
    private void menuElegido(ActionEvent event) {
        Button botonPulsado = (Button) event.getSource();
        actualizarEstadoMenu(botonPulsado);
    }

    public void menuAjustes() {
        ajustesAbierto=!ajustesAbierto;
        if (ajustesAbierto){
            Util.animarAncho(panelAjustes, 250, 300);
            Util.girar(imagenEngranaje, 90, 300);
            ajustesAbierto=true;
        }else{
            Util.animarAncho(panelAjustes, 0, 300);
            Util.girar(imagenEngranaje, 0, 300);
            ajustesAbierto=false;
        }

    }

}