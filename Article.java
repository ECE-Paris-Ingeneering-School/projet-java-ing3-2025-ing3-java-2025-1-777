package model;

public class Article {
    private int id_article;
    private String nom;
    private String description;
    private double prixUnitaire;
    private double prixBulk;
    private int quantiteBulk;
    private int stock;
    private String imagePath; // Chemin vers l'image du produit

    public Article() {
    }

  
    public int getId_article() {
        return id_article;
    }
    public void setId_article(int id_article) {
        this.id_article = id_article;
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
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
