package Controlers;

import model.Article;
import model.Panier;

public class CartController {
    private Panier panier;
    private int userId;

    public CartController(int userId) {
        this.userId = userId;
        this.panier = new Panier(userId);
    }


    private static final CartController instance = new CartController(0);
    public static CartController getInstance() {
               return instance;
    }

    public boolean ajouterAuPanier(Article article, int quantite) {
        if (article == null || quantite <= 0) return false;

        panier.getArticles().merge(article, quantite, Integer::sum);
        return true;
    }

    public Panier getPanier() {
        return panier;
    }


    public void supprimerArticle(Article article) {
        panier.supprimerArticle(article);
    }

    public void viderPanier() {
        panier.getArticles().clear();
    }
}