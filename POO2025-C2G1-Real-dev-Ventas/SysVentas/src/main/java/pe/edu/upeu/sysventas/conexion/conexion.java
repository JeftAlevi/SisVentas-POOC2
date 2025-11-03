package pe.edu.upeu.sysventas.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class conexion {

    private static final String URL = "jdbc:sqlite:./data/sysventas.db"; // ruta a tu DB
    private static Connection conn = null;

    // Método para obtener la conexión
    public static Connection getConexion() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL);
                System.out.println("Conexión exitosa a SQLite");

                // Crear tabla clientes si no existe
                String sql = "CREATE TABLE IF NOT EXISTS clientes ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "nombre TEXT NOT NULL,"
                        + "apellidos TEXT NOT NULL,"
                        + "direccion TEXT,"
                        + "dni TEXT,"
                        + "telefono TEXT"
                        + ");";
                Statement st = conn.createStatement();
                st.execute(sql);

            } catch (SQLException e) {
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return conn;
    }
}
