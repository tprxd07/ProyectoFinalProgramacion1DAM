package org.example.proyectofinalprogramacion1dam.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.proyectofinalprogramacion1dam.model.*;
import org.example.proyectofinalprogramacion1dam.modelDAO.*;
import org.example.proyectofinalprogramacion1dam.utils.SceneManager;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;
import org.example.proyectofinalprogramacion1dam.utils.Util;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controlador del .fxml de detalles, al que se accese al pulsar en una aplicacion
 */
public class DetalleAppController implements Initializable {

    @FXML private ImageView portada;
    @FXML private Label nombre;
    @FXML private Label desarrollador;
    @FXML private Label descargas;
    @FXML private Label descripcion;
    @FXML private Button botonAdquirir;

    @FXML private ToggleButton star1;
    @FXML private ToggleButton star2;
    @FXML private ToggleButton star3;
    @FXML private ToggleButton star4;
    @FXML private ToggleButton star5;

    @FXML private VBox nuevaResenia;
    @FXML private Label tituloResenia;
    @FXML private TextArea comentario;
    @FXML private Button confirmarResenia;

    @FXML private ComboBox<String> cbFiltroResenias;
    @FXML private VBox contenedorResenia;

    private List<ToggleButton> puntuacionEstrellas;
    private int puntuacionSeleccionada = 0;
    private Aplicacion appActual;
    private Resenia reseniaPropia = null;
    private boolean modoEdicion = false;
    private final Usuario usuario = Sesion.getUsuarioActual();

    /**
     * Carga toda la informacion de una app en el .fxml, recogiendo lso datos de la BBDD
     */
    private void cargarInfoApp() {
        nombre.setText(appActual.getNombre());
        descargas.setText("Descargas: " + appActual.getDescargas());
        desarrollador.setText(DesarrolladorDAO.findById(appActual.getIdDesarrollador()).getNombre());

        int idUsuarioLogeado = Sesion.getUsuarioActual().getId();

        if (BibliotecaDAO.usuarioTieneApp(idUsuarioLogeado, appActual.getId())) {
            botonAdquirir.setText("Adquirido");
            botonAdquirir.setDisable(true);
        } else if (appActual.getPrecio() == 0) {
            botonAdquirir.setText("Gratis");
            botonAdquirir.setDisable(false);
        } else {
            botonAdquirir.setText(String.format("%.2f €", appActual.getPrecio()));
            botonAdquirir.setDisable(false);
        }

        String descCompleta = appActual.getDescripcion();
        Videojuego vj = VideojuegoDAO.findById(appActual.getId());

        if (vj != null) {
            if (vj.isMultijugador()) {
                descCompleta = "Multijugador\n" + descCompleta;
            } else {
                descCompleta = "Un jugador\n" + descCompleta;
            }
        }

        descripcion.setText(descCompleta);
        portada.setImage(Util.cargarImagen(appActual.getImagen()));
    }

    /**
     * Gestiona las compras y descargas al pulsar el botón con alerta de confirmación
     */
    @FXML
    private void accionarBotonPrincipal() {
        if (botonAdquirir.isDisable()) return;
        double precio = appActual.getPrecio();
        Alert alertaConfirmar = getAlert(precio);

        ButtonType botonSi = new ButtonType("Sí", ButtonBar.ButtonData.YES);
        ButtonType botonNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        alertaConfirmar.getButtonTypes().setAll(botonSi, botonNo);

        java.util.Optional<ButtonType> resultado = alertaConfirmar.showAndWait();

        if (resultado.isEmpty() || resultado.get() == botonNo) {
            return;
        }

        //Procesa la adquisición
        if (precio == 0) {
            //Caso aplicación gratuita
            boolean exito = BibliotecaDAO.adquirirApp(usuario, appActual);
            if (exito) {
                Util.reproducirSonido("descarga.mp3");
                cambiarAAdquirido();
                appActual.setDescargas(appActual.getDescargas() + 1);
                descargas.setText("Descargas: " + (appActual.getDescargas()));

                if (TiendaPrincipalController.getInstance() != null) {
                    TiendaPrincipalController.getInstance().generarTienda();
                }
            }
        } else {
            //Caso aplicación de pago
            if (usuario.getSaldo() >= precio) {

                usuario.setSaldo(usuario.getSaldo() - precio);
                UsuarioDAO.updateUsuario(usuario);
                Sesion.setUsuarioActual(usuario);

                boolean exitoAdquisicion = BibliotecaDAO.adquirirApp(usuario, appActual);

                if (exitoAdquisicion) {
                    //Actualiza el boton y las descargas
                    cambiarAAdquirido();
                    appActual.setDescargas(appActual.getDescargas() + 1);
                    descargas.setText("Descargas: " + (appActual.getDescargas()));

                    //
                    if (TiendaPrincipalController.getInstance() != null) {
                        try {
                            TiendaPrincipalController.getInstance().actualizarSaldo();
                        } catch (Exception e) {
                            System.out.println("No se pudo actualizar el saldo visual");
                        }
                        TiendaPrincipalController.getInstance().generarTienda();
                    }

                    Util.reproducirSonido("descarga.mp3");
                    comprobarYRenderizarResenias();
                } else {
                    System.err.println("Error: No se pudo procesar la transacción en la biblioteca.");
                }

            } else {
                Alert alertaError = new Alert(Alert.AlertType.ERROR);
                alertaError.setTitle("Saldo Insuficiente");
                alertaError.setHeaderText(null);
                alertaError.setContentText("No dispones de saldo suficiente para adquirir este producto.\nPor favor, recarga tu monedero.");
                alertaError.showAndWait();
            }
        }
    }

