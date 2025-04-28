package model;

import java.util.ArrayList;
import java.util.List;
/**
 * Classe qui représente un article du catalogue avec ses attributs .
 */
public class Article {
    private int idArticle;
    private String nom;
    private String description;
    private double prixUnitaire;
    private double prixBulk;
    private int quantiteBulk;
    private int stock;
    private int idMarque;
    private List<String> imagePaths = new ArrayList<>();

  /**
   * getters et setters pour la liste complète*/
    public int getIdArticle() { return idArticle; }
    public void setIdArticle(int idArticle) { this.idArticle = idArticle; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public double getPrixBulk() { return prixBulk; }
    public void setPrixBulk(double prixBulk) { this.prixBulk = prixBulk; }

    public int getQuantiteBulk() { return quantiteBulk; }
    public void setQuantiteBulk(int quantiteBulk) { this.quantiteBulk = quantiteBulk; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getIdMarque() { return idMarque; }
    public void setIdMarque(int idMarque) { this.idMarque = idMarque; }

    public List<String> getImagePaths() { return imagePaths; }
    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths != null ? imagePaths : new ArrayList<>();
    }


    public String getImagePath() {
        return imagePaths.isEmpty() ? null : imagePaths.get(0);
    }
    public void setImagePath(String imagePath) {
        this.imagePaths.clear();
        if (imagePath != null) this.imagePaths.add(imagePath);
    }
}