package model;

/**
 * Classe représentant un article à vendre.
 * Cette classe contient les informations relatives à un article, comme son nom, sa description,
 * son prix unitaire, son prix en gros, la quantité en gros, le stock disponible et le chemin de l'image.
 */
public class Article {
    // Déclaration des attributs représentant les caractéristiques d'un article
    private int idArticle; // Identifiant unique de l'article
    private String nom; // Nom de l'article
    private String description; // Description de l'article
    private double prixUnitaire; // Prix unitaire de l'article
    private double prixBulk; // Prix pour l'achat en gros de l'article
    private int quantiteBulk; // Quantité minimum d'achat pour bénéficier du prix en gros
    private int stock; // Quantité d'articles disponibles en stock
    private String imagePath; // Chemin d'accès à l'image de l'article (représente l'image associée à l'article)

    /**
     * Constructeur par défaut.
     * Il permet de créer un article sans initialiser ses attributs.
     */
    public Article() {

    }

    /**
     * Constructeur avec paramètres pour initialiser tous les attributs de l'article.
     * @param idArticle Identifiant de l'article.
     * @param nom Nom de l'article.
     * @param description Description de l'article.
     * @param prixUnitaire Prix unitaire de l'article.
     * @param prixBulk Prix en gros de l'article.
     * @param quantiteBulk Quantité minimum pour bénéficier du prix en gros.
     * @param stock Quantité d'articles en stock.
     */
    public Article(int idArticle, String nom, String description, double prixUnitaire, double prixBulk, int quantiteBulk, int stock) {
        this.idArticle = idArticle;
        this.nom = nom;
        this.description = description;
        this.prixUnitaire = prixUnitaire;
        this.prixBulk = prixBulk;
        this.quantiteBulk = quantiteBulk;
        this.stock = stock;
    }

    /**
     * Getter pour récupérer le chemin de l'image de l'article.
     * @return Le chemin de l'image associée à l'article.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Setter pour définir le chemin de l'image de l'article.
     * @param imagePath Chemin d'accès à l'image de l'article.
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // **Getters et Setters** pour accéder et modifier les autres attributs de l'article

    /**
     * Getter pour l'id de l'article.
     * @return L'id de l'article.
     */
    public int getIdArticle() {
        return idArticle;
    }

    /**
     * Setter pour définir l'id de l'article.
     * @param idArticle L'id à définir pour l'article.
     */
    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    /**
     * Getter pour le nom de l'article.
     * @return Le nom de l'article.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter pour définir le nom de l'article.
     * @param nom Le nom à définir pour l'article.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter pour la description de l'article.
     * @return La description de l'article.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter pour définir la description de l'article.
     * @param description La description à définir pour l'article.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter pour le prix unitaire de l'article.
     * @return Le prix unitaire de l'article.
     */
    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    /**
     * Setter pour définir le prix unitaire de l'article.
     * @param prixUnitaire Le prix unitaire à définir pour l'article.
     */
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    /**
     * Getter pour le prix en gros de l'article.
     * @return Le prix en gros de l'article.
     */
    public double getPrixBulk() {
        return prixBulk;
    }

    /**
     * Setter pour définir le prix en gros de l'article.
     * @param prixBulk Le prix en gros à définir pour l'article.
     */
    public void setPrixBulk(double prixBulk) {
        this.prixBulk = prixBulk;
    }

    /**
     * Getter pour la quantité minimum d'achat pour bénéficier du prix en gros.
     * @return La quantité minimum d'achat pour bénéficier du prix en gros.
     */
    public int getQuantiteBulk() {
        return quantiteBulk;
    }

    /**
     * Setter pour définir la quantité minimum d'achat pour bénéficier du prix en gros.
     * @param quantiteBulk La quantité minimum d'achat à définir.
     */
    public void setQuantiteBulk(int quantiteBulk) {
        this.quantiteBulk = quantiteBulk;
    }

    /**
     * Getter pour le stock disponible de l'article.
     * @return Le stock d'articles disponibles.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter pour définir le stock de l'article.
     * @param stock Le stock d'articles à définir.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
}
