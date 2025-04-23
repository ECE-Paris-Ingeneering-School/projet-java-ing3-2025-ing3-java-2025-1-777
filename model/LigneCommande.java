package model;

/**
 * Classe représentant une ligne d’une commande.
 */
public class LigneCommande {
    private int idLigne;
    private int idCommande;
    private int idArticle;
    private int quantite;
    private double prixTotal;

    public LigneCommande() {
    }

    public LigneCommande(int idLigne, int idCommande, int idArticle, int quantite, double prixTotal) {
        this.idLigne = idLigne;
        this.idCommande = idCommande;
        this.idArticle = idArticle;
        this.quantite = quantite;
        this.prixTotal = prixTotal;
    }

    public int getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }
}
