package org.example.proyectofinalprogramacion1dam.model;

import java.time.LocalDateTime;

public class Resenia {
    private int id;
    private int puntuacion;
    private String comentario;
    private LocalDateTime fechaResenia;
    private int idApp;
    private int idUsuario;

    public Resenia(){}

    public Resenia(int id, int puntuacion, String comentario, LocalDateTime fechaResenia, int idApp, int idUsuario) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fechaResenia = fechaResenia;
        this.idApp = idApp;
        this.idUsuario = idUsuario;
    }

    public Resenia(int idUsuario, int idApp, String comentario, int puntuacion) {
        this.idUsuario = idUsuario;
        this.idApp = idApp;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaResenia() {
        return fechaResenia;
    }

    public void setFechaResenia(LocalDateTime fechaResenia) {
        this.fechaResenia = fechaResenia;
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
        return "Resenia{" +
                "id=" + id +
                ", puntuacion=" + puntuacion +
                ", comentario='" + comentario + '\'' +
                ", fechaResenia=" + fechaResenia +
                ", idApp=" + idApp +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
