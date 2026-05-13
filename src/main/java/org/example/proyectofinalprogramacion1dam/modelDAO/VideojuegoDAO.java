package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.Videojuego;
import org.example.proyectofinalprogramacion1dam.model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideojuegoDAO {
    private final static String SQL_ALL_JOIN = "SELECT a.*, v.multijugador FROM aplicacion a JOIN videojuego v ON a.id = v.idApp";
    private final static String SQL_INSERT_SPECIFIC = "INSERT INTO videojuego (idApp, multijugador) VALUES (?, ?)";
    private final static String SQL_UPDATE_SPECIFIC = "UPDATE videojuego SET multijugador = ? WHERE idApp = ?";

    /**
     * Lista de todas las aplicaciones que sean vdeojuegos
     * @return lista de videojuegos
     */
    public static List<Videojuego> findAll() {
        List<Videojuego> videojuegos = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL_JOIN)) {
            while (rs.next()) {
                videojuegos.add(new Videojuego(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("version"),
                        rs.getInt("descargas"),
                        Categoria.valueOf(rs.getString("categoria")),
                        rs.getInt("idDesarrollador"), // Según tu SQL: idDesarrollador 
                        rs.getBoolean("multijugador")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar videojuegos: " + e.getMessage());
        }
        return videojuegos;
    }

    /**
     * Busca un videojuego por su ID uniendo las tablas 'aplicacion' y 'videojuego'.
     * @param id El ID de la aplicación a buscar.
     * @return Un objeto Videojuego completo o null si no se encuentra o no es un videojuego.
     */
    public static Videojuego findById(int id) {
        Videojuego videojuego = null;
        // Consulta con JOIN para obtener todos los campos
        String sql = "SELECT a.*, v.multijugador FROM aplicacion a JOIN videojuego v ON a.id = v.idApp WHERE a.id = ?";

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                videojuego = new Videojuego(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("version"),
                        rs.getInt("descargas"),
                        Categoria.valueOf(rs.getString("categoria")),
                        rs.getInt("idDesarrollador"),
                        rs.getBoolean("multijugador")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar videojuego por ID: " + e.getMessage());
        }
        return videojuego;
    }

    /**
     * Añadi un videojuego a la base de datos
     * @param v videojuego creado
     * @return videojuego añadido
     */
    public static boolean addVideojuego(Videojuego v) {
        if (AplicacionDAO.addAplicacion(v)) {
            int idAsignado = AplicacionDAO.findByNombre(v.getNombre()).get(0).getId();
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT_SPECIFIC)) {
                ps.setInt(1, idAsignado);
                ps.setBoolean(2, v.isMultijugador());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RuntimeException("Error al insertar subtipo Videojuego: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Actualiza un videojuego en la base de datos
     * @param v videojuego elegido
     * @return videojuego actualizado
     */
    public static boolean updateVideojuego(Videojuego v) {
        // 1. Actualizamos nombre, precio, etc., en la tabla 'aplicacion'
        if (AplicacionDAO.updateAplicacion(v)) {
            // 2. Actualizamos el campo específico en 'videojuego'
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE_SPECIFIC)) {
                ps.setBoolean(1, v.isMultijugador());
                ps.setInt(2, v.getId());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar subtipo Videojuego: " + e.getMessage());
            }
        }
        return false;
    }
}