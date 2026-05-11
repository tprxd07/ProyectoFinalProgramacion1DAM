package org.example.proyectofinalprogramacion1dam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.UsuarioDAO;
import org.example.proyectofinalprogramacion1dam.utils.SceneManager;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;

public class SignUpController {
    @FXML
    private TextField textoUsuario;
    @FXML
    private TextField textoEmail;
    @FXML
    private TextField textoPassword;
    @FXML
    private TextField textoConfirmarPassword;
    @FXML
    private Label textoSesion;
    @FXML
    private void registrarUsuario() {
        String user = textoUsuario.getText();
        String email = textoEmail.getText();
        String pass = textoPassword.getText();
        String confirm = textoConfirmarPassword.getText();

        if (user.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            textoSesion.setText("Rellena todos los campos");
            return;
        }

        if (!pass.equals(confirm)) {
            textoSesion.setText("Las contraseñas no coinciden");
            return;
        }

        if (UsuarioDAO.findByEmail(email) != null) {
            textoSesion.setText("Este correo ya tiene una cuenta asignada");
            return;
        }

        if (UsuarioDAO.findByUsername(user) != null) {
            textoSesion.setText("Ya existe este nombre de usuario");
            return;
        }

        Usuario nuevoUsuario = new Usuario(user, email, pass);
        boolean exito = UsuarioDAO.addUsuario(nuevoUsuario);

        if (exito) {
            Sesion.setMensajeInfo("Bienvenido, " + nuevoUsuario.getNombreUsuario());

            SceneManager.cambiarEscena(textoSesion.getScene(), "Login.fxml");
        } else {
            textoSesion.setText("Error al conectar con la base de datos");
        }
    }
}
