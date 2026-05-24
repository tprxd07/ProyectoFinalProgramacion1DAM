package org.example.proyectofinalprogramacion1dam.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.UsuarioDAO;
import org.example.proyectofinalprogramacion1dam.utils.SceneManager;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;
import org.example.proyectofinalprogramacion1dam.utils.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane contenedorSesion;
    @FXML
    private Text textoSesion;
    @FXML
    private Button botonConfirmar;
    private Object controladorActivo;
    private boolean modoLogin = true;

    @FXML
    private void mostrarLogin() {
        modoLogin=true;
        controladorActivo=SceneManager.inyectarEscena(contenedorSesion, "LoginInfo.fxml");
        botonConfirmar.setText("Confirmar");
    }

    @FXML
    private void mostrarSignUp() {
        modoLogin=false;
        controladorActivo=SceneManager.inyectarEscena(contenedorSesion, "SignUp.fxml");
        botonConfirmar.setText("Crear cuenta");
    }

    @FXML
    private void iniciarSesion() {
        LoginInfoController datos = (LoginInfoController) controladorActivo;

        String email = datos.getEmail();
        String pass = datos.getPassword();

        if (email.isEmpty() || pass.isEmpty()) {
            textoSesion.setText("Rellena los campos");
            return;
        }
        //Metodo FindByEmail
        Usuario userExistente = UsuarioDAO.findByEmail(email);

        if (userExistente == null) {
            textoSesion.setText("El correo no tiene cuenta asignada");
        } else {
            Usuario userLogueado = UsuarioDAO.addUsuario(email, pass);
            if (userLogueado == null) {
                textoSesion.setText("Contraseña incorrecta");
            } else {
                Sesion.setUsuarioActual(userLogueado);
                Scene escenaActual = textoSesion.getScene();
                Stage ventana=(Stage) escenaActual.getWindow();
                ventana.setResizable(true);
                ventana.setMinWidth(600);
                ventana.setMinHeight(400);
                ventana.setWidth(600);
                ventana.setHeight(400);
                textoSesion.setText("Bienvenido, " + userLogueado.getNombre());
                SceneManager.cambiarEscena(escenaActual, "TiendaPrincipal.fxml");
            }
        }
    }

    private void registrarUsuario() {
        SignUpController datos = (SignUpController) controladorActivo;

        String user = datos.getUsuario();
        String email = datos.getEmail();
        String pass = datos.getPassword();
        String confirm = datos.getConfirmarPassword();

        if (user.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            textoSesion.setText("Rellena todos los campos");
            return;
        }else if (!pass.equals(confirm)) {
            textoSesion.setText("Las contraseñas no coinciden");
            return;
        }else if (UsuarioDAO.findByEmail(email) != null) {
            textoSesion.setText("Este correo ya tiene una cuenta asignada");
            return;
        }else if (UsuarioDAO.findByUsername(user) != null) {
            textoSesion.setText("Ya existe este nombre de usuario");
        }
    }

    @FXML
    private void soniditoGracioso(){
        Sesion.setUsuarioActual(UsuarioDAO.findById(5));
        Scene escenaActual = textoSesion.getScene();
        Stage ventana=(Stage) escenaActual.getWindow();
        ventana.setResizable(true);
        ventana.setMinWidth(600);
        ventana.setMinHeight(400);
        ventana.setWidth(600);
        ventana.setHeight(400);
        SceneManager.cambiarEscena(escenaActual, "TiendaPrincipal.fxml");
    }

    @FXML
    private void confirmar(){
        if (modoLogin==true){
            iniciarSesion();
        }else{
            registrarUsuario();
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
