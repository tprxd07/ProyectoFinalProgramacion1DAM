package org.example.proyectofinalprogramacion1dam.model;

import java.time.LocalDateTime;

public class Biblioteca {
    private int idApp;
    private int idUsuario;
    private LocalDateTime fechaAdquisicion;

    public Biblioteca(){}
    public Biblioteca(int idApp, int idUsuario, LocalDateTime fechaAdquisicion) {
        this.idApp = idApp;
        this.idUsuario = idUsuario;
        this.fechaAdquisicion = fechaAdquisicion;
    }
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

    public LocalDateTime getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDateTime fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    @Override
    public String toString() {
        return "Biblioteca{" +
                "idApp=" + idApp +
                ", idUsuario=" + idUsuario +
                ", fechaAdquisicion=" + fechaAdquisicion +
                '}';
    }
}
