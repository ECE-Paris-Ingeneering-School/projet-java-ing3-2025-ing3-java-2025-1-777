package Controlers;

// Importation des classes nécessaires pour gérer les remises et les articles
import DAO.DiscountDAO;
import DAO.DiscountDAOImpl;
import model.Article;
import model.Panier;
import java.util.Map;  // Import ajouté ici, nécessaire pour gérer les entrées de panier

public class CartController {
    // Déclaration des variables membres
    private Panier panier; // Instance de la classe Panier, représentant le panier de l'utilisateur
    private int userId; // Identifiant de l'utilisateur associé au panier
    private DiscountDAO discountDAO; // Interface pour l'accès aux données de remises

    // Constructeur de CartController
    public CartController(int userId) {
        // Vérifie si l'ID de l'utilisateur est valide
        if (userId <= 0) {
            // Si l'ID est invalide, lance une exception
            throw new IllegalArgumentException("ID utilisateur invalide: " + userId);
        }
        this.userId = userId; // Assigne l'ID utilisateur à la variable membre
        this.discountDAO = new DiscountDAOImpl(); // Crée une instance de DiscountDAOImpl pour gérer les remises
        this.panier = new Panier(userId, discountDAO); // Crée un panier pour l'utilisateur avec l'ID et le DiscountDAO
        // Message de débogage pour vérifier la création du CartController
        System.out.println("Nouveau CartController pour userID: " + userId);
    }

    // Méthode pour ajouter un article au panier
    public boolean ajouterAuPanier(Article article, int quantite) {
        // Vérifie si l'article est valide et si la quantité demandée est disponible en stock
        if (article == null || quantite <= 0 || article.getStock() < quantite) {
            return false; // Si l'article est invalide ou si la quantité est insuffisante, retourne false
        }
        panier.ajouterArticle(article, quantite); // Si tout est valide, ajoute l'article au panier
        return true; // Retourne true pour indiquer que l'ajout a réussi
    }

    // Méthode pour obtenir le panier actuel
    public Panier getPanier() {
        return panier; // Retourne l'objet Panier
    }

    // Méthode pour supprimer un article du panier
    public void supprimerArticle(Article article) {
        panier.supprimerArticle(article); // Appelle la méthode pour supprimer l'article du panier
    }

    // Méthode pour vider le panier
    public void viderPanier() {
        panier.getArticles().clear(); // Efface tous les articles dans le panier
    }

    // Méthode pour obtenir le total avec remises appliquées
    public double getTotalAvecRemises() {
        return panier.calculerTotal(); // Calcule le total des articles dans le panier en appliquant les remises
    }

    // Méthode pour vérifier si le stock est disponible pour tous les articles du panier
    public boolean isStockAvailable() {
        // Parcourt tous les articles du panier et vérifie si la quantité demandée est disponible
        for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
            // Si le stock d'un article est insuffisant, retourne false
            if (entry.getKey().getStock() < entry.getValue()) {
                return false;
            }
        }
        return true; // Si tous les articles ont suffisamment de stock, retourne true
    }

    // Méthode pour obtenir un résumé de la commande sous forme de texte
    public String getOrderSummary() {
        StringBuilder summary = new StringBuilder(); // Utilise StringBuilder pour concaténer efficacement les chaînes de caractères
        // Parcourt chaque article du panier et ajoute son nom et sa quantité au résumé
        for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
            summary.append(entry.getKey().getNom()) // Nom de l'article
                    .append(" x ")
                    .append(entry.getValue()) // Quantité de l'article
                    .append("\n"); // Ajoute un saut de ligne après chaque article
        }
        summary.append("\nTotal: ")
                .append(String.format("%.2f €", panier.calculerTotal())); // Ajoute le total avec deux décimales
        return summary.toString(); // Retourne le résumé sous forme de chaîne de caractères
    }
}
