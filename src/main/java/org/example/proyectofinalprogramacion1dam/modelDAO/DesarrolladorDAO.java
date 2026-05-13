package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.Desarrollador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DesarrolladorDAO {
    private final static String SQL_ALL = "SELECT * FROM desarrollador";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM desarrollador WHERE id = ?";
    private final static String SQL_FIND_BY_NOMBRE = "SELECT * FROM desarrollador WHERE nombre = ?";
    private final static String SQL_INSERT = "INSERT INTO desarrollador (nombre, pais, webOficial) VALUES (?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE desarrollador SET nombre = ?, pais = ?, webOficial = ? WHERE id = ?";
    private final static String SQL_DELETE = "DELETE FROM desarrollador WHERE id = ?";

    /**
     * Devuelve una lista con todos los desarrolladores de la base de datos.
     * @return lista de desarrolladores
     */
    public static List<Desarrollador> findAll() {
        List<Desarrollador> desarrolladores = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                desarrolladores.add(new Desarrollador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("pais"),
                        rs.getString("webOficial")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener desarrolladores: " + e.getMessage());
        }
        return desarrolladores;
    }

    /**
     * Busca un desarrollador por su ID.
     * @param id id del desarrollador
     * @return El objeto Desarrollador o null si no existe.
     */
    public static Desarrollador findById(int id) {
        Desarrollador desarrollador = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                desarrollador = new Desarrollador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("pais"),
                        rs.getString("webOficial")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar desarrollador por ID: " + e.getMessage());
        }
        return desarrollador;
    }

    /**
     * Busca un desarrollador por su nombre.
     * @param nombre nombre del desarrollador
     * @return El objeto Desarrollador o null si no existe.
     */
    public static Desarrollador findByNombre(String nombre) {
        Desarrollador desarrollador = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_NOMBRE)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                desarrollador = new Desarrollador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("pais"),
                        rs.getString("webOficial")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar desarrollador por nombre: " + e.getMessage());
        }
        return desarrollador;
    }

    /**
     * Registra un nuevo desarrollador en la base de datos.
     * @param dev Objeto desarrollador con los datos.
     * @return true si se ha insertado correctamente.
     */
    public static boolean addDesarrollador(Desarrollador dev) {
        if (dev == null || findByNombre(dev.getNombre()) != null) {
            return false;
        }
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
            ps.setString(1, dev.getNombre());
            ps.setString(2, dev.getPais());
            ps.setString(3, dev.getWebOficial());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al añadir desarrollador: " + e.getMessage());
        }
    }

    /**
     * Actualiza los datos de un desarrollador
     * @param dev Objeto con los datos actualizados
     * @return true si se actualizo correctamente
     */
    public static boolean updateDesarrollador(Desarrollador dev) {
        if (dev == null || findById(dev.getId()) == null) {
            return false;
        }
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
            ps.setString(1, dev.getNombre());
            ps.setString(2, dev.getPais());
            ps.setString(3, dev.getWebOficial());
            ps.setInt(4, dev.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar desarrollador: " + e.getMessage());
        }
    }

    /**
     * Elimina un desarrollador de la base de datos.
     * @param id ID del desarrollador a borrar.
     * @return true si se borró correctamente. [cite: 106]
     */
    public static boolean deleteDesarrollador(int id) {
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar desarrollador: " + e.getMessage());
        }
    }
}