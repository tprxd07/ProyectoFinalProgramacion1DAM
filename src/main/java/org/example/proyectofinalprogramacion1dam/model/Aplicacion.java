package org.example.proyectofinalprogramacion1dam.model;

public class Aplicacion {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String version;
    private int descargas;
    private Categoria categoria;
    private int idDesarrollador;
    private String imagen;

    public Aplicacion(int id, String nombre, String descripcion, double precio, String version, int descargas, Categoria categoria, int idDesarrollador, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.version = version;
        this.descargas = descargas;
        this.categoria = categoria;
        this.idDesarrollador = idDesarrollador;
        this.imagen = imagen;
    }

    public Aplicacion(String nombre, String descripcion, double precio, String version, Categoria categoria, int idDesarrollador, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.version = version;
        this.categoria = categoria;
        this.idDesarrollador = idDesarrollador;
        this.imagen = imagen;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getIdDesarrollador() {
        return idDesarrollador;
    }

    public void setIdDesarrollador(int idDesarrollador) {
        this.idDesarrollador = idDesarrollador;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Aplicacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", version='" + version + '\'' +
                ", descargas=" + descargas +
                ", categoria=" + categoria +
                ", idDesarrollador=" + idDesarrollador +
                '}';
    }
}
