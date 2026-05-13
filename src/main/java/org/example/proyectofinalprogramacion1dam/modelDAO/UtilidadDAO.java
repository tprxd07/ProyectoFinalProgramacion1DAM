package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.Utilidad;
import org.example.proyectofinalprogramacion1dam.model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilidadDAO {
    private final static String SQL_ALL_JOIN = "SELECT a.* FROM aplicacion a JOIN utilidades u ON a.id = u.idApp";
    private final static String SQL_FIND_BY_ID = "SELECT a.* FROM aplicacion a JOIN utilidades u ON a.id = u.idApp WHERE a.id = ?";
    private final static String SQL_INSERT_SPECIFIC = "INSERT INTO utilidades (idApp) VALUES (?)";

    /**
     * Recupera todas las apps utilidades de la base de datos.
     */
    public static List<Utilidad> findAll() {
        List<Utilidad> utilidades = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL_JOIN)) {
            while (rs.next()) {
                // Creamos objetos Utilidad usando los datos heredados
                utilidades.add(new Utilidad(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("version"),
                        rs.getInt("descargas"),
                        Categoria.valueOf(rs.getString("categoria")),
                        rs.getInt("idDesarrollador")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar utilidades: " + e.getMessage());
        }
        return utilidades;
    }

    /**
     * Busca una app utilidad por su id
     * @param id de la app
     * @return apps utilidades con ese id
     */
    public static Utilidad findById(int id) {
        Utilidad utilidad = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                utilidad = new Utilidad(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("version"),
                        rs.getInt("descargas"),
                        Categoria.valueOf(rs.getString("categoria")),
                        rs.getInt("idDesarrollador")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar utilidad por ID: " + e.getMessage());
        }
        return utilidad;
    }

    /**
     * Inserta una app utilidad nueva a la base de datos
     * @param u La nueva app utilidad
     * @return app insertada
     */
    public static boolean addUtilidad(Utilidad u) {
        if (AplicacionDAO.addAplicacion(u)) {
            int idAsignado = AplicacionDAO.findByNombre(u.getNombre()).get(0).getId();
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT_SPECIFIC)) {
                ps.setInt(1, idAsignado);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RuntimeException("Error al insertar en la tabla utilidades: " + e.getMessage());
            }
        }
        return false;
    }
}