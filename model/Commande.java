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

    public Commande() {
    }

    public Commande(int idCommande, int idUtilisateur, Date dateCommande, double totalCommande) {
        this.idCommande = idCommande;
        this.idUtilisateur = idUtilisateur;
        this.dateCommande = dateCommande;
        this.totalCommande = totalCommande;
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
}
