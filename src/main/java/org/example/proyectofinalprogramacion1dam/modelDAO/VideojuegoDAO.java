package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;
import org.example.proyectofinalprogramacion1dam.model.Videojuego;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO encargada del subtipo de aplicaciones Videojuego
 */
public class VideojuegoDAO {
    private final static String SQL_ALL_V = "SELECT idApp, multijugador FROM videojuego";
    private final static String SQL_FIND_SPECIFIC = "SELECT multijugador FROM videojuego WHERE idApp = ?";
    private final static String SQL_INSERT_SPECIFIC = "INSERT INTO videojuego (idApp, multijugador) VALUES (?, ?)";
    private final static String SQL_UPDATE_SPECIFIC = "UPDATE videojuego SET multijugador = ? WHERE idApp = ?";

    /**
     * Lista de todos los videojuegos
     * @return lista de videojuegos
     */
    public static List<Videojuego> findAll() {
        List<Videojuego> videojuegos = new ArrayList<>();
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_ALL_V);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idApp");
                boolean multi = rs.getBoolean("multijugador");
                Aplicacion base = AplicacionDAO.findById(id);

                if (base != null) {
                    videojuegos.add(new Videojuego(
                            base.getId(),
                            base.getNombre(),
                            base.getDescripcion(),
                            base.getPrecio(),
                            base.getDescargas(),
                            base.getCategoria(),
                            base.getIdDesarrollador(),
                            base.getImagen(),
                            multi
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar videojuegos con PreparedStatement: " + e.getMessage());
        }
        return videojuegos;
    }

    /**
     * Busca un videojuego por su id
     * @param id id del videojuego
     * @return videojuego si lo encuentra, null si no
     */
    public static Videojuego findById(int id) {
        Aplicacion base = AplicacionDAO.findById(id);
        if (base == null) return null;

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_SPECIFIC)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Videojuego(
                        base.getId(),
                        base.getNombre(),
                        base.getDescripcion(),
                        base.getPrecio(),
                        base.getDescargas(),
                        base.getCategoria(),
                        base.getIdDesarrollador(),
                        base.getImagen(),
                        rs.getBoolean("multijugador")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar videojuego por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Añade un videojuego
     * @param v videojueg a añadir
     * @return true si se puede añadir, false si no
     */
    public static boolean addVideojuego(Videojuego v) {
        if (v == null) return false;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT_SPECIFIC)) {
            ps.setInt(1, v.getId());
            ps.setBoolean(2, v.isMultijugador());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar en la tabla videojuego: " + e.getMessage());
        }
    }

    /**
     * Actualiza la informacion de un videojuego
     * @param v videojuego que acabo de actualizar
     */
    public static void updateVideojuego(Videojuego v) {
        if (AplicacionDAO.updateAplicacion(v)) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE_SPECIFIC)) {
                ps.setBoolean(1, v.isMultijugador());
                ps.setInt(2, v.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar subtipo Videojuego: " + e.getMessage());
            }
        }
    }
}