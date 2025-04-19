// model/Panier.java
package model;

import java.util.HashMap;
import java.util.Map;
import util.BulkDiscountCalculator;
import util.DiscountApplier;
import DAO.DiscountDAO;
import DAO.DiscountDAOImpl;

public class Panier {
    private Map<Article, Integer> articles;
    private int userId;
    private DiscountDAO discountDAO;

    // OU mieux : injection par constructeur
    public Panier(int userId, DiscountDAO discountDAO) {
        this.userId = userId;
        this.articles = new HashMap<>();
        this.discountDAO = discountDAO;
    }

    public void ajouterArticle(Article article, int quantite) {
        articles.merge(article, quantite, Integer::sum);
    }

    public void supprimerArticle(Article article) {
        articles.remove(article);
    }

    public Map<Article, Integer> getArticles() {
        return new HashMap<>(articles);
    }

    public double calculerTotal() {
        return articles.entrySet().stream()
                .mapToDouble(e -> calculerPrixArticle(e.getKey(), e.getValue()))
                .sum();
    }

    public double calculerPrixArticle(Article article, int quantity) {
        // 1. Calcul du prix bulk
        double prix = BulkDiscountCalculator.calculateBulkPrice(
                quantity,
                article.getPrixUnitaire(),
                article.getPrixBulk(),
                article.getQuantiteBulk()
        );

        // 2. Application des discounts supplémentaires
        Discount discount = discountDAO.getDiscountForArticle(article.getIdArticle());
        if (discount != null) {
            prix = DiscountApplier.applyDiscount(prix, discount);
        }

        return prix;
    }

    public int getUserId() {
        return userId;
    }

    public void viderPanier() {
        articles.clear();
    }

    public void setDiscountDAO(DiscountDAO discountDAO) {
        this.discountDAO = discountDAO;
    }
}