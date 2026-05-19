package org.example.proyectofinalprogramacion1dam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.UsuarioDAO;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;

import java.net.URL;
import java.util.ResourceBundle;

public class SaldoController implements Initializable {
    @FXML private TextField saldoPersonalizado;
    @FXML private RadioButton rb5;
    @FXML private RadioButton rb10;
    @FXML private RadioButton rb20;
    @FXML private RadioButton rbPersonalizado;
    @FXML private Button confirmar;
    @FXML private Label seguro;
    @FXML private HBox contenedorConfirmacion;

    @FXML
    private ToggleGroup grupoSaldo;
    private double cantidadPendiente = 0;

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
                double cantidad = Double.parseDouble(saldoPersonalizado.getText().trim());
                if (cantidad > 0) {
                    cantidadPendiente = cantidad;
                } else {
                    mostrarErrorEntrada();
                    return; //Corta la ejecución si el número es negativo o cero
                }
            } catch (NumberFormatException e) {
                mostrarErrorEntrada();
                return; //Corta la ejecución si mete letras
            }
        }
        mostrarConfirmacion(true);
    }

    private void mostrarErrorEntrada() {
        System.err.println("Entrada de saldo personalizada incorrecta.");
        saldoPersonalizado.setText("");
        saldoPersonalizado.setPromptText("Número inválido");
    }
    @FXML
    private void confirmarSi() { //
        if (cantidadPendiente > 0) {
            procesarRecarga(cantidadPendiente); //
        }
    }
    @FXML
    private void confirmarNo() { //
        cantidadPendiente = 0; //
        mostrarConfirmacion(false); //
    }

    private void mostrarConfirmacion(boolean Confirmacion) { //
        confirmar.setVisible(!Confirmacion); //
        confirmar.setManaged(!Confirmacion); //

        seguro.setVisible(Confirmacion); //
        seguro.setManaged(Confirmacion); //

        contenedorConfirmacion.setVisible(Confirmacion); //
        contenedorConfirmacion.setManaged(Confirmacion); //
    }

    private void procesarRecarga(double cantidad) {
        Usuario user = Sesion.getUsuarioActual();
        user.setSaldo(user.getSaldo() + cantidad);

        if (UsuarioDAO.updateUsuario(user)) {
            ((Stage) saldoPersonalizado.getScene().getWindow()).close();
            TiendaPrincipalController.getInstance().actualizarSaldo();
        }
    }

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
            mostrarConfirmacion(false);
        });
    }
}