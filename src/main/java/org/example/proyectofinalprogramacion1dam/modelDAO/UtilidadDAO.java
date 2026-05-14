package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;
import org.example.proyectofinalprogramacion1dam.model.Utilidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilidadDAO {
    private final static String SQL_ALL_U = "SELECT idApp FROM utilidades";
    private final static String SQL_CHECK_U = "SELECT idApp FROM utilidades WHERE idApp = ?";
    private final static String SQL_INSERT_SPECIFIC = "INSERT INTO utilidades (idApp) VALUES (?)";

    public static List<Utilidad> findAll() {
        List<Utilidad> utilidades = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL_U)) {
            while (rs.next()) {
                int id = rs.getInt("idApp");
                Aplicacion base = AplicacionDAO.findById(id);
                if (base != null) {
                    utilidades.add(new Utilidad(
                            base.getId(),
                            base.getNombre(),
                            base.getDescripcion(),
                            base.getPrecio(),
                            base.getVersion(),
                            base.getDescargas(),
                            base.getCategoria(),
                            base.getIdDesarrollador()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar utilidades: " + e.getMessage());
        }
        return utilidades;
    }

    public static Utilidad findById(int id) {
        Aplicacion base = AplicacionDAO.findById(id);
        if (base == null) return null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_CHECK_U)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Utilidad(
                        base.getId(),
                        base.getNombre(),
                        base.getDescripcion(),
                        base.getPrecio(),
                        base.getVersion(),
                        base.getDescargas(),
                        base.getCategoria(),
                        base.getIdDesarrollador()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar utilidad por ID: " + e.getMessage());
        }
        return null;
    }

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