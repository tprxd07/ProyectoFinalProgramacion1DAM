package org.example.proyectofinalprogramacion1dam.utils;

import org.example.proyectofinalprogramacion1dam.model.Usuario;

public class Sesion {
    private static Usuario usuarioActual;
    private static String mensajeInfo = "";

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
}
