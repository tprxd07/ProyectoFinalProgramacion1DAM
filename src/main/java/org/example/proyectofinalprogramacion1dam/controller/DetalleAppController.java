package org.example.proyectofinalprogramacion1dam.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.proyectofinalprogramacion1dam.model.*;
import org.example.proyectofinalprogramacion1dam.modelDAO.*; // Importa todos tus DAOs reales
import org.example.proyectofinalprogramacion1dam.utils.SceneManager;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;
import org.example.proyectofinalprogramacion1dam.utils.Util; //

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class DetalleAppController implements Initializable {

    @FXML private ImageView portada;
    @FXML private Label nombre;
    @FXML private Label desarrollador;
    @FXML private Label descargas;
    @FXML private Label descripcion;
    @FXML private Button botonAdquirir;

    //Grupo estrellas puntuacion
    @FXML private ToggleButton star1;
    @FXML private ToggleButton star2;
    @FXML private ToggleButton star3;
    @FXML private ToggleButton star4;
    @FXML private ToggleButton star5;

    //Apartado de crear reseña
    @FXML private VBox nuevaResenia;
    @FXML private TextArea comentario;
    @FXML private Button confirmarResenia;

    @FXML private VBox contenedorResenia;

    private List<ToggleButton> listaEstrellas;
    private int puntuacionSeleccionada = 0;
    private Aplicacion appActual;
    //Devuelve la reseña que el usuario tenga para esta app.
    //Se inicia null para que aparezca el apartado de crear y no el de modificar
    private Resenia reseniaPropia = null;
    private boolean modoEdicion = false;
    Usuario usuario = Sesion.getUsuarioActual();
    
    /**
     * Rellena los campos de informacion de la app
     */
    private void cargarInfoApp() {
        nombre.setText(appActual.getNombre());
        descargas.setText("Descargas: " + appActual.getDescargas());
        desarrollador.setText(DesarrolladorDAO.findById(appActual.getIdDesarrollador()).getNombre());

        //Gestion del boton para descargar
        int idUsuarioLogeado = Sesion.getUsuarioActual().getId();

        //Si el usuario ya la tiene en su biblioteca
        if (BibliotecaDAO.usuarioTieneApp(idUsuarioLogeado, appActual.getId())) {
            botonAdquirir.setText("Adquirido");
            botonAdquirir.setDisable(true);
        }
        //Si es una aplicación gratuita
        else if (appActual.getPrecio() == 0) {
            botonAdquirir.setText("Gratis");
            botonAdquirir.setDisable(false);
        }
        //Si es una aplicación de pago
        else {
            botonAdquirir.setText(String.format("%.2f €", appActual.getPrecio()));
            botonAdquirir.setDisable(false);
        }

        //Si es videojuego, añade el dato de multijugador en la categoria
        String descCompleta = appActual.getDescripcion();
        Videojuego vj = VideojuegoDAO.findById(appActual.getId());

        //Solo si se encuentra en la tabla de la base de datos se añade
        if (vj != null) {
            if (vj.isMultijugador()) {
                descCompleta = descCompleta + "\n\nMultijugador";
            } else {
                descCompleta = descCompleta + "\n\nUn jugador";
            }
        }

        descripcion.setText(descCompleta);
        descripcion.setText(descCompleta);
        portada.setImage(Util.cargarImagen(appActual.getImagen()));
    }

    /**
     * Gestiona las compras y descargas al pulsar el boton
     */
    @FXML
    private void accionarBotonPrincipal() {
        if (botonAdquirir.isDisable()) return;
        double precio = appActual.getPrecio();

        //Si es gratis
        if (precio == 0) {
            boolean exito = BibliotecaDAO.adquirirApp(usuario, appActual);
            if (exito) {
                Util.reproducirSonido("descarga.mp3"); //TODO sonido descarga
                cambiarAAdquirido();
                appActual.setDescargas(appActual.getDescargas() + 1);
                descargas.setText("Descargas: " + (appActual.getDescargas()));
            } else {
                System.err.println("Error al descargar");
            }
        //Si tiene precio
        } else {
            if (usuario.getSaldo() >= precio) {
                usuario.setSaldo(usuario.getSaldo() - precio);
                //Cambia el saldo del usuario en la BBDD
                UsuarioDAO.updateUsuario(usuario);
                //Registrar en el historial de compras
                HistorialCompra hc = new HistorialCompra(appActual.getId(), usuario.getId(), precio);
                CompraDAO.registrarCompra(hc);
                //Añadir a la biblioteca del usuario
                BibliotecaDAO.adquirirApp(usuario, appActual);

                //Actualizar el saldo
                try {
                    TiendaPrincipalController.getInstance().actualizarSaldo();
                } catch (Exception e) {
                    System.out.println("No se pudo actualizar el saldo");
                }

                Util.reproducirSonido("descarga.mp3");
                cambiarAAdquirido();
                appActual.setDescargas(appActual.getDescargas() + 1);
                descargas.setText("Descargas: " + (appActual.getDescargas()));
            } else {
                // Mensaje controlado si no tiene suficiente saldo
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Saldo Insuficiente");
                alerta.setHeaderText(null);
                alerta.setContentText("No dispones de saldo suficiente para adquirir este producto.\nPor favor, recarga tu monedero.");
                alerta.showAndWait();
            }
        }
    }

    private void cambiarAAdquirido() {
        botonAdquirir.setText("Adquirido");
        botonAdquirir.setDisable(true);
    }

    private void validarFormulario() {
        boolean textoValido = !comentario.getText().trim().isEmpty();
        boolean estrellasValidas = puntuacionSeleccionada > 0;
        confirmarResenia.setDisable(!(textoValido && estrellasValidas));
    }

    private void actualizarEstrellasVisuales(int puntuacion) {
        for (int i = 0; i < listaEstrellas.size(); i++) {
            ToggleButton estrella = listaEstrellas.get(i);
            if (i < puntuacion) {
                estrella.setSelected(true);
                estrella.setText("★");
                estrella.setStyle("-fx-text-fill: textWhite");
            } else {
                estrella.setSelected(false);
                estrella.setText("☆");
                estrella.setStyle("-fx-text-fill: lightGrey");
            }
        }
    }

    private void comprobarYRenderizarResenias() {
        //limpiar contenedor de reseñas para evitar duplicados
        contenedorResenia.getChildren().clear();
        reseniaPropia = ReseniaDAO.findAll().stream()
                .filter(r -> r.getIdUsuario() == usuario.getId() && r.getIdApp() == appActual.getId())
                .findFirst()
                .orElse(null);

        if (reseniaPropia != null && !modoEdicion) {
            nuevaResenia.setVisible(false);
            nuevaResenia.setManaged(false);

            Node tarjetaPropia = SceneManager.cargarResenia(reseniaPropia, true, this);
            contenedorResenia.getChildren().add(tarjetaPropia);
        } else {
            nuevaResenia.setVisible(true);
            nuevaResenia.setManaged(true);

            if(modoEdicion && reseniaPropia != null) {
                comentario.setText(reseniaPropia.getComentario());
                puntuacionSeleccionada = reseniaPropia.getPuntuacion();
                actualizarEstrellasVisuales(puntuacionSeleccionada);
                confirmarResenia.setText("Guardar Cambios");
            } else {
                puntuacionSeleccionada = 5;
                actualizarEstrellasVisuales(5);
                confirmarResenia.setText("Confirmar");
            }
        }

        // Renderiza el resto de las opiniones de la app desde la Base de Datos
        List<Resenia> todasLasReseñas = ReseniaDAO.findAll().stream()
                .filter(r -> r.getIdApp() == appActual.getId())
                .toList();

        for (Resenia r : todasLasReseñas) {
            if (reseniaPropia != null && r.getId() == reseniaPropia.getId()) {
                continue; // Evita duplicar tu propia reseña en la zona general
            }
            Node tarjetaResto = SceneManager.cargarResenia(r, false, null);
            if (tarjetaResto != null) {
                contenedorResenia.getChildren().add(tarjetaResto);
            }
        }
    }

    public void activarModoEdicion() {
        this.modoEdicion = true;
        comprobarYRenderizarResenias();
    }

    @FXML
    private void enviarFormularioReseña() {
        String comentario = this.comentario.getText().trim();
        int puntos = puntuacionSeleccionada;

        if (modoEdicion && reseniaPropia != null) {
            reseniaPropia.setComentario(comentario);
            reseniaPropia.setPuntuacion(puntos);
            ReseniaDAO.updateResenia(reseniaPropia); // Llama a tu método real de actualización
            modoEdicion = false;
        } else {
            Resenia nueva = new Resenia();
            nueva.setIdApp(appActual.getId());
            nueva.setIdUsuario(Sesion.getUsuarioActual().getId()); //
            nueva.setPuntuacion(puntos);
            nueva.setComentario(comentario);
            ReseniaDAO.addResenia(nueva); // Llama a tu método real de inserción
        }

        this.comentario.clear();
        comprobarYRenderizarResenias();
    }

    @FXML
    private void volverAtras() {
        try {
            TiendaPrincipalController tienda = TiendaPrincipalController.getInstance();
            tienda.generarTienda();
        } catch (Exception e) {
            System.err.println("Error al regresar a la tienda principal.");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Obtener la app de tu sesión global
        this.appActual = Sesion.getAppSeleccionada();

        if (appActual == null) {
            System.err.println("Error: No hay ninguna aplicación seleccionada en la sesión.");
            return;
        }

        // 2. Inicializar componentes de las estrellas
        listaEstrellas = Arrays.asList(star1, star2, star3, star4, star5);
        confirmarResenia.setDisable(true);

        comentario.textProperty().addListener((observable, oldValue, newValue) -> validarFormulario());

        for (int i = 0; i < listaEstrellas.size(); i++) {
            final int indiceActual = i + 1;
            ToggleButton botonEstrella = listaEstrellas.get(i);
            botonEstrella.setOnAction(event -> {
                puntuacionSeleccionada = indiceActual;
                actualizarEstrellasVisuales(puntuacionSeleccionada);
                validarFormulario();
            });
        }

        // 3. Cargar la interfaz con tus DAOs reales
        cargarInfoApp();
        comprobarYRenderizarResenias();
    }
}