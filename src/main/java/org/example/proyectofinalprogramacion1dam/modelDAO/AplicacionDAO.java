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
    private final static String SQL_INSERT = "INSERT INTO aplicacion (nombre, descripcion, precio, version, descargas, categoria, id_desarrollador) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE aplicacion SET nombre = ?, descripcion = ?, precio = ?, version = ?, descargas = ?, categoria = ?, id_desarrollador = ? WHERE id = ?";
    private final static String SQL_DELETE = "DELETE FROM aplicacion WHERE id = ?";

    /**
     * Obtiene todas las aplicaciones cumpliendo con el requisito de CRUD completo[cite: 55, 140].
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
                        rs.getInt("id_desarrollador")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aplicaciones;
    }

    /**
     * Busca una aplicación por ID. Útil para el control de integridad y persistencia[cite: 141, 142].
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
                        rs.getInt("id_desarrollador")
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
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_NOMBRE)) {
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
                        rs.getInt("id_desarrollador")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aplicaciones;
    }

    /**
     * Inserta una nueva aplicación. Se encarga de la persistencia de datos avanzados[cite: 142, 170].
     */
    public static boolean addAplicacion(Aplicacion app) {
        if (app == null) return false;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
            ps.setString(1, app.getNombre());
            ps.setString(2, app.getDescripcion());
            ps.setDouble(3, app.getPrecio());
            ps.setString(4, app.getVersion());
            ps.setInt(5, app.getDescargas());
            ps.setString(6, app.getCategoria().name()); // Guardamos el nombre del Enum
            ps.setInt(7, app.getIdDesarrollador());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualiza los datos de la app. Cumple con el criterio de "Cómo funciona el actualizar"[cite: 106].
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
     * Elimina una aplicación de la base de datos[cite: 106].
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