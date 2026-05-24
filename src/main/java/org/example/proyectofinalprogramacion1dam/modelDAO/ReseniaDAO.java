package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.Resenia;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para la gestión de la persistencia de las valoraciones y opiniones de los usuarios.
 */
public class ReseniaDAO {
    private final static String SQL_ALL = "SELECT * FROM resenia";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM resenia WHERE id = ?";
    private final static String SQL_FIND_BY_APP = "SELECT * FROM resenia WHERE idApp = ?";
    private final static String SQL_INSERT = "INSERT INTO resenia (puntuacion, comentario, fechaResenia, idApp, idUsuario) VALUES (?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE resenia SET puntuacion = ?, comentario = ? WHERE id = ?";
    private final static String SQL_DELETE = "DELETE FROM resenia WHERE id = ?";

    /**
     * Busca todas las reseñas
     * @return lista de reseñas
     */
    public static List<Resenia> findAll() {
        List<Resenia> resenias = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                resenias.add(new Resenia(
                        rs.getInt("id"),
                        rs.getInt("puntuacion"),
                        rs.getString("comentario"),
                        rs.getTimestamp("fechaResenia").toLocalDateTime(),
                        rs.getInt("idApp"),
                        rs.getInt("idUsuario")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resenias;
    }

    /**
     * Busca una reseña por su id
     * @param id Numero distintivo de la reseña
     * @return reseña con ese id
     */
    public static Resenia findById(int id) {
        Resenia resenia = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                resenia = new Resenia(
                        rs.getInt("id"),
                        rs.getInt("puntuacion"),
                        rs.getString("comentario"),
                        rs.getTimestamp("fechaResenia").toLocalDateTime(),
                        rs.getInt("idApp"),
                        rs.getInt("idUsuario")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resenia;
    }

    /**
     * Busca todas las reseñas asociadas a una aplicación específica.
     * @param idApp Id de la aplicacion de la que se van a buscar las reseñas
     * @return reseñas en la app con ese id
     */
    public static List<Resenia> findByApp(int idApp) {
        List<Resenia> resenias = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_APP)) {
            ps.setInt(1, idApp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resenias.add(new Resenia(
                        rs.getInt("id"),
                        rs.getInt("puntuacion"),
                        rs.getString("comentario"),
                        rs.getTimestamp("fechaResenia").toLocalDateTime(),
                        rs.getInt("idApp"),
                        rs.getInt("idUsuario")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resenias;
    }

    /**
     * Inserta una reseña a la base de datps
     * @param resenia Objeto reseña que se va a introducir
     */
    public static void addResenia(Resenia resenia) {
        if (resenia == null) return;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
            ps.setInt(1, resenia.getPuntuacion());
            ps.setString(2, resenia.getComentario());
            // Si la fecha es null en el objeto, usamos la fecha actual del sistema
            LocalDateTime fecha = (resenia.getFechaResenia() != null) ? resenia.getFechaResenia() : LocalDateTime.now();
            ps.setTimestamp(3, Timestamp.valueOf(fecha));
            ps.setInt(4, resenia.getIdApp());
            ps.setInt(5, resenia.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualiza el comentario o la puntuación de una reseña existente
     * @param resenia reseña que se va a cambair
     */
    public static void updateResenia(Resenia resenia) {
        if (resenia == null || findById(resenia.getId()) == null) return;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, resenia.getPuntuacion());
            ps.setString(2, resenia.getComentario());
            ps.setInt(3, resenia.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina una reseña de la base de datos
     * @param id Numero distintivo de la reseña
     * @return reseña eliminada
     */
    public static boolean deleteResenia(int id) {
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}