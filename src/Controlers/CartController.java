package Controlers;

import DAO.CartItemDAO;
import DAO.CartItemDAOImpl;
import model.Article;
import model.Panier;

import java.util.Map;

/**
 * Le contrôleur du panier qui gère les articles ajoutés ou supprimés dans le panier
 * et les interactions avec la base de données concernant ces articles.
 */
public class CartController {
    // Taux de TVA appliqué aux calculs de prix.
    private static final double TVA_RATE = 0.20;

    // Le panier de l'utilisateur, qui contient les articles ajoutés.
    private final Panier panier;

    // L'interface pour interagir avec les articles du panier dans la base de données.
    private final CartItemDAO cartItemDAO = new CartItemDAOImpl();

    /**
     * Constructeur du contrôleur du panier.
     * Initialise le panier de l'utilisateur et charge les articles existants depuis la base de données.
     *
     * @param userId L'ID de l'utilisateur pour lequel le panier est créé.
     */
    public CartController(int userId) {
        this.panier = new Panier(userId);
        // Charge les articles existants du panier pour cet utilisateur depuis la base de données.
        cartItemDAO.findByUser(userId)
                .forEach((article, qty) -> panier.ajouterArticle(article, qty));
    }

    /**
     * Ajoute un article au panier et met à jour la base de données.
     *
     * @param a L'article à ajouter au panier.
     * @param q La quantité de l'article à ajouter.
     * @return true si l'article a été ajouté avec succès, false sinon.
     */
    public boolean ajouterAuPanier(Article a, int q) {
        if (a == null || q <= 0) return false;
        panier.ajouterArticle(a, q);
        // Met à jour la base de données avec l'article ajouté ou mis à jour.
        return cartItemDAO.saveOrUpdate(
                panier.getUserId(),
                a.getIdArticle(),
                panier.getArticles().get(a)
        );
    }

    /**
     * Supprime complètement un article du panier et de la base de données.
     *
     * @param a L'article à supprimer.
     */
    public void supprimerArticle(Article a) {
        panier.supprimerArticle(a);
        // Supprime l'article du panier et de la base de données.
        cartItemDAO.delete(panier.getUserId(), a.getIdArticle());
    }

    /**
     * Vide le panier en mémoire et dans la base de données.
     */
    public void viderPanier() {
        panier.getArticles().clear();
        // Vide la base de données des articles du panier pour cet utilisateur.
        cartItemDAO.clear(panier.getUserId());
    }

    /**
     * Retourne le panier de l'utilisateur.
     *
     * @return Le panier de l'utilisateur, contenant les articles.
     */
    public Panier getPanier() {
        return panier;
    }

    /**
     * Calcule le total hors taxes (HT) en prenant en compte les prix de gros (bulk) et les prix unitaires.
     *
     * @return Le total hors taxes (HT) du panier.
     */
    public double calculerTotalHT() {
        double total = 0;
        // Parcours des articles du panier pour calculer le total.
        for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
            Article art = entry.getKey();
            int qty = entry.getValue();

            int bulkQty     = art.getQuantiteBulk();
            double unit     = art.getPrixUnitaire();
            double bulkPrice= art.getPrixBulk();

            // Si le prix de gros s'applique, on le prend en compte pour les quantités en gros.
            if (bulkQty > 0 && qty >= bulkQty) {
                int groups    = qty / bulkQty;
                int remainder = qty % bulkQty;
                total += groups * bulkPrice + remainder * unit;
            } else {
                total += qty * unit;
            }
        }
        return total;
    }

    /**
     * Calcule le montant de la TVA (Taxe sur la Valeur Ajoutée) à un taux de 20%.
     *
     * @return Le montant de la TVA.
     */
    public double calculerTVA() {
        return calculerTotalHT() * TVA_RATE;
    }

    /**
     * Calcule le total toutes taxes comprises (TTC), en ajoutant la TVA au total hors taxes.
     *
     * @return Le total TTC.
     */
    public double calculerTotalTTC() {
        return calculerTotalHT() + calculerTVA();
    }
}
