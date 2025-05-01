package model;

/**
 * Classe représentant les détails d’une ligne de commande.
 */
public class LigneCommande {
    private int idLigne;
    private int idCommande;
    private int idArticle;
    private int quantite;
    private double prixTotal;

    public LigneCommande() {
    }

    public LigneCommande(int idLigne,int idCommande,int idArticle,int quantite,double prixTotal) {
        this.idLigne    = idLigne;
        this.idCommande = idCommande;
        this.idArticle  = idArticle;
        this.quantite   = quantite;
        this.prixTotal  = prixTotal;
    }

    /** getter qui retourne l'identifiant de la ligne de commande.
     */
    public int getIdLigne() {
        return idLigne;
    }

    /** setter qui définit l'identifiant de la ligne de commande.
     */
    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne;
    }

    /** getter qui retourne l'identifiant de la commande associée.
     */
    public int getIdCommande() {
        return idCommande;
    }

    /** setter qui définit l'identifiant de la commande associée.
     */
    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    /** getter qui retourne l'identifiant de l'article de cette ligne.
     */
    public int getIdArticle() {
        return idArticle;
    }

    /** setter qui définit l'identifiant de l'article de cette ligne.
     */
    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    /** getter qui retourne la quantité commandée.
     */
    public int getQuantite() {
        return quantite;
    }

    /** setter qui définit la quantité commandée.
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /** getter qui retourne le prix total de cette ligne de commande.
     */
    public double getPrixTotal() {
        return prixTotal;
    }

    /**Setter qui définit le prix total de cette ligne de commande.
     */
    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }
}
