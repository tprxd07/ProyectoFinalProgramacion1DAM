package org.example.proyectofinalprogramacion1dam.model;

import java.time.LocalDateTime;

public class Biblioteca {
    private int idApp;
    private int idUsuario;

    public Biblioteca(){}
    public Biblioteca(int idApp, int idUsuario) {
        this.idApp = idApp;
        this.idUsuario = idUsuario;
    }

    public int getIdApp() {
        return idApp;
    }

    public void setIdApp(int idApp) {
        this.idApp = idApp;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Biblioteca{" +
                "idApp=" + idApp +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
