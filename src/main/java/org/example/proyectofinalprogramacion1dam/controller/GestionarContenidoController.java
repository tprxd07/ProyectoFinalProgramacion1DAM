package org.example.proyectofinalprogramacion1dam.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion1dam.model.*;
import org.example.proyectofinalprogramacion1dam.modelDAO.*;
import org.example.proyectofinalprogramacion1dam.utils.Alerta;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GestionarContenidoController implements Initializable {

    //Botones para elegir desarrolladores o app
    @FXML private Button botonPestaniaApp, botonPestaniasDes;

    //Botones para elegir la accion
    @FXML private Button botonAniadir, botonModificar, botonEliminar;

    //Contenedores de formularios
    @FXML private VBox formDesarrollador, formAplicacion, formEliminar;

    //Formulario: Desarrollador
    @FXML private TextField nombreDes, paisDes;

    //Formulario: Aplicacion
    @FXML private TextField nombreApp, precioApp, imagenApp;
    @FXML private ComboBox<String> cbDesarrolladorApp;
    @FXML private TextArea descApp;
    @FXML private RadioButton rbVideojuego, rbUtilidad;
    @FXML private HBox contenedorMultijugador;
    @FXML private ComboBox<String> cbMultijugador;
    @FXML private ComboBox<Categoria> cbCategoria;

    //Panel eliminar
    @FXML private ComboBox<String> cbElementosBorrar;
    @FXML private Button botonConfirmar;

    private boolean modoApp = true; //true = App, false = Desarrollador
    private int accionActual = 1; //1 = Añadir, 2 = Modificar, 3 = Eliminar

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Cbbox de multijugador por defecto en No
        if (cbMultijugador != null) {
            cbMultijugador.setItems(FXCollections.observableArrayList("Sí", "No"));
            cbMultijugador.setValue("No");
        }

        //Cargar los desarrolladores existentes en el ComboBox del formulario
        cargarComboDesarrolladores();

        //Configurar los filtros dinámicos de categorías según el tipo de aplicación seleccionado
        rbVideojuego.setOnAction(e -> actualizarCategoriasPorTipo());
        rbUtilidad.setOnAction(e -> actualizarCategoriasPorTipo());
        actualizarCategoriasPorTipo();

        //Buscador de Aplicaciones (Salta al pulsar intro o al cambiar de casilla)
        nombreApp.setOnAction(e -> autoRellenarAplicacion());
        nombreApp.focusedProperty().addListener((obs, teniaFoco, tieneFoco) -> {
            if (!tieneFoco && accionActual == 2) {
                autoRellenarAplicacion();
            }
        });

        //Buscador de Desarrolladores (Salta al pulsar ENTER o al cambiar de casilla)
        nombreDes.setOnAction(e -> autoRellenarDesarrollador());
        nombreDes.focusedProperty().addListener((obs, teniaFoco, tieneFoco) -> {
            if (!tieneFoco && accionActual == 2) {
                autoRellenarDesarrollador();
            }
        });

        //Valores seleccionados por defecto al entrar a la ventana
        modoApp = true;
        accionActual = 1;
        botonConfirmar.setText("Añadir Contenido");

        actualizarPestaniasVisuales();
        actualizarAccionVisual(botonAniadir);
        cambiarFormularioVisibilidad();
    }

    //Carga la CbBox para que se muestren los desarrolladores disponibles
    private void cargarComboDesarrolladores() {
        try {
            List<Desarrollador> listaDevs = DesarrolladorDAO.findAll();
            List<String> nombresDevs = new ArrayList<>();
            for (Desarrollador d : listaDevs) {
                nombresDevs.add(d.getNombre());
            }
            cbDesarrolladorApp.setItems(FXCollections.observableArrayList(nombresDevs));
        } catch (Exception e) {
            System.err.println("Error al poblar el ComboBox de desarrolladores.");
        }
    }

    //Carga las categorias respondientes segun sea videojuego o utilidad
    private void actualizarCategoriasPorTipo() {
        if (rbVideojuego.isSelected()) {
            contenedorMultijugador.setVisible(true);
            contenedorMultijugador.setManaged(true);
            List<Categoria> catJuegos = new ArrayList<>();
            for (Categoria c : Categoria.values()) {
                if (c == Categoria.MMO || c == Categoria.Shooter || c == Categoria.Indie || c == Categoria.Aventura) {
                    catJuegos.add(c);
                }
            }
            cbCategoria.setItems(FXCollections.observableArrayList(catJuegos));
        } else {
            contenedorMultijugador.setVisible(false);
            contenedorMultijugador.setManaged(false);
            List<Categoria> catUtils = new ArrayList<>();
            for (Categoria c : Categoria.values()) {
                if (c == Categoria.Salud || c == Categoria.Finanzas || c == Categoria.Ocio) {
                    catUtils.add(c);
                }
            }
            cbCategoria.setItems(FXCollections.observableArrayList(catUtils));
        }
    }

    //Rellena automaticamente el resto de datos al poner el nombre de la aplicacion (solo si se va a modificar
    private void autoRellenarAplicacion() {
        if (accionActual == 1) return;

        String nombreBuscar = nombreApp.getText().trim();
        if (nombreBuscar.isEmpty()) return;

        try {
            Aplicacion app = null;
            List<Aplicacion> todasLasApps = AplicacionDAO.findAll();

            for (Aplicacion a : todasLasApps) {
                if (a.getNombre().trim().equalsIgnoreCase(nombreBuscar)) {
                    app = a;
                    break;
                }
            }

            if (app != null) {
                precioApp.setText(String.valueOf(app.getPrecio()));
                descApp.setText(app.getDescripcion());
                imagenApp.setText(app.getImagen());

                Desarrollador dev = DesarrolladorDAO.findById(app.getIdDesarrollador());
                if (dev != null) {
                    cbDesarrolladorApp.setValue(dev.getNombre());
                }

                Videojuego vj = VideojuegoDAO.findById(app.getId());
                if (vj != null) {
                    rbVideojuego.setSelected(true);
                    actualizarCategoriasPorTipo();
                    cbMultijugador.setValue(vj.isMultijugador() ? "Sí" : "No");
                } else {
                    rbUtilidad.setSelected(true);
                    actualizarCategoriasPorTipo();
                }
                cbCategoria.setValue(app.getCategoria());
            } else {
                precioApp.clear();
                descApp.clear();
                imagenApp.clear();
                cbDesarrolladorApp.setValue(null);
                cbCategoria.setValue(null);
            }
        } catch (Exception e) {
            System.err.println("Error durante el autocompletado de la aplicación: " + e.getMessage());
        }
    }

    //Rellena el país del desarrollador si encuentra su nombre
    private void autoRellenarDesarrollador() {
        String nombreBuscar = nombreDes.getText().trim();
        if (nombreBuscar.isEmpty()) return;

        try {
            Desarrollador d = DesarrolladorDAO.findByNombre(nombreBuscar);
            if (d != null) {
                paisDes.setText(d.getPais());
            }
        } catch (Exception e) {
            System.err.println("Error durante el autocompletado del desarrollador.");
        }
    }

    //Cambia para modificar entre aplicaciones y desarrolladores al pulsar el boton
    @FXML
    private void cambiarPestania(ActionEvent event) {
        Button botonPulsado = (Button) event.getSource();
        if (botonPulsado == botonPestaniaApp) {
            modoApp = true;
        } else if (botonPulsado == botonPestaniasDes) {
            modoApp = false;
        }
        actualizarPestaniasVisuales();
        cambiarFormularioVisibilidad();
    }

    //Cambia si la accion qeu se va a hacer es añadir, modificar o eliminar
    @FXML
    private void cambiarAccion(ActionEvent event) {
        Button botonPulsado = (Button) event.getSource();
        resetearCampos();

        if (botonPulsado == botonAniadir) {
            accionActual = 1;
            botonConfirmar.setText("Añadir Contenido");
        } else if (botonPulsado == botonModificar) {
            accionActual = 2;
            botonConfirmar.setText("Guardar Cambios");
        } else if (botonPulsado == botonEliminar) {
            accionActual = 3;
            botonConfirmar.setText("Eliminar permanentemente");
            cargarElementosEliminar();
        }
        actualizarAccionVisual(botonPulsado);
        cambiarFormularioVisibilidad();
    }

    //Carga el cbBox de los elementos que se pueden eliminar, siendo aplicaciones enteras o desarrolladores
    private void cargarElementosEliminar() {
        List<String> items = new ArrayList<>();
        if (modoApp) {
            List<Aplicacion> apps = AplicacionDAO.findAll();
            for (Aplicacion a : apps) {
                items.add(a.getId() + " - " + a.getNombre());
            }
        } else {
            List<Desarrollador> devs = DesarrolladorDAO.findAll();
            for (Desarrollador d : devs) {
                items.add(d.getId() + " - " + d.getNombre());
            }
        }
        cbElementosBorrar.setItems(FXCollections.observableArrayList(items));
    }

    //Cambia de accion al confirmar segun la elegida
    @FXML
    private void ejecutarOperacion() {
        try {
            if (accionActual == 3) {
                borrar();
            } else if (accionActual == 1) {
                ejecutarAlta();
            } else {
                ejecutarModificacion();
            }

            if (TiendaPrincipalController.getInstance() != null) {
                TiendaPrincipalController.getInstance().generarTienda();
            }

            Stage stage = (Stage) botonConfirmar.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            System.err.println("Error procesando la operación en el panel de administración.");
            e.printStackTrace();
        }
    }

    //Añade una app o desarrollador
    private void ejecutarAlta() {
        if (modoApp) {
            String nombre = nombreApp.getText().trim();
            String devNombre = cbDesarrolladorApp.getValue();
            Categoria cat = cbCategoria.getValue();

            //Validación de campos obligatorios
            if (nombre.isEmpty() || devNombre == null || cat == null) {
                Alerta.mostrarInformacion("Campos Incompletos", "Faltan datos", "Por favor, rellena el nombre, desarrollador y categoría.");
                return;
            }

            Desarrollador dev = DesarrolladorDAO.findByNombre(devNombre);
            if (dev == null) return;

            String textoPrecio = precioApp.getText().trim();
            double precio = 0.0;

            if (!textoPrecio.isEmpty()) {
                try {
                    textoPrecio = textoPrecio.replace(',', '.');
                    precio = Double.parseDouble(textoPrecio);

                    if (precio < 0) {
                        Alerta.mostrarError("Error precio", "El precio no puede ser negativo", "Cambia el precio para continuar");
                        return; // Detiene la ejecución
                    }
                } catch (NumberFormatException e) {
                    Alerta.mostrarError("Error precio", "El número no es válido","Debe ser un número válido (ej: 2.99) o dejarse vacío para que sea gratis.");
                    return;
                }
            }

            //Insertamos en la tabla aplicacion
            Aplicacion app = new Aplicacion(0, nombre, descApp.getText().trim(), precio, 0, cat, dev.getId(), imagenApp.getText().trim());
            int idGenerado = AplicacionDAO.addAplicacion(app);

            //Se inserta en la tabla que va a heredar
            if (idGenerado > 0) {
                try {
                    if (rbVideojuego.isSelected()) {
                        boolean esMulti = cbMultijugador.getValue() != null && cbMultijugador.getValue().equals("Sí");
                        Videojuego vj = new Videojuego(idGenerado, nombre, app.getDescripcion(), precio, 0, cat, dev.getId(), app.getImagen(), esMulti);
                        VideojuegoDAO.addVideojuego(vj);
                    } else {
                        Utilidad ut = new Utilidad(idGenerado, nombre, app.getDescripcion(), precio, 0, cat, dev.getId(), app.getImagen());
                        UtilidadDAO.addUtilidad(ut);
                    }
                    Alerta.mostrarInformacion("Operación Exitosa", "Aplicación Añadida", "Se ha añadido \"" + nombre + "\" correctamente.");

                } catch (Exception ex) {
                    System.err.println("Error crítico al insertar en la tabla específica (Videojuego/Utilidad): " + ex.getMessage());
                    ex.printStackTrace();
                    Alerta.mostrarInformacion("Error de Base de Datos", "No se pudo completar el alta", "La aplicación general se creó, pero falló su especialización como Utilidad.");
                }
            } else {
                Alerta.mostrarInformacion("Error", "No se pudo añadir", "Hubo un problema al registrar la aplicación en la base de datos.");
            }
            //si no esta en modoap, se añade en la tabla desarrollador
        } else {
            String nombreDev = nombreDes.getText().trim();
            if (nombreDev.isEmpty()) return;

            Desarrollador d = new Desarrollador(0, nombreDev, paisDes.getText().trim());
            DesarrolladorDAO.addDesarrollador(d);
            Alerta.mostrarInformacion("Operación exitosa", "Desarrollador añadido", "Se ha añadido el desarrollador \"" + nombreDev + "\" correctamente.");
        }
    }

    //Modifica los datos del contenido elegido, siendo una app o un desarrollador
    private void ejecutarModificacion() {
        if (modoApp) {
            String nombre = nombreApp.getText().trim();
            if (nombre.isEmpty()) return;

            Aplicacion app = null;
            for (Aplicacion a : AplicacionDAO.findAll()) {
                if (a.getNombre().equalsIgnoreCase(nombre)) {
                    app = a;
                    break;
                }
            }

            if (app == null) return;

            String devNombre = cbDesarrolladorApp.getValue();
            Desarrollador dev = DesarrolladorDAO.findByNombre(devNombre);
            int idDevAsignar = (dev != null) ? dev.getId() : app.getIdDesarrollador();

            try {
                app.setPrecio(Double.parseDouble(precioApp.getText().trim()));
            } catch (NumberFormatException e) { return; }

            app.setDescripcion(descApp.getText().trim());
            app.setImagen(imagenApp.getText().trim());
            app.setCategoria(cbCategoria.getValue());
            app.setIdDesarrollador(idDevAsignar);

            AplicacionDAO.updateAplicacion(app);

            if (rbVideojuego.isSelected()) {
                boolean esMulti = cbMultijugador.getValue() != null && cbMultijugador.getValue().equals("Sí");
                Videojuego vj = new Videojuego(app.getId(), app.getNombre(), app.getDescripcion(), app.getPrecio(), app.getDescargas(), app.getCategoria(), app.getIdDesarrollador(), app.getImagen(), esMulti);
                VideojuegoDAO.updateVideojuego(vj);
            } else {
                Utilidad ut = new Utilidad(app.getId(), app.getNombre(), app.getDescripcion(), app.getPrecio(), app.getDescargas(), app.getCategoria(), app.getIdDesarrollador(), app.getImagen());
                UtilidadDAO.updateUtilidad(ut);
            }
            Alerta.mostrarInformacion("Operación Exitosa", "Aplicación Modificada", "Se ha modificado \"" + nombre + "\" correctamente.");
        } else {
            String nombreDev = nombreDes.getText().trim();
            if (nombreDev.isEmpty()) return;

            Desarrollador d = DesarrolladorDAO.findByNombre(nombreDev);
            if (d == null) return;

            d.setPais(paisDes.getText().trim());
            DesarrolladorDAO.updateDesarrollador(d);
            Alerta.mostrarInformacion("Operación Exitosa", "Desarrollador Modificado", "Se ha modificado el desarrollador \"" + nombreDev + "\" correctamente.");
        }
    }

    //Elimina el objeto elegido en el cbBox
    private void borrar() {
        String seleccion = cbElementosBorrar.getValue();
        if (seleccion == null) return;

        boolean seguro = Alerta.mostrarConfirmacion(
                "Confirmar eliminación",
                "¿Estás seguro de que deseas eliminar este elemento?",
                "Esta acción es irreversible y borrará el registro permanentemente de la base de datos."
        );

        if (!seguro) return;

        int idTarget = Integer.parseInt(seleccion.split(" - ")[0]);

        if (modoApp) {
            AplicacionDAO.deleteAplicacion(idTarget);
        } else {
            DesarrolladorDAO.deleteDesarrollador(idTarget);
        }
    }
