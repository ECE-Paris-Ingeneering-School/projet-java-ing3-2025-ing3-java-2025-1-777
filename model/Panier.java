package model;

import java.util.HashMap;
import java.util.Map;

public class Panier {
    private Map<Article, Integer> articles; 
    private int userId; 

    public Panier(int userId) {
        this.userId = userId;
        this.articles = new HashMap<>();
    }

    public void ajouterArticle(Article article, int quantite) {
        articles.merge(article, quantite, Integer::sum);
    }

    public void supprimerArticle(Article article) {
        articles.remove(article);
    }

    public Map<Article, Integer> getArticles() {
        return articles;
    }

    public double calculerTotal() {
        return articles.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrixUnitaire() * e.getValue())
                .sum();
    }

    public int getUserId() {
        return userId;
    }
}
