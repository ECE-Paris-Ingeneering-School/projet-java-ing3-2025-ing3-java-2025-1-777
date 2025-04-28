package Controlers;

import DAO.*;
import model.Article;
import model.Utilisateur;

import java.util.List;
/**
 * Contrôleur principal de l'application gérant l'authentification,
 * l'accès au catalogue et au panier utilisateur.
 */
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
/** methode qui retourne l'utilisateur*/
    public Utilisateur login(String email, String password) {
        currentUser = utilisateurDAO.findByEmailAndPassword(email, password);
        if (currentUser != null) {
            cartController = new CartController(currentUser.getIdUtilisateur());
        }
        return currentUser;
    }

    /** methode qui Retourne le panier (après login). */
    public CartController getCartController() {
        if (cartController == null) {
            cartController = new CartController(0);
        }
        return cartController;
    }
/** methode qui retourne la liste des produits */
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

    public Utilisateur getCurrentUser() {
        return currentUser;
    }


}