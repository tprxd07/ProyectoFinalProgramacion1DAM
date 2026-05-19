package org.example.proyectofinalprogramacion1dam.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion1dam.controller.DetalleAppController;
import org.example.proyectofinalprogramacion1dam.controller.TarjetaAppController;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;

import java.io.IOException;


public class SceneManager {


    /**
     * Cambia el contenido completo de la ventana
     * @param currentScene Escena actual de la ventana
     * @param fxmlFileName Escena a la que se va a cambiar
     */
    public static void cambiarEscena(Scene currentScene, String fxmlFileName) {
        try {
            String ruta = "/org/example/proyectofinalprogramacion1dam/view/" + fxmlFileName;
            Parent root = FXMLLoader.load(SceneManager.class.getResource(ruta));
            currentScene.setRoot(root);
        } catch (IOException e) {
            System.err.println("Error: No se pudo cargar el archivo " + fxmlFileName);
            e.printStackTrace();
        }
    }

    /**
     * Recoge los datos de una app para crear un apartado visual con la portada, nombre y precio
     * @param app
     * @return
     */
    public static Node cargarTarjeta(Aplicacion app) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/org/example/proyectofinalprogramacion1dam/view/TarjetaApp.fxml"));
            Node tarjeta = loader.load();
            TarjetaAppController controller = loader.getController();
            controller.setDatos(app);

            return tarjeta;
        } catch (IOException e) {
            System.err.println("Error al cargar la tarjeta: " + e.getMessage());
            return null;
        }
    }

    /**
     * Inyecta un FXML dentro de un contenedor (Pane o derivados).
     * Este metodo cambia solo una parte de la ventana
     * @param contenedor El panel donde queremos meter el diseño.
     * @param fxmlFileName Solo el nombre del archivo ("Login.fxml").
     */
    public static Object inyectarEscena(Region contenedor, String fxmlFileName) { // Cambiado Pane por Region
        try {
            String ruta = "/org/example/proyectofinalprogramacion1dam/view/" + fxmlFileName;
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(ruta));
            Node nuevoNodo = loader.load();

            //Si el contenedor es un ScrollPane, usamos setContent
            if (contenedor instanceof ScrollPane) {
                ScrollPane scroll = (ScrollPane) contenedor;
                scroll.setContent(nuevoNodo);
            } else {
                // Si es un contenedor normal, usamos getChildren
                ((Pane) contenedor).getChildren().setAll(nuevoNodo);
            }

            //Ajustes de crecimiento según el contenedor
            if (contenedor instanceof AnchorPane) {
                AnchorPane.setTopAnchor(nuevoNodo, 0.0);
                AnchorPane.setBottomAnchor(nuevoNodo, 0.0);
                AnchorPane.setLeftAnchor(nuevoNodo, 0.0);
                AnchorPane.setRightAnchor(nuevoNodo, 0.0);
            }
            else if (contenedor instanceof VBox) {
                VBox.setVgrow(nuevoNodo, Priority.ALWAYS);
            }
            else if (contenedor instanceof HBox) {
                HBox.setHgrow(nuevoNodo, Priority.ALWAYS);
            }
            // Ajuste para el ScrollPane
            else if (contenedor instanceof ScrollPane scroll) {
                scroll.setFitToWidth(true);
                scroll.setFitToHeight(true);
            }

            return loader.getController();

        } catch (IOException e) {
            System.err.println("Error: No se pudo cargar el archivo FXML en " + fxmlFileName);
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Error: No se encontró el archivo. Revisa la ruta: " + fxmlFileName);
        }
        return null;
    }


    /**
     * Carga la página de detalles de una app al seleccionarla
     * @param app Aplicacion de la que se van a cargar los detalles
     */
    public static void mostrarDetalles(Aplicacion app, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/org/example/proyectofinalprogramacion1dam/view/DetallesApp.fxml"));
            Parent root = loader.load();
            DetalleAppController controller = loader.getController();
            //controller.setDatos(app);

            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Abre una ventana nueva independiente
     * @param fxmlFileName Nombre del archivo .fxml
     * @param titulo Título que tendrá la ventana
     * @param modal Si es true, bloquea la ventana principal hasta que se cierre esta
     * @return El controlador de la nueva ventana
     */
    public static Object abrirVentana(String fxmlFileName, String titulo, boolean modal) {
        try {
            String ruta = "/org/example/proyectofinalprogramacion1dam/view/" + fxmlFileName;
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(ruta));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle(titulo);
            newStage.setScene(new Scene(root));

            if (modal) {
                newStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            }

            newStage.show();
            return loader.getController();

        } catch (IOException e) {
            System.err.println("Error al abrir la ventana: " + fxmlFileName);
            return null;
        }
    }

}