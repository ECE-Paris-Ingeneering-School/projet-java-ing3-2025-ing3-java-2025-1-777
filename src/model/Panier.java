package model;

import Controlers.ShoppingController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modèle du panier
 */
public class Panier {
    private final int userId;
    private final Map<Article, Integer> articles = new HashMap<>();
    /**
     * méthode qui initialise un nouveau panier pour l'utilisateur.
     */
    public Panier(int userId) {
        this.userId = userId;
    }
    /**
     * méthode qui permet d'ajouter une quantité d'un article au panier
     */
    public void ajouterArticle(Article article, int quantite) {
        articles.merge(article, quantite, Integer::sum);
    }
    /**
     * méthode qui permet de supprimer un article du panier.
     */
    public void supprimerArticle(Article article) {
        articles.remove(article);
    }
    /**
     * méthode qui permet de vider le panier.
     */
    public void clear() {
        articles.clear();
    }
/**
 * Retourne la map Article→Quantité dans le panier.
 */
    public Map<Article,Integer> getArticles() {
        return articles;
    }
    /**
     * Retourne l'identifiant de l'utilisateur
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Construit une liste de CartItem à partir de la Map interne.
     */
    public List<CartItem> getItems() {
        List<CartItem> items = new ArrayList<>();
        for (Map.Entry<Article,Integer> e : articles.entrySet()) {
            Article art = e.getKey();
            int qty = e.getValue();
            items.add(new CartItem(userId, art.getIdArticle(), null, qty));
        }
        return items;
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

    /** 20% de TVA sur le total HT. */
    public double calculerTVA() {
        return calculerTotalHT() * 0.20;
    }

    /** Total TTC = HT + TVA. */
    public double calculerTotalTTC() {
        return calculerTotalHT() + calculerTVA();
    }
}