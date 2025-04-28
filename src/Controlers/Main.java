package Controlers;

import view.LoginFrame;
import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe principale pour lancer l'application Shopping.
 * Cette classe contient le point d'entrée de l'application et gère la connexion à la base de données.
 */
public class Main {
    /**
     * Point d'entrée de l'application. Lance la fenêtre de connexion et établit la connexion à la base de données.
     *
     * @param args Les arguments de ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        // Lance la fenêtre de connexion sur le thread de l'interface utilisateur.
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });

        try {
            // Charge le driver JDBC pour MySQL.
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Établit une connexion à la base de données Shopping.
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Shopping?useSSL=false&serverTimezone=UTC",
                    "", // Utilisateur (vide ici, à configurer selon la base de données)
                    ""  // Mot de passe (vide ici, à configurer selon la base de données)
            );
            // Affiche un message pour indiquer que la connexion à la base de données a réussi.
            System.out.println("La connexion réussie !");
            // Ferme la connexion une fois la vérification effectuée.
            conn.close();
        } catch (Exception e) {
            // Capture et affiche les exceptions éventuelles liées à la connexion ou au chargement du driver.
            e.printStackTrace();
        }
    }
}