    /**
     * Crea una alerta al comprar una aplicacion
     * Si es gratis, cambia el mensaje
     */
    private Alert getAlert(double precio) {
        Alert alertaConfirmar = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmar.setTitle("Confirmar adquisición");
        alertaConfirmar.setHeaderText(null);

        if (precio == 0) {
            alertaConfirmar.setContentText("¿Estás seguro de que quieres descargar gratis \"" + appActual.getNombre() + "\"?");
        } else {
            alertaConfirmar.setContentText(String.format("¿Estás seguro de que quieres comprar \"%s\" por %.2f €?", appActual.getNombre(), precio));
        }
        return alertaConfirmar;
    }

    //Cambia el texto de precio/gratis a adquirido
    private void cambiarAAdquirido() {
        botonAdquirir.setText("Adquirido");
        botonAdquirir.setDisable(true);
    }

    //valida la reseña para poder enviarla
    private void validarFormulario() {
        boolean textoValido = !comentario.getText().trim().isEmpty();
        boolean estrellasValidas = puntuacionSeleccionada > 0;
        confirmarResenia.setDisable(!(textoValido && estrellasValidas));
    }

    /**
     * Actualiza la puntuacion de las 5 estrellas segun a las que le des click, dejando unas llenas y otras vacias
     * @param puntuacion Puntuacion del 1 al 5
     */
    private void actualizarEstrellasVisuales(int puntuacion) {
        for (int i = 0; i < puntuacionEstrellas.size(); i++) {
            ToggleButton estrella = puntuacionEstrellas.get(i);
            if (i < puntuacion) {
                estrella.getStyleClass().add("texto-estrellas-puntuacion");
                estrella.setSelected(true);
                estrella.setText("★");
            } else {
                estrella.setSelected(false);
                estrella.setText("☆");
            }
        }
    }

