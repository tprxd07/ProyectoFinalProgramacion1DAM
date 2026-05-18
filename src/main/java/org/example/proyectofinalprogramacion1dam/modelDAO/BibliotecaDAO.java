package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.Biblioteca;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;
import org.example.proyectofinalprogramacion1dam.model.HistorialCompra;
import org.example.proyectofinalprogramacion1dam.model.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaDAO {
    private final static String SQL_INSERT = "INSERT INTO adquiere (idApp, idUsuario, fechaAdquisicion) VALUES (?, ?, ?)";
    private final static String SQL_FIND_BY_USER = "SELECT * FROM adquiere WHERE idUsuario = ?";
    private final static String SQL_FIND_ALL = "SELECT * FROM adquiere";
    private final static String SQL_CHECK_OWNER = "SELECT * FROM adquiere WHERE idApp = ? AND idUsuario = ?";
    private final static String SQL_UPDATE_DESCARGAS = "UPDATE aplicacion SET descargas = descargas + 1 WHERE id = ?";

    /**
     * Hace que el usuario adquiera una app
     * @param user Usuario que va a comprar
     * @param app App que se va a adquirir
     * @return true si se ha realizado correctamente, false si no
     */
    public static boolean adquirirApp(Usuario user, Aplicacion app) {
        Connection con = null;
        try {
            con = ConnectionBD.getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
                ps.setInt(1, app.getId());
                ps.setInt(2, user.getId());
                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                ps.executeUpdate();
            }

            //+1 a las descargas de la app
            try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE_DESCARGAS)) {
                ps.setInt(1, app.getId());
                ps.executeUpdate();
            }

            //si tiene precio se usa HistorialCompra y compraDAO
            if (app.getPrecio() > 0) {
                HistorialCompra hc = new HistorialCompra(app.getId(), user.getId(), app.getPrecio(), LocalDateTime.now());

                // Registramos usando tu metodo existente
                if (!CompraDAO.registrarCompra(hc)) {
                    throw new SQLException("Error al registrar el pago");
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) { //añadimos el con.commit para poder usar el rollback en caso de error en la transferencia
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            throw new RuntimeException("Error en el proceso: " + e.getMessage());
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * Busca la lista de apps de un usuario
     * @param idUsuario busca al usuario por su id
     * @return Lista de juegos en la biblioteca del usuario
     */
    public static List<Biblioteca> findByUsuario(int idUsuario) {
        List<Biblioteca> lista = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_USER)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                lista.add(new Biblioteca(
                        rs.getInt("idApp"),
                        rs.getInt("idUsuario"),
                        rs.getTimestamp("fechaAdquisicion").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar biblioteca: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Comprueba si un usuario ya tiene una aplicacion en su biblioteca
     * @param idUsuario id del usuario logueado
     * @param idApp id de la aplicación a consultar
     * @return true si ya la tiene, false si no
     */
    public static boolean usuarioTieneApp(int idUsuario, int idApp) {
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_CHECK_OWNER)) {
            ps.setInt(1, idApp);
            ps.setInt(2, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar propiedad: " + e.getMessage());
        }
    }
}