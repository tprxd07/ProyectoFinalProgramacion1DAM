package org.example.proyectofinalprogramacion1dam.utils;

import javafx.scene.image.Image;

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
}
