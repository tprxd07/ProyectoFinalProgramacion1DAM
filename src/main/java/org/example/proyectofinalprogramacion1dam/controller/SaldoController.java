package org.example.proyectofinalprogramacion1dam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.UsuarioDAO;
import org.example.proyectofinalprogramacion1dam.utils.Alerta;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;

import java.net.URL;
import java.util.ResourceBundle;
//esta clase recarga el saldo del usuario
public class SaldoController implements Initializable {
    @FXML private TextField saldoPersonalizado;
    @FXML private RadioButton rb5;
    @FXML private RadioButton rb10;
    @FXML private RadioButton rb20;
    @FXML private RadioButton rbPersonalizado;

    @FXML
    private ToggleGroup grupoSaldo;
    private double cantidadPendiente = 0;

    //Recarga el saldo del usuario segun la accion elegida, mostrando alerta para confirmar o
    //si el dato introducido (en caso de personalizado) es incorrecto
    @FXML
    private void accionarConfirmar() {
        Toggle seleccion = grupoSaldo.getSelectedToggle();

        if (seleccion == rb5) {
            cantidadPendiente = 5.0;
        } else if (seleccion == rb10) {
            cantidadPendiente = 10.0;
        } else if (seleccion == rb20) {
            cantidadPendiente = 20.0;
        } else if (seleccion == rbPersonalizado) {
            try {
                cantidadPendiente = Double.parseDouble(saldoPersonalizado.getText().trim());
                if (cantidadPendiente <= 0) {
                    Alerta.mostrarError("Inválido", "Cantidad incorrecta", "Por favor, introduce un importe mayor que 0€.");
                    return;
                }
            } catch (NumberFormatException e) {
                Alerta.mostrarError("Error de formato", "Número no válido", "Por favor, introduce un número decimal");
                return;
            }
        }

        if (cantidadPendiente > 0) {
            boolean seguro = Alerta.mostrarConfirmacion(
                    "Confirmar recarga",
                    "¿Deseas añadir saldo a tu monedero?",
                    "Se van a ingresar " + String.format("%.2f", cantidadPendiente) + "€ en tu cuenta."
            );

            if (seguro) {
                procesarRecarga(cantidadPendiente);
            } else {
                cantidadPendiente = 0;
            }
        }
    }

    //Cambia el saldo en la BBDd y se actualiza en la tienda principal
    private void procesarRecarga(double cantidad) {
        Usuario user = Sesion.getUsuarioActual();
        user.setSaldo(user.getSaldo() + cantidad);

        if (UsuarioDAO.updateUsuario(user)) {
            ((Stage) saldoPersonalizado.getScene().getWindow()).close();
            TiendaPrincipalController.getInstance().actualizarSaldo();
        }
    }

    //Pone por defecto 5€ como opcion selecionada
    @Override
    public void initialize(URL url, ResourceBundle rb){
        grupoSaldo.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == rbPersonalizado) {
                saldoPersonalizado.setDisable(false);
                saldoPersonalizado.requestFocus();
            } else {
                saldoPersonalizado.setDisable(true);
                saldoPersonalizado.clear();
            }
        });
    }
}