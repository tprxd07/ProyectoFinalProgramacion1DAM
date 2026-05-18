package org.example.proyectofinalprogramacion1dam.model;

public class Utilidad extends Aplicacion {

    public Utilidad(int id, String nombre, String descripcion, double precio, String version, int descargas, Categoria categoria, int idDesarrollador, String imagen) {
        super(id, nombre, descripcion, precio, version, descargas, categoria, idDesarrollador, imagen);
    }

    public Utilidad(String nombre, String descripcion, double precio, String version, Categoria categoria, int idDesarrollador, String imagen) {
        super(nombre, descripcion, precio, version, categoria, idDesarrollador, imagen);
    }

    @Override
    public String toString(){
        return "Utilidad: "+super.toString();
    }
}
