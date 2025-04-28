package Controlers;

import DAO.CartItemDAO;
import DAO.CartItemDAOImpl;
import model.Article;
import model.CartItem;
import model.Panier;
import DAO.DiscountDAOImpl;
import model.Discount;



import java.util.List;
import java.util.Map;
/** Controlleur du panier*/
public class CartController {
    private static final double TVA_RATE = 0.20;

    private final Panier panier;
    private final CartItemDAO cartItemDAO = new CartItemDAOImpl();

    public CartController(int userId) {
        this.panier = new Panier(userId);
        cartItemDAO.findByUser(userId)
                .forEach((article, qty) -> panier.ajouterArticle(article, qty));
    }

    /**  Ajout des articles dans le panier et la BDD */
    public boolean ajouterAuPanier(Article a, int q) {
        if (a == null || q <= 0) return false;
        panier.ajouterArticle(a, q);
        return cartItemDAO.saveOrUpdate(
                panier.getUserId(),
                a.getIdArticle(),
                panier.getArticles().get(a)
        );
    }

    /** Suppression des articles a du panier et de la BDD */
    public void supprimerArticle(Article a) {
        panier.supprimerArticle(a);
        cartItemDAO.delete(panier.getUserId(), a.getIdArticle());
    }

    /** Vide le panier */
    public void viderPanier() {
        panier.getArticles().clear();
        cartItemDAO.clear(panier.getUserId());
    }

    /** Retourne l’objet modèle Panier (pour affichage, checkout, etc.) */
    public Panier getPanier() {
        return panier;
    }

    /**
     * Calcul du total Hors Taxes
     */
    public double calculerTotalHT() {
        double total = 0;
        for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
            Article art = entry.getKey();
            int qty = entry.getValue();

            // Appliquer la remise
            Discount discount = new DiscountDAOImpl().getDiscountForArticle(art.getIdArticle());
            double unitPrice = art.getPrixUnitaire();
            if (discount != null) {
                unitPrice = unitPrice * (1 - discount.getTaux() / 100); // Appliquer la remise
            }

            int bulkQty = art.getQuantiteBulk();
            double bulkPrice = art.getPrixBulk();

            if (bulkQty > 0 && qty >= bulkQty) {
                int groups = qty / bulkQty;
                int remainder = qty % bulkQty;
                total += groups * bulkPrice + remainder * unitPrice;
            } else {
                total += qty * unitPrice;
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
    public List<CartItem> getItems() {
        return panier.getItems();
    }

}