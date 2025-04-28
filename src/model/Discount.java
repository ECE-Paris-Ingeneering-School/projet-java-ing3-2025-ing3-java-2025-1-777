package model;
/**
 * Classe représentant une réduction applicable à un article.
 */
public class Discount {
    private int idDiscount;
    private String description;
    private double taux;
    private String type; // 'pourcentage' ou 'fixe'
    private int idArticle; // clé étrangère vers l'article concerné

    public Discount() {
    }

    public Discount(int idDiscount, String description, double taux, String type, int idArticle) {
        this.idDiscount = idDiscount;
        this.description = description;
        this.taux = taux;
        this.type = type;
        this.idArticle = idArticle;
    }

    public int getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(int idDiscount) {
        this.idDiscount = idDiscount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }
}

