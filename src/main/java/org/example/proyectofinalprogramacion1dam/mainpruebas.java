package org.example.proyectofinalprogramacion1dam;

import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.UsuarioDAO;

public class mainpruebas {
    public static void main(String[] args) {
        Usuario usuario = UsuarioDAO.findById(2);
        System.out.println(usuario);
    }
}
