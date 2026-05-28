package org.example.proyectofinalprogramacion1dam.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;
import org.example.proyectofinalprogramacion1dam.model.Categoria;
import org.example.proyectofinalprogramacion1dam.model.Desarrollador;
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.AplicacionDAO;
import org.example.proyectofinalprogramacion1dam.modelDAO.DesarrolladorDAO;
import org.example.proyectofinalprogramacion1dam.modelDAO.UtilidadDAO;
import org.example.proyectofinalprogramacion1dam.modelDAO.VideojuegoDAO;
import org.example.proyectofinalprogramacion1dam.utils.SceneManager;
import org.example.proyectofinalprogramacion1dam.utils.Sesion;
import org.example.proyectofinalprogramacion1dam.utils.Util;

import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

public class TiendaPrincipalController implements Initializable {
    @FXML
    private Button botonAjustes;
    @FXML
    private ImageView imagenEngranaje;

    @FXML
    private Button botonApps;

    @FXML
    private Button botonInicio;

    @FXML
    private Button botonJuegos;

    @FXML
    private Button botonModificar;

    @FXML
    private TextField busqueda;

    @FXML
    private RadioMenuItem rbAZ, rbZA;

    @FXML
    private VBox panelAjustes;

    @FXML
    private VBox filasMain;
    @FXML
    private Button saldoAjustes;
    @FXML
    private ScrollPane tiendaPrincipal;

    private boolean ajustesAbierto = false;
    private List<Button> botonesNav;
    private String vistaActual;
    private static TiendaPrincipalController instancia;

    public static TiendaPrincipalController getInstance() {
        return instancia;
    }

    /**
     * Devuelve el contenedor ScrollPane central de la tienda para poder cambiar su contenido
     * @return El ScrollPane principal
     */
    public ScrollPane getTiendaPrincipal() {
        return this.tiendaPrincipal;
    }

    //Actualiza el css del menu lateral segun el que se haya elegido
    private void actualizarEstadoMenu(Button botonPulsado) {
        for (Button b : botonesNav) {
            b.getStyleClass().remove("nav-button-active");
            b.setViewOrder(0.0);
        }
        botonPulsado.getStyleClass().add("nav-button-active");
        botonPulsado.setViewOrder(-1.0);
    }

    //Cambia el menú elegido para filtrar las apps
    @FXML
    private void menuElegido(ActionEvent event) {
        Button botonPulsado = (Button) event.getSource();
        actualizarEstadoMenu(botonPulsado);
        switch (botonPulsado.getId()) {
            case "botonJuegos":
                vistaActual = "Juegos";
                break;

            case "botonApps":
                vistaActual = "Apps";
                break;

            case "botonCrear":
                vistaActual = "Crear";
                break;
            default:
                vistaActual = "Inicio";
                break;
        }
        generarTienda();
    }

    //Abre y cierra el menu de ajustes de forma animada
    public void menuAjustes() {
        ajustesAbierto=!ajustesAbierto;
        if (ajustesAbierto){
            Util.animarAncho(panelAjustes, 150, 300);
            Util.girar(imagenEngranaje, 0, 300);
        }else{
            Util.animarAncho(panelAjustes, 0, 300);
            Util.girar(imagenEngranaje, 180, 300);
        }
        actualizarSaldo();
    }

    //Genera la tienda segun los datos de las tarjetas y su categoria
    public void generarTienda() {
        //Se limpia el contenedor para evitar duplicados
        filasMain.getChildren().clear();
        filasMain.setSpacing(30);

        List<Aplicacion> listaApps;
        if (vistaActual.equals("Juegos")) {
            listaApps = VideojuegoDAO.findAll().stream().map(v -> (Aplicacion) v).toList();
        } else if (vistaActual.equals("Apps")) {
            listaApps = UtilidadDAO.findAll().stream().map(u -> (Aplicacion) u).toList();
        } else {
            listaApps = AplicacionDAO.findAll();
        }

        List<Aplicacion> listaFinal = obtenerAppsFiltradas(listaApps);

        //Capturamos el texto de busqueda para saber si el usuario está filtrando
        String textoBusqueda = busqueda.getText().trim();

        //Las secciones se crean por cada nombre de categoria
        for (Categoria cat : Categoria.values()) {
            if (vistaActual.equals("Juegos")) {
                if (cat != Categoria.MMO && cat != Categoria.Shooter &&
                        cat != Categoria.Indie && cat != Categoria.Aventura) continue;
            }
            else if (vistaActual.equals("Apps")) {
                if (cat != Categoria.Salud && cat != Categoria.Finanzas &&
                        cat != Categoria.Ocio) continue;
            }

            //Filtramos las apps que pertenecen a esta categoria especifica
            List<Aplicacion> filtradas = listaFinal.stream()
                    .filter(a -> a.getCategoria() == cat)
                    .toList();

            //Si el usuario está buscando y esta categoría no teine app con este nombre, no se crea la fila
            if (filtradas.isEmpty() && !textoBusqueda.isEmpty()) {
                continue;
            }

            VBox seccionCategoria = new VBox(10);

            Label lbTitulo = new Label(cat.toString());
            lbTitulo.getStyleClass().add("titulo");
            lbTitulo.setPadding(new Insets(0, 0, 0, 10));
            seccionCategoria.getChildren().add(lbTitulo);

            //Si está vacía pero no estamos buscando, se crea la fila y se muestra ek mensaje
            if (filtradas.isEmpty()) {
                Label noHayApps = new Label("Aun no hay aplicaciones disponibles en esta categoría.");
                noHayApps.getStyleClass().add("texto-cursivo");
                noHayApps.setPadding(new Insets(10, 0, 10, 25));
                seccionCategoria.getChildren().add(noHayApps);
            } else {
                HBox hboxCarrusel = new HBox(20);
                hboxCarrusel.setPadding(new Insets(10));

                for (Aplicacion app : filtradas) {
                    Node tarjeta = SceneManager.cargarTarjeta(app);
                    if (tarjeta != null) {
                        tarjeta.setOnMouseClicked(e -> {
                            Sesion.setAppSeleccionada(app);
                            SceneManager.inyectarEscena(tiendaPrincipal, "DetalleApp.fxml");
                        });
                        hboxCarrusel.getChildren().add(tarjeta);
                    }
                }

                ScrollPane scrollHorizontal = new ScrollPane(hboxCarrusel);
                scrollHorizontal.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                scrollHorizontal.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollHorizontal.setPannable(true);
                scrollHorizontal.setFitToHeight(true);
                scrollHorizontal.setMinHeight(235);
                scrollHorizontal.setPrefHeight(235);
                seccionCategoria.getChildren().add(scrollHorizontal);
            }
            filasMain.getChildren().add(seccionCategoria);
        }
        if (filasMain.getChildren().isEmpty() && !textoBusqueda.isEmpty()) {
            VBox panelNoResultados = new VBox(15);
            panelNoResultados.setPadding(new Insets(50, 0, 0, 20));
            panelNoResultados.setAlignment(javafx.geometry.Pos.CENTER);

            Label aviso = new Label("No se han encontrado resultados para: \"" + textoBusqueda + "\"");
            aviso.getStyleClass().add("titulo");

            panelNoResultados.getChildren().addAll(aviso);
            filasMain.getChildren().add(panelNoResultados);
        }
    }

