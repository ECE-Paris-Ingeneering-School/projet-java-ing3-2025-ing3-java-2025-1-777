package Utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire pour obtenir la connexion à la base de données via JDBC.
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:8889/Shopping?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";       // à adapter selon votre configuration
    private static final String PASSWORD = "root";   // à adapter selon votre configuration

    /**
     * Retourne une connexion à la base de données.
     * @return Connection JDBC
     * @throws SQLException en cas d’erreur de connexion
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
