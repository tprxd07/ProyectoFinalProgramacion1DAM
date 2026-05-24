package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.Aplicacion;
import org.example.proyectofinalprogramacion1dam.model.Utilidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO encargada del subtipo de aplicaciones Utilidad
 */
public class UtilidadDAO {
    private final static String SQL_ALL_U = "SELECT idApp FROM utilidades";
    private final static String SQL_CHECK_U = "SELECT idApp FROM utilidades WHERE idApp = ?";
    private final static String SQL_INSERT_SPECIFIC = "INSERT INTO utilidades (idApp) VALUES (?)";

    /**
     * Encuentra todas las apps en la base de datos. Estas heredan de aplicacion, por lo que solo reciben Id
     * @return Lista de las apps con el subtipo utilidad
     */
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
                            base.getDescargas(),
                            base.getCategoria(),
                            base.getIdDesarrollador(),
                            base.getImagen()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar utilidades: " + e.getMessage());
        }
        return utilidades;
    }

    /**
     * Busca apps con el subtipo utilidad por su Id
     * @param id id de la app
     * @return Objeto utilidad
     */
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
                        base.getDescargas(),
                        base.getCategoria(),
                        base.getIdDesarrollador(),
                        base.getImagen()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar utilidad por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Añade una aplicacion con el subtipo utilidad
     * @param u Objeto utilidad
     */
    public static void addUtilidad(Utilidad u) {
        if (u == null) return;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT_SPECIFIC)) {
            ps.setInt(1, u.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar en la tabla utilidades: " + e.getMessage());
        }
    }

    /**
     * Actualiza los campos base en una aplicacion
     * @param u Objeto utilidad
     */
    public static void updateUtilidad(Utilidad u) {
        AplicacionDAO.updateAplicacion(u);
        //Como la tabla utilidades solo contiene 'idApp' como clave primaria y foranea, un update en aplicaciones basta.
    }
}