package org.example.proyectofinalprogramacion1dam.model;

public class Videojuego extends Aplicacion {
    private boolean multijugador;
    public Videojuego() {
    }
    public Videojuego(int id, String nombre, String descripcion, double precio, String version, int descargas, Categoria categoria, int idDesarrollador, boolean multijugador) {
        super(id, nombre, descripcion, precio, version, descargas, categoria, idDesarrollador);
        this.multijugador = multijugador;
    }
    public Videojuego(String nombre, String descripcion, double precio, String version, Categoria categoria, int idDesarrollador, boolean multijugador) {
        super(nombre, descripcion, precio, version, categoria, idDesarrollador);
        this.multijugador = multijugador;
    }

    public boolean isMultijugador() {
        return multijugador;
    }

    public void setMultijugador(boolean multijugador) {
        this.multijugador = multijugador;
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                super.toString() +
                "multijugador=" + multijugador +
                '}';
    }
}
