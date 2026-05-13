package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.HistorialCompra;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {
    private final static String SQL_INSERT = "INSERT INTO paga (idApp, idUsuario, precioPagado, fechaCompra) VALUES (?, ?, ?, ?)";
    private final static String SQL_FIND_BY_USER = "SELECT * FROM paga WHERE idUsuario = ?";
    private final static String SQL_ALL = "SELECT * FROM paga";

    /**
     * Registra una nueva compra en la tabla 'paga'.
     * @param hc Objeto con los datos de la transacción.
     * @return true si la inserción fue exitosa.
     */
    public static boolean registrarCompra(HistorialCompra hc) {
        if (hc == null) return false;

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
            ps.setInt(1, hc.getIdApp());
            ps.setInt(2, hc.getIdUsuario());
            ps.setDouble(3, hc.getPrecioPagado());

            // Gestionamos la fecha: si es null en el objeto, usamos la actual
            LocalDateTime fecha = (hc.getFechaCompra() != null) ? hc.getFechaCompra() : LocalDateTime.now();
            ps.setTimestamp(4, Timestamp.valueOf(fecha));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar compra: " + e.getMessage());
        }
    }

    /**
     * Encuentra el historial de un usuario por su ID
     * @param idUsuario id del usuario
     * @return historial del usuario
     */
    public static List<HistorialCompra> findByUsuario(int idUsuario) {
        List<HistorialCompra> historial = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_USER)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                historial.add(new HistorialCompra(
                        rs.getInt("idApp"),
                        rs.getInt("idUsuario"),
                        rs.getDouble("precioPagado"),
                        rs.getTimestamp("fechaCompra").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar historial: " + e.getMessage());
        }
        return historial;
    }

    /**
     * Devuelve todas las compras registradas
     */
    public static List<HistorialCompra> findAll() {
        List<HistorialCompra> lista = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                lista.add(new HistorialCompra(
                        rs.getInt("idApp"),
                        rs.getInt("idUsuario"),
                        rs.getDouble("precioPagado"),
                        rs.getTimestamp("fechaCompra").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar todas las compras: " + e.getMessage());
        }
        return lista;
    }
}