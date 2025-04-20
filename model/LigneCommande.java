package model;

/**
 * Classe représentant les détails d’achat d’une commande.
 * Cette classe contient les informations liées à une ligne de commande, c'est-à-dire un article spécifique
 * dans une commande, incluant sa quantité et son prix total.
 */
public class LigneCommande {
    // Déclaration des attributs représentant les détails d'une ligne de commande
    private int idLigne; // Identifiant unique de la ligne de commande
    private int idCommande; // Identifiant de la commande à laquelle cette ligne appartient
    private int idArticle; // Identifiant de l'article correspondant à cette ligne de commande
    private int quantite; // Quantité d'articles dans cette ligne de commande
    private double prixTotal; // Prix total de la ligne (quantité * prix de l'article)

    /**
     * Constructeur par défaut.
     * Permet de créer une ligne de commande sans initialiser ses attributs.
     */
    public LigneCommande() {
    }

    /**
     * Constructeur avec paramètres pour initialiser tous les attributs de la ligne de commande.
     * @param idLigne Identifiant unique de la ligne de commande.
     * @param idCommande Identifiant de la commande à laquelle cette ligne appartient.
     * @param idArticle Identifiant de l'article correspondant à cette ligne.
     * @param quantite Quantité d'articles dans cette ligne de commande.
     * @param prixTotal Prix total de la ligne de commande.
     */
    public LigneCommande(int idLigne, int idCommande, int idArticle, int quantite, double prixTotal) {
        this.idLigne = idLigne; // Initialisation de l'identifiant de la ligne de commande
        this.idCommande = idCommande; // Initialisation de l'identifiant de la commande
        this.idArticle = idArticle; // Initialisation de l'identifiant de l'article
        this.quantite = quantite; // Initialisation de la quantité d'articles
        this.prixTotal = prixTotal; // Initialisation du prix total de la ligne de commande
    }

    // **Getters et Setters** pour accéder et modifier les attributs de la ligne de commande

    /**
     * Getter pour l'identifiant de la ligne de commande.
     * @return L'identifiant de la ligne de commande.
     */
    public int getIdLigne() {
        return idLigne; // Retourne l'identifiant de la ligne
    }

    /**
     * Setter pour l'identifiant de la ligne de commande.
     * @param idLigne L'identifiant à attribuer à la ligne de commande.
     */
    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne; // Définit l'identifiant de la ligne de commande
    }

    /**
     * Getter pour l'identifiant de la commande à laquelle cette ligne appartient.
     * @return L'identifiant de la commande.
     */
    public int getIdCommande() {
        return idCommande; // Retourne l'identifiant de la commande
    }

    /**
     * Setter pour l'identifiant de la commande à laquelle cette ligne appartient.
     * @param idCommande L'identifiant de la commande à définir.
     */
    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande; // Définit l'identifiant de la commande
    }

    /**
     * Getter pour l'identifiant de l'article correspondant à cette ligne.
     * @return L'identifiant de l'article.
     */
    public int getIdArticle() {
        return idArticle; // Retourne l'identifiant de l'article
    }

    /**
     * Setter pour l'identifiant de l'article correspondant à cette ligne.
     * @param idArticle L'identifiant de l'article à définir.
     */
    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle; // Définit l'identifiant de l'article
    }

    /**
     * Getter pour la quantité d'articles dans cette ligne de commande.
     * @return La quantité d'articles dans la ligne.
     */
    public int getQuantite() {
        return quantite; // Retourne la quantité d'articles
    }

    /**
     * Setter pour la quantité d'articles dans cette ligne de commande.
     * @param quantite La quantité à définir pour la ligne de commande.
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite; // Définit la quantité d'articles
    }

    /**
     * Getter pour le prix total de la ligne de commande.
     * @return Le prix total de la ligne de commande.
     */
    public double getPrixTotal() {
        return prixTotal; // Retourne le prix total de la ligne
    }

    /**
     * Setter pour le prix total de la ligne de commande.
     * @param prixTotal Le prix total à définir pour la ligne de commande.
     */
    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal; // Définit le prix total de la ligne de commande
    }
}
