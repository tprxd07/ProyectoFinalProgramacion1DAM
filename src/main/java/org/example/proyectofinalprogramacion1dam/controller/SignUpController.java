package org.example.proyectofinalprogramacion1dam.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
//Esta clase tiene los datos paara registrar un usuario
public class SignUpController {
    @FXML
    private TextField textoUsuario;
    @FXML
    private TextField textoEmail;
    @FXML
    private TextField textoPassword;
    @FXML
    private TextField textoConfirmarPassword;

    public String getEmail() { return textoEmail.getText(); }
    public String getUsuario() { return textoUsuario.getText(); }
    public String getPassword() { return textoPassword.getText(); }
    public String getConfirmarPassword() { return textoConfirmarPassword.getText(); }

}
