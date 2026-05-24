package org.example.proyectofinalprogramacion1dam.model;

public class Videojuego extends Aplicacion {
    private boolean multijugador;

    /**
     * Hereda el constructor completo de la clase aplicacion, además de añadir
     * @param multijugador un boolean de true o false
     */
    public Videojuego(int id, String nombre, String descripcion, double precio, int descargas, Categoria categoria, int idDesarrollador, String imagen, boolean multijugador) {
        super(id, nombre, descripcion, precio,descargas, categoria, idDesarrollador, imagen);
        this.multijugador = multijugador;
    }

    /**
     * Hereda el constructor incompleto de la clase aplicacion, además de añadir
     * @param multijugador un boolean de true o false
     */
    public Videojuego(String nombre, String descripcion, double precio, Categoria categoria, int idDesarrollador, String imagen, boolean multijugador) {
        super(nombre, descripcion, precio, categoria, idDesarrollador, imagen);
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
