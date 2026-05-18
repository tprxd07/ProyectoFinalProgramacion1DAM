package org.example.proyectofinalprogramacion1dam.modelDAO;

import org.example.proyectofinalprogramacion1dam.dataAccess.ConnectionBD;
import org.example.proyectofinalprogramacion1dam.model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final static String SQL_ALL = "SELECT * FROM usuario";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM usuario WHERE id = ?";
    private final static String SQL_FIND_BY_EMAIL = "SELECT * FROM usuario WHERE email = ?";
    private final static String SQL_FIND_BY_USERNAME = "SELECT * FROM usuario WHERE nombreUsuario = ?";
    private final static String SQL_LOGIN = "SELECT * FROM usuario WHERE email = ? AND contrasenia = ?";
    private final static String SQL_INSERT = "INSERT INTO usuario (nombreUsuario, email, contrasenia) VALUES (?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE usuario SET nombreUsuario = ?, email = ?, contrasenia = ?, saldo = ? WHERE id = ?";
    private final static String SQL_DELETE ="DELETE FROM usuario WHERE id = ?";

    /**
     * Devuelve una lista con todos los usuarios de la base de datos.
     * @return lista de usuarios
     */
    public static List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        try (ResultSet rs = ConnectionBD.getConnection().createStatement().executeQuery(SQL_ALL)) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombreUsuario"),
                        rs.getString("email"),
                        rs.getString("contrasenia"),
                        rs.getDouble("saldo")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    /**
     * Busca un usuario por su ID.
     * @param idUsuario Id del usuario
     * @return El objeto Usuario o null si no existe.
     */
    public static Usuario findById(int idUsuario) {
        Usuario usuario = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombreUsuario"),
                        rs.getString("email"),
                        rs.getString("contrasenia"),
                        rs.getDouble("saldo")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    /**
     * Busca un usuario por su Email
     * @param email correo electronico del usuario
     * @return El objeto Usuario o null si no existe.
     */
    public static Usuario findByEmail(String email) {
        Usuario usuario = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_EMAIL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombreUsuario"),
                        rs.getString("email"),
                        rs.getString("contrasenia"),
                        rs.getDouble("saldo")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    /**
     * Busca un usuario por su nombre de usuario (Nickname).
     * @param username El nombre de usuario a buscar.
     * @return El objeto Usuario si lo encuentra, null si no existe.
     */
    public static Usuario findByUsername(String username) {
        Usuario usuario = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_FIND_BY_USERNAME)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombreUsuario"),
                        rs.getString("email"),
                        rs.getString("contrasenia"),
                        rs.getDouble("saldo")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    /**
     * Metodo para validar el login.
     * @param email correo electronico
     * @param pass contraseña
     * @return El objeto Usuario si las credenciales son correctas, null si no.
     */
    public static Usuario login(String email, String pass) {
        Usuario usuario = null;
        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_LOGIN)) {
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombreUsuario"),
                        rs.getString("email"),
                        rs.getString("contrasenia"),
                        rs.getDouble("saldo")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }
    /**
     * Actualiza los datos de un usuario existente.
     * Permite cambiar nombreUsuario, email, contrasenia o saldo.
     * @param user Objeto usuario con los datos actualizados.
     * @return true si la actualización fue exitosa.
     */
    public static boolean updateUsuario(Usuario user) {
        if (user == null || findById(user.getId()) == null) {
            return false;
        }

        try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_UPDATE)) {
            ps.setString(1, user.getNombreUsuario());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getContrasenia());
            ps.setDouble(4, user.getSaldo());
            ps.setInt(5, user.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * @param user Objeto usuario con los datos de registro.
     * @return true si se ha insertado correctamente.
     */
    public static boolean addUsuario(Usuario user) {
        boolean added = false;
        if (user != null && findByEmail(user.getEmail()) == null) {
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, user.getNombreUsuario());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getContrasenia());
                ps.executeUpdate();
                added = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return added;
    }

    public static boolean deleteUser(int id){
        if (findById(id) != null){
            try (PreparedStatement ps = ConnectionBD.getConnection().prepareStatement(SQL_DELETE)){
                ps.setInt(1,id);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}