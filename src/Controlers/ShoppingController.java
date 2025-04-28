package Controlers;

import DAO.*;
import model.Article;
import model.Discount;
import model.Utilisateur;

import java.util.List;

/**
 * Contrôleur principal pour gérer les actions de l'utilisateur dans l'application de shopping.
 * Cette classe gère les connexions des utilisateurs, l'accès au catalogue d'articles, et le calcul des prix des articles.
 */
public class ShoppingController {
    // Instance unique de la classe (Singleton).
    private static ShoppingController instance;

    // Interfaces pour interagir avec les données des utilisateurs et des articles.
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();
    private final ArticleDAO articleDAO = new ArticleDAOImpl();

    // Contrôleur du panier, associé à un utilisateur.
    private CartController cartController;

    // Utilisateur actuellement connecté.
    private Utilisateur currentUser;

    /**
     * Constructeur privé pour implémenter le design pattern Singleton.
     * Le constructeur est privé pour empêcher la création multiple d'instances de cette classe.
     */
    private ShoppingController() {}

    /**
     * Retourne l'instance unique du contrôleur.
     *
     * @return L'instance unique de ShoppingController.
     */
    public static ShoppingController getInstance() {
        if (instance == null) instance = new ShoppingController();
        return instance;
    }

    /**
     * Authentifie l'utilisateur en fonction de son email et mot de passe.
     * Si l'utilisateur est trouvé, initialise le contrôleur du panier.
     *
     * @param email    L'email de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @return L'utilisateur authentifié, ou null si l'authentification a échoué.
     */
    public Utilisateur login(String email, String password) {
        currentUser = utilisateurDAO.findByEmailAndPassword(email, password);
        if (currentUser != null) {
            // Si l'utilisateur est connecté, initialise son panier.
            cartController = new CartController(currentUser.getIdUtilisateur());
        }
        return currentUser;
    }

    /**
     * Retourne le contrôleur du panier associé à l'utilisateur courant.
     * Si l'utilisateur n'est pas connecté, crée un panier vide avec l'ID 0.
     *
     * @return Le contrôleur du panier.
     */
    public CartController getCartController() {
        if (cartController == null) {
            // Si l'utilisateur n'est pas connecté, un panier vide est créé.
            cartController = new CartController(0);
        }
        return cartController;
    }

    /**
     * Retourne le catalogue complet des articles disponibles dans l'application.
     *
     * @return Une liste d'articles.
     */
    public List<Article> getCatalogue() {
        return articleDAO.findAll();
    }

    /**
     * Calcule le prix d'un article en tenant compte des remises pour les quantités bulk.
     *
     * @param article  L'article pour lequel le prix est calculé.
     * @param quantite La quantité d'articles à acheter.
     * @return Le prix total pour la quantité d'articles donnée, en tenant compte des remises bulk.
     */
    public double calculerPrixArticle(Article article, int quantite) {
        double pu = article.getPrixUnitaire(); // Prix unitaire de l'article.
        int seuil = article.getQuantiteBulk(); // Seuil de quantité pour appliquer le prix bulk.
        if (quantite >= seuil) {
            // Si la quantité dépasse le seuil, appliquer le prix en bulk.
            double tauxRemise = article.getPrixBulk() / 100.0;
            return quantite * pu * (1 - tauxRemise);
        } else {
            // Sinon, utiliser le prix normal.
            return quantite * pu;
        }
    }

    /**
     * Retourne l'utilisateur actuellement connecté.
     *
     * @return L'utilisateur connecté.
     */
    public Utilisateur getCurrentUser() {
        return currentUser;
    }

    /**
     * Calcule le prix d'un article en appliquant une remise si disponible.
     * Le calcul inclut les remises liées à la quantité et les remises supplémentaires via des promotions.
     *
     * @param art L'article pour lequel le prix est calculé.
     * @param q   La quantité de l'article à acheter.
     * @return Le prix total de l'article après application des remises.
     */
    public double calculerPrixArticleAvecRemise(Article art, int q) {
        // Calcule d'abord le prix sans remise.
        double total = calculerPrixArticle(art, q);
        // Recherche d'une promotion applicable à cet article.
        Discount promo = new DiscountDAOImpl().findByArticle(art.getIdArticle());
        if (promo != null) {
            // Si une promotion existe, l'appliquer.
            total = total * (1 - promo.getTaux() / 100.0);
        }
        return total;
    }
}
