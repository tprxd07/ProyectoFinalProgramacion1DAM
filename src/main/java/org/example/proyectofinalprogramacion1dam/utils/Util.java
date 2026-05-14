package org.example.proyectofinalprogramacion1dam.utils;

import javafx.scene.image.Image;

import javafx.scene.media.AudioClip;
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
}
