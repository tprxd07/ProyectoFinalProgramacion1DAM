package org.example.proyectofinalprogramacion1dam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.UsuarioDAO;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;

public class SaldoController {
    @FXML private TextField saldoPersonalizado;

    @FXML
    private void añadirFijo(ActionEvent event) {
        Button btn = (Button) event.getSource();
        double cantidad = Double.parseDouble(btn.getText().replace("€", ""));
        procesarRecarga(cantidad);
    }

    @FXML
    private void añadirPersonalizado() {
        try {
            double cantidad = Double.parseDouble(saldoPersonalizado.getText());
            if (cantidad > 0) {
                procesarRecarga(cantidad);
            }
        } catch (NumberFormatException e) {
            System.err.println("No se permite el uso de letras");
            saldoPersonalizado.setText("Solo números");
        }
    }

    private void procesarRecarga(double cantidad) {
        Usuario user = Sesion.getUsuarioActual();
        user.setSaldo(user.getSaldo() + cantidad);

        if (UsuarioDAO.updateUsuario(user)) {
            ((Stage) saldoPersonalizado.getScene().getWindow()).close();
            TiendaPrincipalController.getInstance().actualizarSaldo();
        }
    }
}