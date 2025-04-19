package Controlers;

import DAO.DiscountDAO;
import DAO.DiscountDAOImpl;
import model.Article;
import model.Panier;

public class CartController {
    private Panier panier;
    private int userId;
    private DiscountDAO discountDAO;

    public CartController(int userId) {
        this.userId = userId;
        this.panier = new Panier(userId, new DiscountDAOImpl());
        this.discountDAO = new DiscountDAOImpl();
    }

    // singleton par défaut
    private static final CartController instance = new CartController(0);
    public static CartController getInstance() {
               return instance;
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
}



