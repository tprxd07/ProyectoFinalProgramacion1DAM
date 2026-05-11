package org.example.proyectofinalprogramacion1dam.model;

import java.time.LocalDateTime;

public class LogroObtenido {
    private int idUsuario;
    private int idLogro;
    private LocalDateTime fechaDesbloqueo;

    public LogroObtenido(){}
    public LogroObtenido(int idUsuario, int idLogro, LocalDateTime fechaDesbloqueo) {
        this.idUsuario = idUsuario;
        this.idLogro = idLogro;
        this.fechaDesbloqueo = fechaDesbloqueo;
    }
    public LogroObtenido(int idLogro, int idUsuario) {
        this.idLogro = idLogro;
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdLogro() {
        return idLogro;
    }

    public void setIdLogro(int idLogro) {
        this.idLogro = idLogro;
    }

    public LocalDateTime getFechaDesbloqueo() {
        return fechaDesbloqueo;
    }

    public void setFechaDesbloqueo(LocalDateTime fechaDesbloqueo) {
        this.fechaDesbloqueo = fechaDesbloqueo;
    }

    @Override
    public String toString() {
        return "LogroObtenido{" +
                "idUsuario=" + idUsuario +
                ", idLogro=" + idLogro +
                ", fechaDesbloqueo=" + fechaDesbloqueo +
                '}';
    }
}
