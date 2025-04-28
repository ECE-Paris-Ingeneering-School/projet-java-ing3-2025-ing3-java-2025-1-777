package Controlers;

import DAO.CartItemDAO;
import DAO.CartItemDAOImpl;
import model.Article;
import model.Panier;

import java.util.Map;

public class CartController {
    private static final double TVA_RATE = 0.20;

    private final Panier panier;
    private final CartItemDAO cartItemDAO = new CartItemDAOImpl();

    public CartController(int userId) {
        this.panier = new Panier(userId);
        cartItemDAO.findByUser(userId)
                .forEach((article, qty) -> panier.ajouterArticle(article, qty));
    }

    /**  mise à jour de la  BDD */
    public boolean ajouterAuPanier(Article a, int q) {
        if (a == null || q <= 0) return false;
        panier.ajouterArticle(a, q);
        return cartItemDAO.saveOrUpdate(
                panier.getUserId(),
                a.getIdArticle(),
                panier.getArticles().get(a)
        );
    }

    /** Supprime complètement l’article a du panier et de la BDD */
    public void supprimerArticle(Article a) {
        panier.supprimerArticle(a);
        cartItemDAO.delete(panier.getUserId(), a.getIdArticle());
    }

    /** Vide le panier à la fois en mémoire et en base */
    public void viderPanier() {
        panier.getArticles().clear();
        cartItemDAO.clear(panier.getUserId());
    }

    /** Retourne l’objet modèle Panier (pour affichage, checkout, etc.) */
    public Panier getPanier() {
        return panier;
    }

    /**
     * Calcul du total Hors Taxes en appliquant :
     * - le prix bulk pour chaque tranche de quantiteBulk
     * - le prix unitaire pour le reste
     */
    public double calculerTotalHT() {
        double total = 0;
        for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
            Article art = entry.getKey();
            int qty = entry.getValue();

            int bulkQty     = art.getQuantiteBulk();
            double unit     = art.getPrixUnitaire();
            double bulkPrice= art.getPrixBulk();

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

    /** Montant de la TVA (au taux fixe de 20%) */
    public double calculerTVA() {
        return calculerTotalHT() * TVA_RATE;
    }

    /** Total TTC = HT + TVA */
    public double calculerTotalTTC() {
        return calculerTotalHT() + calculerTVA();
    }
}