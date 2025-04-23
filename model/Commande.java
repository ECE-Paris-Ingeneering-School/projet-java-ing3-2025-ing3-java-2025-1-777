package model;

import java.util.Date;

/**
 * Classe représentant une commande passée par un utilisateur.
 */
public class Commande {
    private int idCommande;
    private int idUtilisateur;
    private Date dateCommande;
    private double totalCommande;

    private String adresseLivraison;
    private String status;       
    private Date dateLivraison;

    public Commande() {}

    public Commande(int idCommande, int idUtilisateur, Date dateCommande,
                    double totalCommande, String adresseLivraison,
                    String status, Date dateLivraison) {
        this.idCommande = idCommande;
        this.idUtilisateur = idUtilisateur;
        this.dateCommande = dateCommande;
        this.totalCommande = totalCommande;
        this.adresseLivraison = adresseLivraison;
        this.status = status;
        this.dateLivraison = dateLivraison;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public double getTotalCommande() {
        return totalCommande;
    }

    public void setTotalCommande(double totalCommande) {
        this.totalCommande = totalCommande;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }
}
