package org.example.proyectofinalprogramacion1dam.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.BibliotecaDAO;
import org.example.proyectofinalprogramacion1dam.utils.SceneManager;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;
import org.example.proyectofinalprogramacion1dam.utils.Util;

public class TarjetaAppController {
    @FXML
    private ImageView portada;

    @FXML
    private Label precio;

    @FXML
    private Label titulo;

    private Aplicacion appActual;

    public void setDatos(Aplicacion app) {
        this.appActual=app;
        titulo.setText(app.getNombre());
        titulo.setWrapText(true);
        titulo.setMaxWidth(100);
        portada.setImage(Util.cargarImagen(app.getImagen()));

        Usuario user = Sesion.getUsuarioActual();

        if (BibliotecaDAO.usuarioTieneApp(user.getId(), app.getId())) {
            precio.setText("En propiedad");
            precio.getStyleClass().add("text-field");
        }if (app.getPrecio() == 0) {
            precio.setText("Gratis");
            precio.getStyleClass().add("label-gratis");
        } else {
            precio.setText(app.getPrecio() + "€");
            precio.getStyleClass().add("label-precio");
        }
    }

    @FXML
    private void abrirDetalles() {
        if (appActual != null) {
            Stage stage = (Stage) titulo.getScene().getWindow();
            SceneManager.mostrarDetalles(appActual, stage);
        }
    }
}
