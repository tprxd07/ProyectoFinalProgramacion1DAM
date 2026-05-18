package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;
import org.example.proyectofinalprogramacion1dam.model.Categoria;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AplicacionDAO {
    private final static String SQL_ALL = "SELECT * FROM aplicacion";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM aplicacion WHERE id = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM aplicacion WHERE nombre = ?";
    private final static String SQL_INSERT = "INSERT INTO aplicacion (nombre, descripcion, precio, version, descargas, categoria, iddesarrollador, imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE aplicacion SET nombre = ?, descripcion = ?, precio = ?, version = ?, descargas = ?, categoria = ?, iddesarrollador = ?, imagen = ? WHERE id = ?";
    private final static String SQL_DELETE = "DELETE FROM aplicacion WHERE id = ?";

    /**
     * Busca todas las aplicaciones en la base de datos
     * @return Lista de las aplicaciones
     */
    public static List<Aplicacion> findAll() {
        List<Aplicacion> aplicaciones = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                aplicaciones.add(new Aplicacion(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("version"),
                        rs.getInt("descargas"),
                        Categoria.valueOf(rs.getString("categoria")), // Asumiendo que es un Enum
                        rs.getInt("iddesarrollador"),
                        rs.getString("imagen")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aplicaciones;
    }

    /**
     * Busca una app por su id
     * @param id id de la app
     * @return al app con ese id
     */
    public static Aplicacion findById(int id) {
        Aplicacion app = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                app = new Aplicacion(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("version"),
                        rs.getInt("descargas"),
                        Categoria.valueOf(rs.getString("categoria")),
                        rs.getInt("iddesarrollador"),
                        rs.getString("imagen")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return app;
    }

    /**
     * Busca aplicaciones cuyo nombre coincida total o parcialmente con el texto indicado.
     * @param nombre Texto a buscar en el nombre de la aplicación.
     * @return Lista de aplicaciones que coinciden con la búsqueda.
     */
    public static List<Aplicacion> findByNombre(String nombre) {
        List<Aplicacion> aplicaciones = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                aplicaciones.add(new Aplicacion(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("version"),
                        rs.getInt("descargas"),
                        Categoria.valueOf(rs.getString("categoria")),
                        rs.getInt("iddesarrollador"),
                        rs.getString("imagen")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aplicaciones;
    }

    /**
     * Añade una app a la base de datos
     * @param app Aplicacion que se ha creado
     * @return Id de la aplicacion creada, -1 si no ha sido posible
     */
    public static int addAplicacion(Aplicacion app) {
        if (app == null) return -1;
       //return_generates_keys devuelve el ID generado por la base de datos
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, app.getNombre());
            ps.setString(2, app.getDescripcion());
            ps.setDouble(3, app.getPrecio());
            ps.setString(4, app.getVersion());
            ps.setInt(5, app.getDescargas());
            ps.setString(6, app.getCategoria().name());
            ps.setInt(7, app.getIdDesarrollador());
            ps.setString(8, app.getImagen());

            //Buscamos la ID si se inserta correctamente
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; //Devolvemos -1 si algo falla
    }

    /**
     * Actualiza los datos de una aplicacion en la base de datos
     * @param app Aplicacion que se va amodificar
     * @return True si se ha podido modificar, false si no se ha podido/no se encuentra la app
     */
    public static boolean updateAplicacion(Aplicacion app) {
        if (app == null) return false;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
            ps.setString(1, app.getNombre());
            ps.setString(2, app.getDescripcion());
            ps.setDouble(3, app.getPrecio());
            ps.setString(4, app.getVersion());
            ps.setInt(5, app.getDescargas());
            ps.setString(6, app.getCategoria().name());
            ps.setInt(7, app.getIdDesarrollador());
            ps.setInt(8, app.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina una app de la base de datos
     * @param id id de la app
     * @return app eliminada
     */
    public static boolean deleteAplicacion(int id) {
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}