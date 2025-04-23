package Utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire pour obtenir la connexion à la base de données via JDBC.
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:8889/Shopping?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";       
    private static final String PASSWORD = "root";   

    /**
     * Retourne une connexion à la base de données.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

