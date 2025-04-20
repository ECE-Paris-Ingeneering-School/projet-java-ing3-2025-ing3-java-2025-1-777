package Controlers;

import DAO.ArticleDAO;
import DAO.ArticleDAOImpl;
import DAO.UtilisateurDAO;
import DAO.UtilisateurDAOImpl;
import model.Article;
import model.Utilisateur;
import java.util.List;


public class ShoppingController {
    private Utilisateur currentUser;
    private UtilisateurDAO utilisateurDAO;
    private ArticleDAO articleDAO;
    private static CartController cartController;
    private static ShoppingController instance;
    private int currentUserId;

    public ShoppingController() {
        this.utilisateurDAO = new UtilisateurDAOImpl();
        this.articleDAO = new ArticleDAOImpl();
    }

    public CartController getCartController() {
        if (cartController == null) {
            throw new IllegalStateException("Connectez-vous d'abord.");
        }
        return cartController;
    }

    public static ShoppingController getInstance() {
        if (instance == null) {
            instance = new ShoppingController();
        }
        return instance;
    }

    public void initializePanier(int userId) {
        this.cartController = new CartController(userId);
    }

    public List<Article> getCatalogue() {
        return articleDAO.findAll();
    }

    public double calculerPrixArticle(Article article, int quantite) {
        double prixTotal = 0.0;
        int quantiteBulk = article.getQuantiteBulk();
        if (quantite >= quantiteBulk) {
            int nbBulk = quantite / quantiteBulk;
            int reste = quantite % quantiteBulk;
            prixTotal = nbBulk * article.getPrixBulk() + reste * article.getPrixUnitaire();
        } else {
            prixTotal = quantite * article.getPrixUnitaire();
        }
        return prixTotal;
    }

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


        user.setMotDePasse(hashPassword(user.getMotDePasse()));

        if (user.getRole() == null) {
            user.setRole("client");
        }

        return utilisateurDAO.insert(user);
    }

    private String hashPassword(String plainPassword) {

        return plainPassword;
    }
}