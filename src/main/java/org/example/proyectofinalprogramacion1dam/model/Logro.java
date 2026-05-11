package org.example.proyectofinalprogramacion1dam.model;

public class Logro {
    private int id;
    private String nombre;
    private String descripcion;
    private int idVideojuego;

    public Logro() {}
    public Logro(int id, String nombre, String descripcion, int idVideojuego) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idVideojuego = idVideojuego;
    }
    public Logro(String nombre, String descripcion, int idVideojuego) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idVideojuego = idVideojuego;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(int idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    @Override
    public String toString() {
        return "Logro{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", idVideojuego=" + idVideojuego +
                '}';
    }
}
