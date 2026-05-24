package org.example.proyectofinalprogramacion1dam.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;
import org.example.proyectofinalprogramacion1dam.model.Biblioteca;
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.AplicacionDAO;
import org.example.proyectofinalprogramacion1dam.modelDAO.BibliotecaDAO;
import org.example.proyectofinalprogramacion1dam.utils.Alerta;
import org.example.proyectofinalprogramacion1dam.utils.SceneManager;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PerfilController implements Initializable {

    @FXML
    private Label labelNombreUsuario;
    @FXML
    private FlowPane apartadoBiblioteca;
    @FXML
    private Button cerrarSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuario usuarioActual = Sesion.getUsuarioActual();

        if (usuarioActual != null) {
            labelNombreUsuario.setText(usuarioActual.getNombre());
            cargarBibliotecaUsuario(usuarioActual.getId());
        }
    }

    /**
     * Carga la biblioteca del usuario
     * @param idUsuario Id del usuario con la sesion iniciada
     */
    private void cargarBibliotecaUsuario(int idUsuario) {
        apartadoBiblioteca.getChildren().clear();
        List<Biblioteca> registrosAdquisiciones = BibliotecaDAO.findByUsuario(idUsuario);

        if (registrosAdquisiciones != null && !registrosAdquisiciones.isEmpty()) {
            for (Biblioteca registro : registrosAdquisiciones) {
                // Paso B: Buscar los datos de la aplicación uno a uno por su ID correlativa
                Aplicacion app = AplicacionDAO.findById(registro.getIdApp());

                if (app != null) {
                    // Generar la tarjeta visual utilizando tu gestor de escenas estructurado
                    Node tarjeta = SceneManager.cargarTarjeta(app);
                    if (tarjeta != null) {
                        apartadoBiblioteca.getChildren().add(tarjeta);
                    }
                }
            }
        } else {
            // Mensaje informativo si el usuario no tiene ninguna aplicación en propiedad
            Label msjVacio = new Label("Aún no has adquirido ninguna aplicación.");
            msjVacio.getStyleClass().add("texto-cursivo");
            apartadoBiblioteca.getChildren().add(msjVacio);
        }
    }

    //Cierra la sesion actual
    @FXML
    public void cerrarSesionActiva(javafx.event.ActionEvent event) {
        try {
            //Limpiamos el usuario de la sesion global
            Sesion.setUsuarioActual(null);
            //cierra todas las ventanas
            java.util.List<javafx.stage.Window> ventanas = new java.util.ArrayList<>(javafx.stage.Stage.getWindows());
            for (javafx.stage.Window ventana : ventanas) {
                if (ventana instanceof javafx.stage.Stage) {
                    ((javafx.stage.Stage) ventana).close();
                }
            }
            //abre el login
            SceneManager.abrirVentana("Login.fxml","Crank v1.0", true);

        } catch (Exception e) {
            System.err.println("Error crítico al fulminar ventanas y abrir el Login: " + e.getMessage());
            e.printStackTrace();
        }
    }
}