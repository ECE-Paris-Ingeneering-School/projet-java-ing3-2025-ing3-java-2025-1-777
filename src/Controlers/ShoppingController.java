package Controlers;

import DAO.*;
import model.Article;
import model.Discount;
import model.Utilisateur;

import java.util.List;

public class ShoppingController {
    private static ShoppingController instance;
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();
    private final ArticleDAO articleDAO = new ArticleDAOImpl();
    private CartController cartController;
    private Utilisateur currentUser;

    private ShoppingController() {}

    public static ShoppingController getInstance() {
        if (instance == null) instance = new ShoppingController();
        return instance;
    }

    public Utilisateur login(String email, String password) {
        currentUser = utilisateurDAO.findByEmailAndPassword(email, password);
        if (currentUser != null) {
            cartController = new CartController(currentUser.getIdUtilisateur());
        }
        return currentUser;
    }

    /** Retourne le panier (après login). */
    public CartController getCartController() {
        if (cartController == null) {
            // user non connecté → panier vide avec id 0
            cartController = new CartController(0);
        }
        return cartController;
    }

    public List<Article> getCatalogue() {
        return articleDAO.findAll();
    }

    /** Calcul prix avec bulk */
    public double calculerPrixArticle(Article article, int quantite) {
        double pu = article.getPrixUnitaire();
        int seuil = article.getQuantiteBulk();
        if (quantite >= seuil) {
            double tauxRemise = article.getPrixBulk() / 100.0;
            return quantite * pu * (1 - tauxRemise);
        } else {
            return quantite * pu;
        }
    }

    /** Utilisateur courant */
    public Utilisateur getCurrentUser() {
        return currentUser;
    }
    // src/Controlers/ShoppingController.java
    public double calculerPrixArticleAvecRemise(Article art,int q){
        double total = calculerPrixArticle(art,q);
        Discount promo = new DiscountDAOImpl().findByArticle(art.getIdArticle());
        if(promo!=null){
            total = total * (1 - promo.getTaux()/100.0);
        }
        return total;
    }

}