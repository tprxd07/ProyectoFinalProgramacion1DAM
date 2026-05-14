package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.Logro;
import org.example.proyectofinalprogramacion1dam.model.LogroObtenido;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogroDAO {
    private final static String SQL_ALL = "SELECT * FROM logro";
    private final static String SQL_FIND_BY_GAME = "SELECT * FROM logro WHERE idVideojuego = ?";
    private final static String SQL_INSERT = "INSERT INTO logro (nombre, descripcion, idVideojuego) VALUES (?, ?, ?)";
    private final static String SQL_DELETE = "DELETE FROM logro WHERE id = ?";
    private final static String SQL_INSERT_OBTENIDO = "INSERT INTO logro_obtenido (idUsuario, idLogro, fechaDesbloqueo) VALUES (?, ?, ?)";
    private final static String SQL_FIND_OBTENIDOS_BY_USER = "SELECT * FROM logro_obtenido WHERE idUsuario = ?";

    /**
     * Busca todos los logros
     * @return Devuelve todos los logros
     */
    public static List<Logro> findAll() {
        List<Logro> logros = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                logros.add(new Logro(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("idVideojuego")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return logros;
    }

    /**
     * Busca los logros de un videojuego
     * @param idVideojuego id del videojuego
     * @return lista de logros del videojuego especificado
     */
    public static List<Logro> findByVideojuego(int idVideojuego) {
        List<Logro> logros = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_GAME)) {
            ps.setInt(1, idVideojuego);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                logros.add(new Logro(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("idVideojuego")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return logros;
    }

    /**
     * Registra un nuevo logro en la base de datos
     * @param logro Datos del logro
     * @return logro añadido
     */
    public static boolean addLogro(Logro logro) {
        if (logro == null || logro.getIdVideojuego() <= 0) {
            return false;
        }

        if (VideojuegoDAO.findById(logro.getIdVideojuego()) == null) {
            System.err.println("Error: El videojuego asociado no existe.");
            return false;
        }

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
            ps.setString(1, logro.getNombre());
            ps.setString(2, logro.getDescripcion());
            ps.setInt(3, logro.getIdVideojuego());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Registra cuando un usuario obtiene un logro
     * @param obtenido logro obtenido en el perfil del usuario
     * @return logro actualizado al perfil del usuario
     */
    public static boolean registrarLogroObtenido(LogroObtenido obtenido) {
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT_OBTENIDO)) {
            ps.setInt(1, obtenido.getIdUsuario());
            ps.setInt(2, obtenido.getIdLogro());

            LocalDateTime fecha = (obtenido.getFechaDesbloqueo() != null) ? obtenido.getFechaDesbloqueo() : LocalDateTime.now();
            ps.setTimestamp(3, Timestamp.valueOf(fecha));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Obtiene el historial de logros de un usuario
     * @param idUsuario id del usaurio
     * @return lista de los logros obtenidos
     */
    public static List<LogroObtenido> findObtenidosByUsuario(int idUsuario) {
        List<LogroObtenido> obtenidos = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_OBTENIDOS_BY_USER)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                obtenidos.add(new LogroObtenido(
                        rs.getInt("idUsuario"),
                        rs.getInt("idLogro"),
                        rs.getTimestamp("fechaDesbloqueo").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obtenidos;
    }

    /**
     * Elimina un logro de la base de datos por su ID.
     * @param id ID del logro a eliminar.
     * @return true si se ha eliminado correctamente.
     */
    public static boolean deleteLogro(int id) {
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}