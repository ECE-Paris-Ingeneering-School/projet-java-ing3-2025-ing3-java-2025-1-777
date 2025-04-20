package model;

import java.util.Date; // Importation de la classe Date pour gérer la date de la commande

/**
 * Classe représentant une commande passée par un utilisateur.
 * Cette classe contient les informations essentielles d'une commande, telles que l'identifiant de la commande,
 * l'identifiant de l'utilisateur, la date de la commande et le montant total de la commande.
 */
public class Commande {
    // Déclaration des attributs représentant les informations d'une commande
    private int idCommande; // Identifiant unique de la commande
    private int idUtilisateur; // Identifiant de l'utilisateur ayant passé la commande
    private Date dateCommande; // Date de la commande
    private double totalCommande; // Montant total de la commande

    /**
     * Constructeur par défaut.
     * Permet de créer une commande sans initialiser ses attributs.
     */
    public Commande() {
    }

    /**
     * Constructeur avec paramètres pour initialiser tous les attributs de la commande.
     * @param idCommande Identifiant unique de la commande.
     * @param idUtilisateur Identifiant de l'utilisateur ayant passé la commande.
     * @param dateCommande Date à laquelle la commande a été passée.
     * @param totalCommande Montant total de la commande.
     */
    public Commande(int idCommande, int idUtilisateur, Date dateCommande, double totalCommande) {
        this.idCommande = idCommande; // Initialisation de l'idCommande
        this.idUtilisateur = idUtilisateur; // Initialisation de l'idUtilisateur
        this.dateCommande = dateCommande; // Initialisation de la date de la commande
        this.totalCommande = totalCommande; // Initialisation du total de la commande
    }

    // **Getters et Setters** pour accéder et modifier les attributs de la commande

    /**
     * Getter pour l'ID de la commande.
     * @return L'identifiant unique de la commande.
     */
    public int getIdCommande() {
        return idCommande; // Retourne l'identifiant de la commande
    }

    /**
     * Setter pour l'ID de la commande.
     * @param idCommande L'identifiant à attribuer à la commande.
     */
    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande; // Définit l'identifiant de la commande
    }

    /**
     * Getter pour l'ID de l'utilisateur ayant passé la commande.
     * @return L'identifiant de l'utilisateur.
     */
    public int getIdUtilisateur() {
        return idUtilisateur; // Retourne l'identifiant de l'utilisateur
    }

    /**
     * Setter pour l'ID de l'utilisateur ayant passé la commande.
     * @param idUtilisateur L'identifiant de l'utilisateur à définir.
     */
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur; // Définit l'identifiant de l'utilisateur
    }

    /**
     * Getter pour la date de la commande.
     * @return La date de la commande.
     */
    public Date getDateCommande() {
        return dateCommande; // Retourne la date de la commande
    }

    /**
     * Setter pour la date de la commande.
     * @param dateCommande La date à attribuer à la commande.
     */
    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande; // Définit la date de la commande
    }

    /**
     * Getter pour le montant total de la commande.
     * @return Le montant total de la commande.
     */
    public double getTotalCommande() {
        return totalCommande; // Retourne le total de la commande
    }

    /**
     * Setter pour le montant total de la commande.
     * @param totalCommande Le montant total à définir pour la commande.
     */
    public void setTotalCommande(double totalCommande) {
        this.totalCommande = totalCommande; // Définit le montant total de la commande
    }
}
