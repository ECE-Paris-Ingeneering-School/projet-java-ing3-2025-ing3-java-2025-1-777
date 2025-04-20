package util;

import java.sql.Connection; // Importation de la classe Connection pour gérer les connexions à la base de données
import java.sql.DriverManager; // Importation de la classe DriverManager pour obtenir une connexion à la base de données
import java.sql.SQLException; // Importation de la classe SQLException pour gérer les exceptions liées à la base de données

/**
 * Classe utilitaire pour gérer la connexion à la base de données.
 * Cette classe permet de se connecter à une base de données MySQL en utilisant JDBC.
 */
public class DBConnection {
    // URL de connexion à la base de données MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/shopping?useSSL=false&serverTimezone=UTC";
    // Nom d'utilisateur pour se connecter à la base de données
    private static final String USER = "root";
    // Mot de passe pour se connecter à la base de données (par défaut pour Wamp : vide)
    private static final String PASSWORD = "";  // Mot de passe Wamp par défaut

    /**
     * Méthode pour obtenir une connexion à la base de données.
     * Utilise le JDBC pour se connecter à la base de données MySQL avec les informations d'URL, d'utilisateur et de mot de passe spécifiées.
     * @return Une connexion à la base de données.
     * @throws SQLException Si une erreur se produit lors de la connexion à la base de données.
     */
    public static Connection getConnection() throws SQLException {
        // Retourne la connexion à la base de données via DriverManager
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Méthode principale pour tester la connexion à la base de données.
     * Essaie d'établir une connexion et affiche un message de succès ou d'erreur selon le résultat.
     * @param args Paramètres de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        // Bloc try-with-resources pour s'assurer que la connexion est fermée automatiquement
        try (Connection conn = getConnection()) {
            // Si la connexion réussit, affiche un message de succès
            System.out.println("Connexion réussie à la base Shopping !");
        } catch (SQLException e) {
            // Si une erreur de connexion se produit, affiche le message d'erreur
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
