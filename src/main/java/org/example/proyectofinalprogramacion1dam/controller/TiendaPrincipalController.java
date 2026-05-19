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
import org.example.proyectofinalprogramacion1dam.model.Usuario;
import org.example.proyectofinalprogramacion1dam.modelDAO.AplicacionDAO;
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
    private Button botonCrear;

    @FXML
    private TextField busqueda;

    @FXML
    private HBox menuArriba;
    @FXML
    private Button flechaBusqueda;

    private ContextMenu menuBusqueda;
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
    private void actualizarEstadoMenu(Button botonPulsado) {
        for (Button b : botonesNav) {
            b.getStyleClass().remove("nav-button-active");
            b.setViewOrder(0.0);
        }
        botonPulsado.getStyleClass().add("nav-button-active");
        botonPulsado.setViewOrder(-1.0);
    }

    @FXML
    private void menuElegido(ActionEvent event) {
        Button botonPulsado = (Button) event.getSource();
        actualizarEstadoMenu(botonPulsado);
        switch (botonPulsado.getId()) {
            case "botonInicio":
                vistaActual = "Inicio";
                break;

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

    public void menuAjustes() {
        ajustesAbierto=!ajustesAbierto;
        if (ajustesAbierto){
            Util.animarAncho(panelAjustes, 150, 300);
            Util.girar(imagenEngranaje, 0, 300);
            Util.animarAlto(menuArriba, panelAjustes.getHeight(), 300);
            System.out.println(panelAjustes.getHeight());
        }else{
            Util.animarAncho(panelAjustes, 0, 300);
            Util.girar(imagenEngranaje, 180, 300);
            Util.animarAlto(menuArriba, 100, 300);
        }
        actualizarSaldo();
    }

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

        List<Aplicacion> listaFinal = getAplicaciones(listaApps);

        //Las secciones se crean por cada nombre de categoria
        for (Categoria cat : Categoria.values()) {
            if (vistaActual.equals("Juegos")) {
                // Solo permitimos categorías de videojuegos
                if (cat != Categoria.MMO && cat != Categoria.Shooter &&
                        cat != Categoria.Indie && cat != Categoria.Aventura) continue;
            }
            else if (vistaActual.equals("Apps")) {
                // Solo permitimos categorías de utilidades
                if (cat != Categoria.Salud && cat != Categoria.Finanzas &&
                        cat != Categoria.Ocio) continue;
            }
            //Filtramos las apps que pertenecen a esta categoria especifica
            List<Aplicacion> filtradas = listaFinal.stream()
                    .filter(a -> a.getCategoria() == cat)
                    .toList();

            VBox seccionCategoria = new VBox(10);

            Label lbTitulo = new Label(cat.toString());
            lbTitulo.getStyleClass().add("titulo");
            lbTitulo.setPadding(new Insets(0, 0, 0, 10));
            seccionCategoria.getChildren().add(lbTitulo);

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
                        hboxCarrusel.getChildren().add(tarjeta);
                    }
                }
                    //Scrollpane para que se pueda mover lateralmente
                    ScrollPane scrollHorizontal = new ScrollPane(hboxCarrusel);
                    scrollHorizontal.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                    scrollHorizontal.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    scrollHorizontal.setPannable(true);
                    scrollHorizontal.setFitToHeight(true);
                    scrollHorizontal.setMinHeight(235);
                    scrollHorizontal.setPrefHeight(235);
                    seccionCategoria.getChildren().add(scrollHorizontal);
                }
            //Se añade la sección completa
            filasMain.getChildren().add(seccionCategoria);
        }
    }

    private List<Aplicacion> getAplicaciones(List<Aplicacion> listaApps) {
        String textoBusqueda = busqueda.getText().trim().toLowerCase();
        Stream<Aplicacion> streamFiltrado = listaApps.stream()
                .filter(a -> a.getNombre().toLowerCase().contains(textoBusqueda));
        if (rbAZ.isSelected()) {
            streamFiltrado = streamFiltrado.sorted(Comparator.comparing(a -> a.getNombre().toLowerCase()));
        } else if (rbZA.isSelected()) {
            streamFiltrado = streamFiltrado.sorted((a1, a2) -> a2.getNombre().compareToIgnoreCase(a1.getNombre()));
        }

        return streamFiltrado.toList();
    }

    public void actualizarSaldo(){
        Usuario user = Sesion.getUsuarioActual()    ;
        double saldo = user.getSaldo();
        saldoAjustes.setText("Monedero: " + String.format("%.2f €", saldo));
    }

    public void crearAplicacion(){
        SceneManager.inyectarEscena(tiendaPrincipal, "CrearApp.fxml");
        busqueda.setVisible(false);
    }

    @FXML
    public void abrirVentanaSaldo(){
        SceneManager.abrirVentana("Saldo.fxml", "Recargar monedero", true);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instancia = this;
        botonesNav = Arrays.asList(botonApps, botonJuegos, botonInicio, botonCrear);
        this.vistaActual="Inicio";
        actualizarEstadoMenu(botonInicio);
        panelAjustes.setMouseTransparent(false);
        panelAjustes.toFront();
        botonAjustes.setMouseTransparent(false);
        botonAjustes.toFront();
        generarTienda();
    }
}