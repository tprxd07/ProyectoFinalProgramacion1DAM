package org.example.proyectofinalprogramacion1dam.utils;

import javafx.stage.Stage;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;
import org.example.proyectofinalprogramacion1dam.model.Usuario;

/**
 * Se encarga de mantener la sesion del usuario abierta, para diferenciar a la hora de las descargas, perfiles y reseñas
 */
public class Sesion {
    private static Usuario usuarioActual;
    private static String mensajeInfo = "";
    private static Aplicacion appSeleccionada;
    private static Stage stagePrincipal;

    public static void setUsuarioActual(Usuario u) {
        usuarioActual = u;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    //Cambia el mensaje de informacion segun la sesion, devuelve el mensaje
    public static String getMensajeInfo() {
        String msj = mensajeInfo;
        mensajeInfo = "";
        return msj;
    }

    public static Aplicacion getAppSeleccionada() {
        return appSeleccionada;
    }

    public static void setAppSeleccionada(Aplicacion appSeleccionada) {
        Sesion.appSeleccionada = appSeleccionada;
    }

    public static Stage getStagePrincipal() {
        return stagePrincipal;
    }

    public static void setStagePrincipal(Stage stage) {
        stagePrincipal = stage;
    }
}
