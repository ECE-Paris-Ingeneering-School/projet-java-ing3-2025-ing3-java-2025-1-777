package model;

/**
 * Classe représentant un article à vendre.
 */
public class Article {
    private int idArticle;
    private String nom;
    private String description;
    private double prixUnitaire;
    private double prixBulk;
    private int quantiteBulk;
    private int stock;
    private String imagePath;

    public Article() {

    }


    public String getImagePath() {
        return imagePath;
    }


    public Article(int idArticle, String nom, String description, double prixUnitaire, double prixBulk, int quantiteBulk, int stock) {
        this.idArticle = idArticle;
        this.nom = nom;
        this.description = description;
        this.prixUnitaire = prixUnitaire;
        this.prixBulk = prixBulk;
        this.quantiteBulk = quantiteBulk;
        this.stock = stock;

    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Getters et setters
    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getPrixBulk() {
        return prixBulk;
    }

    public void setPrixBulk(double prixBulk) {
        this.prixBulk = prixBulk;
    }

    public int getQuantiteBulk() {
        return quantiteBulk;
    }

    public void setQuantiteBulk(int quantiteBulk) {
        this.quantiteBulk = quantiteBulk;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
