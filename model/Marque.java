package model;

/**
 * Classe représentant une marque d'article.
 * Cette classe permet de stocker les informations relatives à une marque, telles que son identifiant
 * et son nom.
 */
public class Marque {
    // Déclaration des attributs représentant les informations d'une marque
    private int idMarque; // Identifiant unique de la marque
    private String nom; // Nom de la marque

    /**
     * Constructeur par défaut.
     * Permet de créer une marque sans initialiser ses attributs.
     */
    public Marque() {
    }

    /**
     * Constructeur avec paramètres pour initialiser tous les attributs de la marque.
     * @param idMarque Identifiant unique de la marque.
     * @param nom Nom de la marque.
     */
    public Marque(int idMarque, String nom) {
        this.idMarque = idMarque; // Initialisation de l'idMarque
        this.nom = nom; // Initialisation du nom de la marque
    }

    // **Getters et Setters** pour accéder et modifier les attributs de la marque

    /**
     * Getter pour l'ID de la marque.
     * @return L'identifiant unique de la marque.
     */
    public int getIdMarque() {
        return idMarque; // Retourne l'identifiant de la marque
    }

    /**
     * Setter pour l'ID de la marque.
     * @param idMarque L'identifiant à attribuer à la marque.
     */
    public void setIdMarque(int idMarque) {
        this.idMarque = idMarque; // Définit l'identifiant de la marque
    }

    /**
     * Getter pour le nom de la marque.
     * @return Le nom de la marque.
     */
    public String getNom() {
        return nom; // Retourne le nom de la marque
    }

    /**
     * Setter pour le nom de la marque.
     * @param nom Le nom à attribuer à la marque.
     */
    public void setNom(String nom) {
        this.nom = nom; // Définit le nom de la marque
    }
}
