package Controlers;

import DAO.ArticleDAO;
import DAO.ArticleDAOImpl;
import DAO.UtilisateurDAO;
import DAO.UtilisateurDAOImpl;
import model.Article;
import model.Utilisateur;
import java.util.List;
import util.DiscountApplier;
import util.BulkDiscountCalculator;
import model.Discount;
import DAO.DiscountDAO;
import DAO.DiscountDAOImpl;

/**
 * Contrôleur principal de l’application Shopping.
 */
public class ShoppingController {
    private Utilisateur currentUser;
    private UtilisateurDAO utilisateurDAO;
    private ArticleDAO articleDAO;
    private CartController cartController;
    private static ShoppingController instance;
    private int currentUserId;
    private DiscountDAO discountDAO;


    public ShoppingController() {
        this.utilisateurDAO = new UtilisateurDAOImpl();
        this.articleDAO = new ArticleDAOImpl();
        this.discountDAO = new DiscountDAOImpl(); // Initialisation ici
    }

    public CartController getCartController() {
        if (cartController == null) {
            throw new IllegalStateException("Aucun utilisateur connecté");
        }
        return cartController;
    }

    public static ShoppingController getInstance() {
        if (instance == null) {
            instance = new ShoppingController();
        }
        return instance;
    }



    // Dans ShoppingController.java
    public void initializePanier(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("ID utilisateur invalide");
        }
        this.cartController = new CartController(userId);
        System.out.println("Panier initialisé pour userID: " + userId); // Debug
    }

    public List<Article> getCatalogue() {
        return articleDAO.findAll();
    }

//    public double calculerPrixArticle(Article article, int quantite) {
//        double prixTotal = 0.0;
//        int quantiteBulk = article.getQuantiteBulk();
//        if (quantite >= quantiteBulk) {
//            int nbBulk = quantite / quantiteBulk;
//            int reste = quantite % quantiteBulk;
//            prixTotal = nbBulk * article.getPrixBulk() + reste * article.getPrixUnitaire();
//        } else {
//            prixTotal = quantite * article.getPrixUnitaire();
//        }
//        return prixTotal;
//    }

    public Utilisateur login(String email, String password) {
        this.currentUser = utilisateurDAO.findByEmailAndPassword(email, password);
        if (currentUser != null) {
            this.cartController = new CartController(currentUser.getIdUtilisateur());
        }
        return currentUser;
    }

    public void setCurrentUser(Utilisateur user) {
        this.currentUser = user;
        this.cartController = new CartController(user.getIdUtilisateur());
    }

    public Utilisateur getCurrentUser() {
        return currentUser;
    }

    public boolean register(Utilisateur user) {
        if (user == null) return false;

        if (utilisateurDAO.emailExists(user.getEmail())) {
            return false;
        }

        // Mot de passe non hashé (temporaire pour démo)
        user.setMotDePasse(hashPassword(user.getMotDePasse()));

        if (user.getRole() == null) {
            user.setRole("client");
        }

        return utilisateurDAO.insert(user);
    }

    private String hashPassword(String plainPassword) {
        // Pas de hash pour l’instant
        return plainPassword;
    }

    // Controlers/ShoppingController.java
    public double calculerPrixArticle(Article article, int quantite) {
        // 1. Calcul du prix bulk
        double prix = BulkDiscountCalculator.calculateBulkPrice(
                quantite,
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




    public boolean isUserLoggedIn() {
        return this.currentUser != null && this.cartController != null;
    }



}
