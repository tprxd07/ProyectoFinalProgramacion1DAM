package org.example.proyectofinalprogramacion1dam.utils;

import javafx.stage.Stage;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;
import org.example.proyectofinalprogramacion1dam.model.Usuario;

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

    public static String getMensajeInfo() {
        String msj = mensajeInfo;
        mensajeInfo = "";
        return msj;
    }

    public static void setMensajeInfo(String mensaje) {
        mensajeInfo = mensaje;
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
