// model/Panier.java
package model;

import java.util.HashMap;
import java.util.Map;
import util.BulkDiscountCalculator; // Utilitaire pour le calcul des remises en gros
import util.DiscountApplier; // Utilitaire pour appliquer des remises supplémentaires
import DAO.DiscountDAO; // Interface pour accéder aux données de remises
import DAO.DiscountDAOImpl; // Implémentation de DiscountDAO

/**
 * Classe représentant un panier d'achat.
 * Cette classe contient la logique pour gérer les articles dans un panier, calculer les totaux,
 * et appliquer les remises sur les articles.
 */
public class Panier {
    private Map<Article, Integer> articles; // Map pour stocker les articles et leur quantité dans le panier
    private int userId; // Identifiant de l'utilisateur auquel appartient ce panier
    private DiscountDAO discountDAO; // DAO pour accéder aux remises associées aux articles

    /**
     * Constructeur de la classe Panier.
     * Initialise un panier vide pour l'utilisateur spécifié et associe le DiscountDAO.
     * @param userId Identifiant de l'utilisateur.
     * @param discountDAO Implémentation de DiscountDAO permettant d'accéder aux remises.
     */
    public Panier(int userId, DiscountDAO discountDAO) {
        this.userId = userId; // Initialisation de l'ID de l'utilisateur
        this.articles = new HashMap<>(); // Initialisation de la Map pour stocker les articles
        this.discountDAO = discountDAO; // Initialisation du DAO pour gérer les remises
    }

    /**
     * Méthode pour ajouter un article au panier.
     * Si l'article existe déjà, la quantité est mise à jour (on ajoute à la quantité existante).
     * @param article L'article à ajouter.
     * @param quantite La quantité d'article à ajouter.
     */
    public void ajouterArticle(Article article, int quantite) {
        articles.merge(article, quantite, Integer::sum); // Merge permet d'ajouter la quantité de l'article si déjà présent
    }

    /**
     * Méthode pour supprimer un article du panier.
     * @param article L'article à supprimer.
     */
    public void supprimerArticle(Article article) {
        articles.remove(article); // Supprime l'article du panier
    }

    /**
     * Getter pour récupérer les articles présents dans le panier.
     * Retourne une copie de la Map des articles afin de ne pas exposer directement les données internes.
     * @return Une copie de la Map des articles et de leurs quantités.
     */
    public Map<Article, Integer> getArticles() {
        return new HashMap<>(articles); // Retourne une nouvelle Map pour éviter les modifications directes
    }

    /**
     * Méthode pour calculer le total du panier.
     * Cette méthode calcule la somme des prix des articles en appliquant les remises.
     * @return Le total du panier après application des remises.
     */
    public double calculerTotal() {
        // Pour chaque article dans le panier, on calcule son prix et on les additionne
        return articles.entrySet().stream()
                .mapToDouble(e -> calculerPrixArticle(e.getKey(), e.getValue())) // On calcule le prix de chaque article
                .sum(); // On fait la somme de tous les prix des articles
    }

    /**
     * Méthode pour calculer le prix d'un article en fonction de sa quantité et des remises appliquées.
     * 1. Applique la remise en gros si la quantité dépasse un certain seuil.
     * 2. Applique d'autres remises si disponibles.
     * @param article L'article dont on veut calculer le prix.
     * @param quantity La quantité de l'article.
     * @return Le prix total pour cet article et cette quantité après application des remises.
     */
    public double calculerPrixArticle(Article article, int quantity) {
        // 1. Calcul du prix en fonction de la quantité (remise en gros)
        double prix = BulkDiscountCalculator.calculateBulkPrice(
                quantity,
                article.getPrixUnitaire(),
                article.getPrixBulk(),
                article.getQuantiteBulk()
        );

        // 2. Application des remises supplémentaires (si présentes)
        Discount discount = discountDAO.getDiscountForArticle(article.getIdArticle()); // On récupère la remise associée à l'article
        if (discount != null) {
            prix = DiscountApplier.applyDiscount(prix, discount); // On applique la remise sur le prix de l'article
        }

        return prix; // Retourne le prix final de l'article après application des remises
    }

    /**
     * Getter pour l'ID de l'utilisateur.
     * @return L'ID de l'utilisateur associé à ce panier.
     */
    public int getUserId() {
        return userId; // Retourne l'ID de l'utilisateur
    }

    /**
     * Méthode pour vider le panier.
     * Supprime tous les articles du panier.
     */
    public void viderPanier() {
        articles.clear(); // Efface tous les articles du panier
    }

    /**
     * Setter pour définir le DiscountDAO.
     * Permet de modifier le DAO utilisé pour accéder aux remises.
     * @param discountDAO Le DAO à utiliser pour accéder aux remises.
     */
    public void setDiscountDAO(DiscountDAO discountDAO) {
        this.discountDAO = discountDAO; // Définit un nouveau DiscountDAO
    }
}
