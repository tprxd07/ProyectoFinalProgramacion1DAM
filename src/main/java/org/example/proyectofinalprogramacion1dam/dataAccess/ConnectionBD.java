package org.example.proyectofinalprogramacion1dam.dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
    private static final String FILE = "connection.xml";
    private static Connection con;
    //1. crear una instancia de la propia clase
    private static ConnectionBD _instance;

    //2. Constructor privado
    private ConnectionBD() {
        //leo del connection.xml los datos para la conexion
        ConnectionProperties properties = XMLManager.readXML(new ConnectionProperties(), FILE);
        try{
            con = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
        }catch(SQLException e){
            e.printStackTrace();
            con=null;
        }
    }

    //3. metodo publico que me devuelve la instancia ya creada, si la primera vez la crea

    public static Connection getConnection() {
        if(_instance==null){
            _instance = new ConnectionBD();
        }
        return con;
    }
}