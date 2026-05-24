package org.example.proyectofinalprogramacion1dam.utils;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.image.Image;

import javafx.scene.layout.Region;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.net.URL;

/**
 * Clase con apartados de utilidad varios, necesarios en distintas clases
 */
public class Util {
    /**
     * Carga una imagen desde los archivos
     * @param nombreArchivo nombre del archivo
     * @return Devuelve la imagen, o una imagen por defecto si no se ha encontrado
     */
    public static Image cargarImagen(String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            System.err.println("Advertencia: El campo de imagen está vacío en la BD.");
            nombreArchivo = "placeholder.png";
        }
        String ruta = "/org/example/proyectofinalprogramacion1dam/images/" + nombreArchivo.trim();
        URL resource = Util.class.getResource(ruta);
        if (resource == null) {
            System.err.println("Error: No se encuentra el archivo físico en los recursos: " + ruta + " -> Cargando placeholder temporal.");
            ruta = "/org/example/proyectofinalprogramacion1dam/images/placeholder.png";
            resource = Util.class.getResource(ruta);
        }
        return new Image(resource.toExternalForm());
    }

    /**
     * Metodo para reproducir un sonido que se encuentre en los archivos
     * @param archivo nombre del sonido
     * */
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

    /**
     * Anima un contenedor para que se estire su altura
     * @param region Contenedor que se va a animar
     * @param altoDestino Height que tendrá el contenedor
     * @param ms Milisegundos que dura la animación
     */
    public static void animarAlto(Region region, double altoDestino, int ms){
        if (altoDestino > 0) region.setVisible(true);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(ms),
                        new KeyValue(region.minHeightProperty(), altoDestino),
                        new KeyValue(region.prefHeightProperty(), altoDestino),
                        new KeyValue(region.maxHeightProperty(), altoDestino)
                )
        );
        if (altoDestino == 0) {
            timeline.setOnFinished(e -> region.setVisible(false));
        }
        timeline.play();
    }
}

