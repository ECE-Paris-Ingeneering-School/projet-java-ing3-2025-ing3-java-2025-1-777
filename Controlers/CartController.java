package Controlers;

import DAO.DiscountDAO;
import DAO.DiscountDAOImpl;
import model.Article;
import model.Panier;
import java.util.Map;  // Import ajouté ici

public class CartController {
    private Panier panier;
    private int userId;
    private DiscountDAO discountDAO;

    public CartController(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("ID utilisateur invalide: " + userId);
        }
        this.userId = userId;
        this.discountDAO = new DiscountDAOImpl(); // Une seule instance
        this.panier = new Panier(userId, discountDAO); // Injection du DAO
        System.out.println("Nouveau CartController pour userID: " + userId); // Debug
    }


    public boolean ajouterAuPanier(Article article, int quantite) {
        if (article == null || quantite <= 0 || article.getStock() < quantite) {
            return false;
        }
        panier.ajouterArticle(article, quantite);
        return true;
    }

    public Panier getPanier() {
        return panier;
    }

    // Dans Controlers/CartController.java
    public void supprimerArticle(Article article) {
        panier.supprimerArticle(article);
    }

    public void viderPanier() {
        panier.getArticles().clear();
    }

    public double getTotalAvecRemises() {
        return panier.calculerTotal();
    }

    public boolean isStockAvailable() {
        for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
            if (entry.getKey().getStock() < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public String getOrderSummary() {
        StringBuilder summary = new StringBuilder();
        for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
            summary.append(entry.getKey().getNom())
                    .append(" x ")
                    .append(entry.getValue())
                    .append("\n");
        }
        summary.append("\nTotal: ")
                .append(String.format("%.2f €", panier.calculerTotal()));
        return summary.toString();
    }
}



