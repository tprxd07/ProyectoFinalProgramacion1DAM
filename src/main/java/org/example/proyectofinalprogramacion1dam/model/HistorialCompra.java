package org.example.proyectofinalprogramacion1dam.model;

import java.time.LocalDateTime;

public class HistorialCompra {
    private int idApp;
    private int idUsuario;
    private double precioPagado;
    private LocalDateTime fechaCompra;

    public HistorialCompra(int idApp, int idUsuario, double precioPagado, LocalDateTime fechaCompra) {
        this.idApp = idApp;
        this.idUsuario = idUsuario;
        this.precioPagado = precioPagado;
        this.fechaCompra = fechaCompra;
    }

    public HistorialCompra(int idApp, int idUsuario, double precioPagado) {
        this.idApp = idApp;
        this.idUsuario = idUsuario;
        this.precioPagado = precioPagado;
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

    public double getPrecioPagado() {
        return precioPagado;
    }

    public void setPrecioPagado(double precioPagado) {
        this.precioPagado = precioPagado;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    @Override
    public String toString() {
        return "HistorialCompra{" +
                "idApp=" + idApp +
                ", idUsuario=" + idUsuario +
                ", precioPagado=" + precioPagado +
                ", fechaCompra=" + fechaCompra +
                '}';
    }
}
