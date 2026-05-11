package org.example.proyectofinalprogramacion1dam.model;

public class Usuario {
    private int id;
    private String nombreUsuario;
    private String email;
    private String contrasenia;
    private double saldo;

    public Usuario() {}
    public Usuario(int id, String nombreUsuario, String email, String contrasenia, double saldo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
        this.saldo = saldo;
    }
    public Usuario(String nombreUsuario, String email, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String   toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", email='" + email + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
