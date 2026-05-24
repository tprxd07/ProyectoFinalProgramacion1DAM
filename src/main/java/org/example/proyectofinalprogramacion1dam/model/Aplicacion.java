package org.example.proyectofinalprogramacion1dam.model;

public class Aplicacion {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int descargas;
    private Categoria categoria;
    private int idDesarrollador;
    private String imagen;

    /**
     * Constructor completo, necesario para insertar/recibir datos de la BBDD
     */
    public Aplicacion(int id, String nombre, String descripcion, double precio, int descargas, Categoria categoria, int idDesarrollador, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.descargas = descargas;
        this.categoria = categoria;
        this.idDesarrollador = idDesarrollador;
        this.imagen = imagen;
    }

    /**
     * Constructor sin los datos que el usuario no va a poder cambiar de forma manual
     * El id se recibe de la BBDD
     * Las descargas se aumenta automaticamente
     */
    public Aplicacion(String nombre, String descripcion, double precio, Categoria categoria, int idDesarrollador, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
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
                ", descargas=" + descargas +
                ", categoria=" + categoria +
                ", idDesarrollador=" + idDesarrollador +
                '}';
    }
}
