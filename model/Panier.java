package model;

import Controlers.ShoppingController;

import java.util.HashMap;
import java.util.Map;

public class Panier {
    private final int userId;
    private final Map<Article, Integer> articles = new HashMap<>();

    public Panier(int userId) {
        this.userId = userId;
    }

    public void ajouterArticle(Article article, int quantite) {
        articles.merge(article, quantite, Integer::sum);
    }

    public void supprimerArticle(Article article) {
        articles.remove(article);
    }

    public void clear() {
        articles.clear();
    }

    public Map<Article, Integer> getArticles() {
        return articles;
    }

    public int getUserId() {
        return userId;
    }

    /**
     * Calcule le total HT en appliquant le prix bulk et unitaire.
     */
    public double calculerTotalHT() {
        ShoppingController shop = ShoppingController.getInstance();
        return articles.entrySet().stream()
                .mapToDouble(e -> shop.calculerPrixArticle(e.getKey(), e.getValue()))
                .sum();
    }

    
    public double calculerTVA() {
        return calculerTotalHT() * 0.20;
    }

    
    public double calculerTotalTTC() {
        return calculerTotalHT() + calculerTVA();
    }
}
