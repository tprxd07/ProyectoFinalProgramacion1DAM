package org.example.proyectofinalprogramacion1dam.utils;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.image.Image;

import javafx.scene.layout.Region;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.net.URL;

public class Util {
    public static Image cargarImagen(String nombreArchivo) {
        String ruta = "/org/example/proyectofinalprogramacion1dam/images/" + nombreArchivo;
        var resource = Util.class.getResource(ruta);

        if (resource == null) {
            System.err.println("Error: No se encuentra la imagen: " + ruta);
            return null;
        }
        return new Image(resource.toExternalForm());
    }
    public static void reproducirSonido(String archivo){
        try{
            String ruta= "/org/example/proyectofinalprogramacion1dam/sounds/"+ archivo;
            URL resource = Util.class.getResource(ruta);

            if (resource != null){
                AudioClip sonido = new AudioClip(resource.toExternalForm());
                sonido.play();
            }else{
                System.err.println("Archivo de sonido no encontrado: "+ruta);
            }
        }catch (Exception e){
            System.err.println("Error multimedia: " + e.getMessage());
        }
    }

    /**
     * Rota un elemento de la interfaz
     * @param nodo El elemento que queremos girar
     * @param angulo El angulo de la rotacion
     * @param ms Duración en milisegundos
     */
    public static void girar(javafx.scene.Node nodo, double angulo, int ms) {
        if (nodo == null) return;
        RotateTransition rt = new RotateTransition(Duration.millis(ms), nodo);
        rt.setToAngle(angulo);

        rt.setInterpolator(Interpolator.EASE_BOTH);

        rt.play();
    }

    /**
     * Anima un contenedor para que se estire a lo ancho
     * @param region Contenedor que se va a animar
     * @param anchoDestino Width que tendrá el contenedor
     * @param ms Milisegundos de transición entre su ancho original y el destino
     */
    public static void animarAncho(Region region, double anchoDestino, int ms) {
        if (anchoDestino > 0) region.setVisible(true);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(ms),
                        new KeyValue(region.minWidthProperty(), anchoDestino),
                        new KeyValue(region.prefWidthProperty(), anchoDestino),
                        new KeyValue(region.maxWidthProperty(), anchoDestino)
                )
        );
        if (anchoDestino == 0) {
            timeline.setOnFinished(e -> region.setVisible(false));
        }
        timeline.play();
    }
}
