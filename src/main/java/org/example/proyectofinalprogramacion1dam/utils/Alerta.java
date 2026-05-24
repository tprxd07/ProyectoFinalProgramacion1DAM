package org.example.proyectofinalprogramacion1dam.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import java.util.Optional;

public class Alerta {

    /**
     * Muestra una alerta informativa
     * @param titulo Título de la ventana emergente
     * @param encabezado Texto destacado superior
     * @param contenido Detalle o cuerpo del mensaje
     */
    public static void mostrarInformacion(String titulo, String encabezado, String contenido) {
        crearAlerta(AlertType.INFORMATION, titulo, encabezado, contenido);
    }

    /**
     * Muestra una alerta de error
     * @param titulo Título de la ventana emergente
     * @param encabezado Texto destacado superior
     * @param contenido Detalle o cuerpo del error
     */
    public static void mostrarError(String titulo, String encabezado, String contenido) {
        crearAlerta(AlertType.ERROR, titulo, encabezado, contenido);
    }

    /**
     * Muestra una alerta de confirmación con botones de Aceptar/Cancelar
     * @param titulo Título de la ventana emergente
     * @param encabezado Texto destacado superior
     * @param contenido Pregunta o acción que requiere confirmación
     * @return true si el usuario pulsa Aceptar, false en caso contrario
     */
    public static boolean mostrarConfirmacion(String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);

        //vincula el css
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(Alerta.class.getResource("/org/example/proyectofinalprogramacion1dam/css/estilos.css").toExternalForm());

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    /**
     * Metodo para contruir distintos tipos de alertas, y personalizar sus datos
     */
    private static void crearAlerta(AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);

        //vindula el css
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(Alerta.class.getResource("/org/example/proyectofinalprogramacion1dam/css/estilos.css").toExternalForm());

        alerta.showAndWait();
    }
}