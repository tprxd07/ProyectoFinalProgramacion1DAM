package org.example.proyectofinalprogramacion1dam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.UsuarioDAO;
import org.example.proyectofinalprogramacion1dam.utils.SceneManager;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane contenedorSesion;
    private TextField textoCorreoLogin;
    private TextField textoContraseniaLogin;
    @FXML
    private Text textoSesion;

    @FXML
    private void mostrarLogin() {
        SceneManager.inyectarEscena(contenedorSesion, "LoginInfo.fxml");
    }

    @FXML
    private void mostrarSignUp() {
        SceneManager.inyectarEscena(contenedorSesion, "SignUp.fxml");
    }

    @FXML
    private void iniciarSesion() {
        String email = textoCorreoLogin.getText();
        String pass = textoContraseniaLogin.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            textoSesion.setText("Rellena los campos");
            return;
        }
        //Metodo FindByEmail
        Usuario userExistente = UsuarioDAO.findByEmail(email);

        if (userExistente == null) {
            textoSesion.setText("El correo no tiene cuenta asignada");
        } else {
            Usuario userLogueado = UsuarioDAO.login(email, pass);
            if (userLogueado == null) {
                textoSesion.setText("Contraseña incorrecta");
            } else {
                Sesion.setUsuarioActual(userLogueado);
                Scene escenaActual = textoSesion.getScene();
                textoSesion.setText("Bienvenido, " + userLogueado.getNombreUsuario());
                SceneManager.cambiarEscena(escenaActual,"org/example/proyectofinalprogramacion1dam/view/TiendaPrincipal.fxml");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String mensajeDeBienvenida = Sesion.getMensajeInfo();
        if (!mensajeDeBienvenida.isEmpty()) {
            textoSesion.setText(mensajeDeBienvenida);
        }
    }
}
