package org.example.proyectofinalprogramacion1dam.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.proyectofinalprogramacion1dam.model.Resenia;
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.UsuarioDAO;

public class ReseniaController {

    @FXML private Label labelUsuario;
    @FXML private Label puntuacion;
    @FXML private Label comentario;
    @FXML private Button botonEditar;

    private DetalleAppController padreController;

    /**
     * Llena los componentes de esta tarjeta con los datos de la fila de MySQL
     * @param resenia Objeto de tipo Resenia con los datos
     * @param esLaPropia Si es true, el botón "Editar" se vuelve visible
     * @param padre Instancia de DetalleAppController para poder activar el modo edición
     */
    public void setResenia(Resenia resenia, boolean esLaPropia, DetalleAppController padre) {
        this.padreController = padre;

        Usuario user = UsuarioDAO.findById(resenia.getIdUsuario());
        if (user != null) {
            labelUsuario.setText("@" + user.getNombre());
        } else {
            labelUsuario.setText("@Usuario_Desconocido");
        }

        String estrellasRellenas = "★".repeat(resenia.getPuntuacion());
        String estrellasVacias = "☆".repeat(5 - resenia.getPuntuacion());
        puntuacion.setText(estrellasRellenas + estrellasVacias);

        comentario.setText(resenia.getComentario());

        if (esLaPropia) {
            botonEditar.setVisible(true);
            botonEditar.setManaged(true);
        } else {
            botonEditar.setVisible(false);
            botonEditar.setManaged(false);
        }
    }

    /**
     * Acción vinculada al botón de editar dentro de la tarjeta
     */
    @FXML
    private void accionarEditar() {
        if (padreController != null) {
            padreController.activarModoEdicion();
        }
    }
}