//Cambia la visibilidad del formulario segun cual se ha elegido
    private void cambiarFormularioVisibilidad() {
        formAplicacion.setVisible(false);
        formAplicacion.setManaged(false);
        formDesarrollador.setVisible(false);
        formDesarrollador.setManaged(false);
        formEliminar.setVisible(false);
        formEliminar.setManaged(false);

        if (accionActual == 3) {
            formEliminar.setVisible(true);
            formEliminar.setManaged(true);
        } else if (modoApp) {
            formAplicacion.setVisible(true);
            formAplicacion.setManaged(true);
        } else {
            formDesarrollador.setVisible(true);
            formDesarrollador.setManaged(true);
        }
    }

    //Actualiza los css de los botones elegidos para app y desarrollador
    private void actualizarPestaniasVisuales() {
        botonPestaniaApp.getStyleClass().remove("nav-button-active");
        botonPestaniasDes.getStyleClass().remove("nav-button-active");

        if (modoApp) {
            botonPestaniaApp.getStyleClass().add("nav-button-active");
        } else {
            botonPestaniasDes.getStyleClass().add("nav-button-active");
        }
    }
    //Actualiza los css de los botones elegidos para las acciones
    private void actualizarAccionVisual(Button pulsado) {
        botonAniadir.getStyleClass().remove("nav-button-active");
        botonModificar.getStyleClass().remove("nav-button-active");
        botonEliminar.getStyleClass().remove("nav-button-active");
        pulsado.getStyleClass().add("nav-button-active");
    }
    //limpia los campos
    private void resetearCampos() {
        nombreApp.clear(); precioApp.clear(); imagenApp.clear(); descApp.clear();
        cbDesarrolladorApp.setValue(null);
        nombreDes.clear(); paisDes.clear();
    }
}