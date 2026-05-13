package org.example.proyectofinalprogramacion1dam.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;

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
     * Inyecta un FXML dentro de un contenedor (Pane o derivados).
     * Este metodo cambia solo una parte de la ventana
     * @param contenedor El panel donde queremos meter el diseño.
     * @param fxmlFileName Solo el nombre del archivo ("Login.fxml").
     */
    public static Object inyectarEscena(Pane contenedor, String fxmlFileName) {
        try {
            String ruta = "/org/example/proyectofinalprogramacion1dam/view/" + fxmlFileName;

            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(ruta));
            Node nuevoNodo = loader.load();

            //Se limpia la escena que estuviera antes
            contenedor.getChildren().setAll(nuevoNodo);

            //Solo se ajusta si es un anchor pane para que rellene el espacio restante
            if (contenedor instanceof AnchorPane) {
                AnchorPane.setTopAnchor(nuevoNodo, 0.0);
                AnchorPane.setBottomAnchor(nuevoNodo, 0.0);
                AnchorPane.setLeftAnchor(nuevoNodo, 0.0);
                AnchorPane.setRightAnchor(nuevoNodo, 0.0);
            }

            //Solo si es VBox se fuerza a que crezca verticalmente
            else if (contenedor instanceof VBox) {
                VBox.setVgrow(nuevoNodo, Priority.ALWAYS);
            }

            //Solo si es HBox se fuerza a que crezca horizontalmente
            else if (contenedor instanceof HBox) {
                HBox.setHgrow(nuevoNodo, Priority.ALWAYS);
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
}