    /**
     * Filtra las apps por nombre del desarrollador o aplicacion
     * @param listaBase Lista general de todas las aplicaciones
     * @return Lista filtrada
     */
    private List<Aplicacion> obtenerAppsFiltradas(List<Aplicacion> listaBase) {
        String textoBusqueda = busqueda.getText().trim().toLowerCase();
        if (textoBusqueda.isEmpty()) {
            return aplicarOrden(listaBase.stream()).toList();
        }

        //Aplicamos el doble filtro de aplicaciones y desarrolladores
        Stream<Aplicacion> streamFiltrado = listaBase.stream()
                .filter(app -> {
                    //El nombre de la app contiene el texto buscado
                    boolean coincideNombreApp = app.getNombre().toLowerCase().contains(textoBusqueda);

                    //Si el nombre de su desarrollador contiene el texto buscado (Usa id desarrollador para obtener el nombre)
                    boolean coincideDesarrollador = false;
                    Desarrollador desarrollador = DesarrolladorDAO.findById(app.getIdDesarrollador());
                    if (desarrollador != null) {
                        coincideDesarrollador = desarrollador.getNombre().toLowerCase().contains(textoBusqueda);
                    }
                    return coincideNombreApp || coincideDesarrollador;
                });

        //Encadena los rb para ordenar
        return aplicarOrden(streamFiltrado).toList();
    }

    /**
     * Ordena por orden alfabetico
     * @param stream La lista de aplicaciones que se va a ordenar
     * @return La lista de aplicaciones ordenada según el RadioButton seleccionado
     */
    private Stream<Aplicacion> aplicarOrden(Stream<Aplicacion> stream) {
        if (rbAZ != null && rbAZ.isSelected()) {
            return stream.sorted(java.util.Comparator.comparing(a -> a.getNombre().toLowerCase()));
        } else if (rbZA != null && rbZA.isSelected()) {
            return stream.sorted((a1, a2) -> a2.getNombre().compareToIgnoreCase(a1.getNombre()));
        }
        return stream;
    }

    public void actualizarSaldo(){
        Usuario user = Sesion.getUsuarioActual()    ;
        double saldo = user.getSaldo();
        saldoAjustes.setText("Monedero: " + String.format("%.2f €", saldo));
    }

    @FXML
    public void abrirVentanaSaldo(){
        SceneManager.abrirVentana("Saldo.fxml", "Recargar monedero", true);
    }
    @FXML
    public void abrirModificarContenido() {
        SceneManager.abrirVentana("GestionarContenido.fxml", "Panel de Administración", true);
    }
    @FXML
    public void abrirPerfil(){
        SceneManager.abrirVentana("Perfil.fxml", "Perfil", false);
    }
    @FXML
    public void abrirHistorialCompras(){
        SceneManager.abrirVentana("HistorialCompras.fxml", "Historial de compras", true);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instancia = this;
        botonesNav = Arrays.asList(botonApps, botonJuegos, botonInicio, botonModificar);
        this.vistaActual="Inicio";
        actualizarEstadoMenu(botonInicio);
        panelAjustes.setMouseTransparent(false);
        panelAjustes.toFront();
        botonAjustes.setMouseTransparent(false);
        botonAjustes.toFront();
        generarTienda();
        menuAjustes();
        menuAjustes();
        //Para que el buscador funcione letra por letra
        busqueda.textProperty().addListener((observable, oldValue, newValue) -> generarTienda());
    }
}