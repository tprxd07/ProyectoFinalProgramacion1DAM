package org.example.proyectofinalprogramacion1dam.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;
import org.example.proyectofinalprogramacion1dam.model.Biblioteca;
import org.example.proyectofinalprogramacion1dam.model.HistorialCompra;
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.AplicacionDAO;
import org.example.proyectofinalprogramacion1dam.modelDAO.BibliotecaDAO;
import org.example.proyectofinalprogramacion1dam.modelDAO.CompraDAO;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HistorialController implements Initializable {

    @FXML private ComboBox<String> cbOrden;
    @FXML private VBox listaCompras;

    private final List<HistorialCompra> comprasUsuario = new ArrayList<>();

    private final DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inicia las opciones del ComboBox
        cbOrden.setItems(FXCollections.observableArrayList("Más recientes", "Más antiguas"));
        cbOrden.setValue("Más recientes");

        //Obtiene el usuario activo de la sesión
        Usuario usuarioActual = Sesion.getUsuarioActual();

        if (usuarioActual != null) {
            List<HistorialCompra> registros = CompraDAO.findByUsuario(usuarioActual.getId());
            comprasUsuario.addAll(registros);
            renderizarHistorial();
        }
    }

    //Crea y carga las compras hechas por este perfil
    private void renderizarHistorial() {
        listaCompras.getChildren().clear();

        if (comprasUsuario.isEmpty()) {
            Label msjVacio = new Label("No has realizado ninguna compra todavía.");
            msjVacio.getStyleClass().add("texto-cursivo");
            listaCompras.getChildren().add(msjVacio);
            return;
        }
        if (cbOrden.getValue().equals("Más recientes")) {
            comprasUsuario.sort((c1, c2) -> {
                if (c1.getFechaCompra() == null || c2.getFechaCompra() == null) return 0;
                return c2.getFechaCompra().compareTo(c1.getFechaCompra());
            });
        } else {
            comprasUsuario.sort((c1, c2) -> {
                if (c1.getFechaCompra() == null || c2.getFechaCompra() == null) return 0;
                return c1.getFechaCompra().compareTo(c2.getFechaCompra());
            });
        }

        //Construye de forma dinámica las filas visuales
        for (HistorialCompra registro : comprasUsuario) {
            Aplicacion app = AplicacionDAO.findById(registro.getIdApp());

            if (app != null) {
                HBox fila = new HBox();
                fila.getStyleClass().add("fila-compra");
                fila.setSpacing(15);

                Label lbNombre = new Label(app.getNombre());
                lbNombre.getStyleClass().add("texto");
                lbNombre.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(lbNombre, Priority.ALWAYS);

                Label lbPrecio = new Label(String.format("%.2f €", registro.getPrecioPagado()));
                lbPrecio.getStyleClass().add("texto-precio-compra");
                lbPrecio.setPrefWidth(90);

                String fechaTexto = (registro.getFechaCompra() != null) ? registro.getFechaCompra().format(formateador) : "Fecha desconocida";
                Label lbFecha = new Label(fechaTexto);
                lbFecha.getStyleClass().add("texto-fecha-compra");
                lbFecha.setPrefWidth(140);

                fila.getChildren().addAll(lbNombre, lbPrecio, lbFecha);
                listaCompras.getChildren().add(fila);
            }
        }
    }

    //Cambia el orden segun la opcion del CbBox
    @FXML
    private void cambiarOrden() {
        renderizarHistorial();
    }
}