package Controlers;

import view.LoginFrame;
import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe principale pour lancer l'application.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:8889/Shopping?useSSL=false&serverTimezone=UTC",
                    "root",
                    "root"
            );
            System.out.println("Connexion réussie !");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
