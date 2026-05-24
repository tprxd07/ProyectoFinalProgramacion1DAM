package org.example.proyectofinalprogramacion1dam.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.proyectofinalprogramacion1dam.utils.Util;

//Esta clase tiene los datos el usuario que va a iniciar sesion
public class LoginInfoController {
    @FXML
    private TextField textoCorreoLogin;
    @FXML
    private PasswordField textoContraseniaPrivado;
    @FXML
    private TextField textoContraseniaLogin;
    @FXML
    private ToggleButton botonOjo;
    @FXML private ImageView imagenOjo;

    private final Image ojoAbierto= Util.cargarImagen("view.png");
    private final Image ojoCerrado= Util.cargarImagen("hide.png");

    public String getEmail(){
        return textoCorreoLogin.getText();
    }
    public String getPassword() {
        if(botonOjo.isSelected()) {
            return textoContraseniaLogin.getText();
        }
        return textoContraseniaPrivado.getText();
    }

    //Cambia la visibilidad de la contraseña al pulsar el simbolo del ojo
    @FXML
    private void togglePassword(){
        if (botonOjo.isSelected()){
            textoContraseniaLogin.setText(textoContraseniaPrivado.getText());
            textoContraseniaLogin.setVisible(true);
            textoContraseniaPrivado.setVisible(false);
            imagenOjo.setImage(ojoAbierto);
        }else{
            textoContraseniaPrivado.setText(textoContraseniaLogin.getText());
            textoContraseniaPrivado.setVisible(true);
            textoContraseniaLogin.setVisible(false);
            imagenOjo.setImage(ojoCerrado);
        }
    }
}
