package org.example.proyectofinalprogramacion1dam.model;

public class Desarrollador {
    private int id;
    private String nombre;
    private String pais;
    private String webOficial;

    public Desarrollador(){
    }

    public Desarrollador(int id, String nombre, String pais, String webOficial) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.webOficial = webOficial;
    }

    public Desarrollador(String nombre, String pais, String webOficial) {
        this.nombre = nombre;
        this.pais = pais;
        this.webOficial = webOficial;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getWebOficial() {
        return webOficial;
    }

    public void setWebOficial(String webOficial) {
        this.webOficial = webOficial;
    }

    @Override
    public String toString() {
        return "Desarrollador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                ", webOficial='" + webOficial + '\'' +
                '}';
    }
}
