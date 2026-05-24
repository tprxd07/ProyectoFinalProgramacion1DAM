package org.example.proyectofinalprogramacion1dam.model;

public class Utilidad extends Aplicacion {

    /**
     * Hereda el constructor al completo de la clase aplicacion, sin cambios
     */
    public Utilidad(int id, String nombre, String descripcion, double precio, int descargas, Categoria categoria, int idDesarrollador, String imagen) {
        super(id, nombre, descripcion, precio, descargas, categoria, idDesarrollador, imagen);
    }

    /**
     * Hereda el constructor incompleto de la clase aplicacion, sin cambios
     */
    public Utilidad(String nombre, String descripcion, double precio, Categoria categoria, int idDesarrollador, String imagen) {
        super(nombre, descripcion, precio , categoria, idDesarrollador, imagen);
    }

    @Override
    public String toString(){
        return "Utilidad: "+super.toString();
    }
}
