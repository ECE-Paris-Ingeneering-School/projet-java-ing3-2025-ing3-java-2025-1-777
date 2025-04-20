package model;

/**
 * Classe représentant une réduction applicable à un article.
 * Cette classe permet de stocker les informations relatives à une réduction, y compris son identifiant,
 * sa description, son taux, son type (pourcentage ou montant fixe), et l'identifiant de l'article concerné.
 */
public class Discount {
    // Déclaration des attributs représentant les informations d'une réduction
    private int idDiscount; // Identifiant unique de la réduction
    private String description; // Description de la réduction (par exemple, "Soldes d'été")
    private double taux; // Taux de la réduction (en pourcentage ou en valeur absolue, selon le type)
    private String type; // Type de réduction : "pourcentage" ou "montant"
    private int idArticle; // Identifiant de l'article auquel la réduction s'applique

    /**
     * Constructeur par défaut.
     * Permet de créer une réduction sans initialiser ses attributs.
     */
    public Discount() {
    }

    /**
     * Constructeur avec paramètres pour initialiser tous les attributs de la réduction.
     * @param idDiscount Identifiant unique de la réduction.
     * @param description Description de la réduction.
     * @param taux Taux de la réduction (pourcentage ou valeur absolue).
     * @param type Type de la réduction (soit "pourcentage", soit "montant").
     * @param idArticle Identifiant de l'article auquel la réduction s'applique.
     */
    public Discount(int idDiscount, String description, double taux, String type, int idArticle) {
        this.idDiscount = idDiscount; // Initialisation de l'identifiant de la réduction
        this.description = description; // Initialisation de la description de la réduction
        this.taux = taux; // Initialisation du taux de la réduction
        this.type = type; // Initialisation du type de réduction
        this.idArticle = idArticle; // Initialisation de l'identifiant de l'article concerné
    }

    // **Getters et Setters** pour accéder et modifier les attributs de la réduction

    /**
     * Getter pour l'ID de la réduction.
     * @return L'identifiant de la réduction.
     */
    public int getIdDiscount() {
        return idDiscount; // Retourne l'identifiant de la réduction
    }

    /**
     * Setter pour l'ID de la réduction.
     * @param idDiscount L'identifiant à attribuer à la réduction.
     */
    public void setIdDiscount(int idDiscount) {
        this.idDiscount = idDiscount; // Définit l'identifiant de la réduction
    }

    /**
     * Getter pour la description de la réduction.
     * @return La description de la réduction.
     */
    public String getDescription() {
        return description; // Retourne la description de la réduction
    }

    /**
     * Setter pour la description de la réduction.
     * @param description La description à attribuer à la réduction.
     */
    public void setDescription(String description) {
        this.description = description; // Définit la description de la réduction
    }

    /**
     * Getter pour le taux de la réduction.
     * @return Le taux de la réduction (en pourcentage ou en valeur absolue).
     */
    public double getTaux() {
        return taux; // Retourne le taux de la réduction
    }

    /**
     * Setter pour le taux de la réduction.
     * @param taux Le taux de réduction à attribuer (en pourcentage ou en valeur absolue).
     */
    public void setTaux(double taux) {
        this.taux = taux; // Définit le taux de la réduction
    }

    /**
     * Getter pour le type de la réduction.
     * @return Le type de la réduction ("pourcentage" ou "montant").
     */
    public String getType() {
        return type; // Retourne le type de réduction
    }

    /**
     * Setter pour le type de la réduction.
     * @param type Le type de réduction à attribuer ("pourcentage" ou "montant").
     */
    public void setType(String type) {
        this.type = type; // Définit le type de la réduction
    }

    /**
     * Getter pour l'ID de l'article auquel la réduction s'applique.
     * @return L'identifiant de l'article.
     */
    public int getIdArticle() {
        return idArticle; // Retourne l'identifiant de l'article
    }

    /**
     * Setter pour l'ID de l'article auquel la réduction s'applique.
     * @param idArticle L'identifiant de l'article à définir.
     */
    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle; // Définit l'identifiant de l'article
    }
}