    /**
     * Carga todas las reseñas que tenga la aplicacion elegida. SI esta la del usuario, en vezd e poder crear una reseña,
     * el usuario podrá modificar su reseña ya creada.
     * Además, se pueden ordenar las reseñas por tiempo de envio o puntuacion
     */
    private void comprobarYRenderizarResenias() {
        contenedorResenia.getChildren().clear();
        List<Resenia> opinionesApp = ReseniaDAO.findByApp(appActual.getId());

        reseniaPropia = opinionesApp.stream()
                .filter(r -> r.getIdUsuario() == usuario.getId())
                .findFirst()
                .orElse(null);

        if (reseniaPropia != null && !modoEdicion) {
            nuevaResenia.setVisible(false);
            nuevaResenia.setManaged(false);

            Node tarjetaPropia = SceneManager.cargarResenia(reseniaPropia, true, this);
            if (tarjetaPropia != null) {
                contenedorResenia.getChildren().add(tarjetaPropia);
            }
        } else {
            nuevaResenia.setVisible(true);
            nuevaResenia.setManaged(true);

            if (modoEdicion && reseniaPropia != null) {
                comentario.setText(reseniaPropia.getComentario());
                puntuacionSeleccionada = reseniaPropia.getPuntuacion();
                actualizarEstrellasVisuales(puntuacionSeleccionada);
                confirmarResenia.setText("Guardar cambios");
                tituloResenia.setText("Editando tu reseña");
            } else {
                puntuacionSeleccionada = 5;
                actualizarEstrellasVisuales(5);
                confirmarResenia.setText("Confirmar");
                tituloResenia.setText("Valora esta aplicación");
            }
        }

        List<Resenia> restoOpiniones = opinionesApp.stream()
                .filter(r -> reseniaPropia == null || r.getId() != reseniaPropia.getId())
                .collect(Collectors.toList());

        String filtro = cbFiltroResenias != null ? cbFiltroResenias.getValue() : "Más recientes";

        switch (filtro) {
            case "Mejor puntuación" ->
                    restoOpiniones.sort((r1, r2) -> Integer.compare(r2.getPuntuacion(), r1.getPuntuacion()));
            case "Menor puntuación" ->
                    restoOpiniones.sort((r1, r2) -> Integer.compare(r1.getPuntuacion(), r2.getPuntuacion()));
            case "Más antiguas" -> restoOpiniones.sort((r1, r2) -> Integer.compare(r1.getId(), r2.getId()));
            case null, default -> restoOpiniones.sort((r1, r2) -> Integer.compare(r2.getId(), r1.getId()));
        }

        for (Resenia r : restoOpiniones) {
            Node tarjetaResto = SceneManager.cargarResenia(r, false, null);
            if (tarjetaResto != null) {
                contenedorResenia.getChildren().add(tarjetaResto);
            }
        }
    }

    @FXML
    private void filtrarResenias() {
        comprobarYRenderizarResenias();
    }

    //Abre la posibilidad a editar una reseña ya creada
    public void activarModoEdicion() {
        this.modoEdicion = true;
        comprobarYRenderizarResenias();
    }

    /**
     * Envia el comentario y puntuación añadidos a la BBDD para que se pueda mostrar en la app
     */
    @FXML
    private void enviarFormularioResenia() {
        String textoComentario = this.comentario.getText().trim();
        int puntos = puntuacionSeleccionada;

        if (modoEdicion && reseniaPropia != null) {
            reseniaPropia.setComentario(textoComentario);
            reseniaPropia.setPuntuacion(puntos);
            ReseniaDAO.updateResenia(reseniaPropia);
            modoEdicion = false;
        } else {
            Resenia nueva = new Resenia();
            nueva.setIdApp(appActual.getId());
            nueva.setIdUsuario(usuario.getId());
            nueva.setPuntuacion(puntos);
            nueva.setComentario(textoComentario);
            nueva.setFechaResenia(LocalDateTime.now());
            ReseniaDAO.addResenia(nueva);
        }
        this.comentario.clear();
        comprobarYRenderizarResenias();
    }

    @FXML
    public void borrarResenia(){
        ReseniaDAO.deleteResenia(reseniaPropia.getId());
        comprobarYRenderizarResenias();
    }

    //Vuelve a la tienda principal y la recarga
    @FXML
    private void volverAtras() {
        SceneManager.cambiarEscena(portada.getScene(), "TiendaPrincipal.fxml");
        if (TiendaPrincipalController.getInstance() != null) {
            TiendaPrincipalController.getInstance().generarTienda();
        }
    }

    //Carga los CbBox de las estrellas y el filtro de reseñas para que muestren uno por defecto
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.appActual = Sesion.getAppSeleccionada();
        if (appActual == null) return;
        if (cbFiltroResenias != null) {
            cbFiltroResenias.setItems(FXCollections.observableArrayList(
                    "Más recientes", "Más antiguas", "Mejor puntuación", "Menor puntuación"
            ));
            cbFiltroResenias.setValue("Más recientes");
        }
        puntuacionEstrellas = Arrays.asList(star1, star2, star3, star4, star5);
        confirmarResenia.setDisable(true);
        comentario.textProperty().addListener((observable, oldValue, newValue) -> validarFormulario());
        for (int i = 0; i < puntuacionEstrellas.size(); i++) {
            final int indiceActual = i + 1;
            ToggleButton botonEstrella = puntuacionEstrellas.get(i);
            botonEstrella.setOnAction(event -> {
                puntuacionSeleccionada = indiceActual;
                actualizarEstrellasVisuales(puntuacionSeleccionada);
                validarFormulario();
            });
        }

        cargarInfoApp();
        comprobarYRenderizarResenias();
    }
}