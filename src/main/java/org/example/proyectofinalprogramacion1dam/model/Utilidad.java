package org.example.proyectofinalprogramacion1dam.model;

public class Utilidad extends Aplicacion {
    public Utilidad(){}

    public Utilidad(int id, String nombre, String descripcion, double precio, String version, int descargas, Categoria categoria, int idDesarrollador) {
        super(id, nombre, descripcion, precio, version, descargas, categoria, idDesarrollador);
    }

    public Utilidad(String nombre, String descripcion, double precio, String version, Categoria categoria, int idDesarrollador) {
        super(nombre, descripcion, precio, version, categoria, idDesarrollador);
    }

    @Override
    public String toString(){
        return "Utilidad: "+super.toString();
    }
}
