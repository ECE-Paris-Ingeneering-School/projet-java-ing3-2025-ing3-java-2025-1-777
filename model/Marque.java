package model;

/**
 * Classe représentant une marque d'article.
 */
public class Marque {
    private int idMarque;
    private String nom;

    public Marque() {
    }

    public Marque(int idMarque, String nom) {
        this.idMarque = idMarque;
        this.nom = nom;
    }

    public int getIdMarque() {
        return idMarque;
    }

    public void setIdMarque(int idMarque) {
        this.idMarque = idMarque;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
