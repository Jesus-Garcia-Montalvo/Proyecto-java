package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    String bd = "festividades";
    String URL = "jdbc:mysql://localhost:3306/" + bd;
    String USUARIO = "root";
    String CONTRASENA = "";
    String Driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;

    public Conexion() {
    }

    public Connection conectar() {
        try {
            Class.forName(Driver);
            cx = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conectado con la base de datos");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo conectar con la base de datos");
            return null;
        }
        return cx;
    }

    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        Connection cx = conexion.conectar();

        if (cx != null) {
            System.out.println("La conexión está funcionando correctamente");
        } else {
            System.out.println("La conexión no está funcionando correctamente");
        }
    }
}
