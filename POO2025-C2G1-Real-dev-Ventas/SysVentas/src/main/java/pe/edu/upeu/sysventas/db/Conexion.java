package pe.edu.upeu.sysventas.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:sqlite:data/categoriadb.db"; // tu archivo real
    private static Connection conn = null;

    public static Connection getConexion() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
                System.out.println("✅ Conexión establecida con la base de datos SQLite.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión a la base de datos:");
            e.printStackTrace();
        }
        return conn;
    }
}
