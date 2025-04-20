package model;

/**
 * Classe représentant un utilisateur du système.
 * Cette classe contient les informations de base d'un utilisateur, telles que son identifiant,
 * son nom, son prénom, son email, son mot de passe et son rôle (par exemple, "client" ou "admin").
 */
public class Utilisateur {
    private int idUtilisateur; // Identifiant unique de l'utilisateur
    private String nom; // Nom de famille de l'utilisateur
    private String prenom; // Prénom de l'utilisateur
    private String email; // Adresse email de l'utilisateur
    private String motDePasse; // Mot de passe de l'utilisateur pour la connexion
    private String role; // Le rôle de l'utilisateur (par exemple, "client" ou "admin")

    /**
     * Constructeur par défaut.
     * Permet de créer un utilisateur sans initialiser ses attributs.
     */
    public Utilisateur() {
    }

    /**
     * Constructeur avec paramètres pour initialiser tous les attributs de l'utilisateur.
     * @param idUtilisateur Identifiant unique de l'utilisateur.
     * @param nom Le nom de l'utilisateur.
     * @param prenom Le prénom de l'utilisateur.
     * @param email L'email de l'utilisateur.
     * @param motDePasse Le mot de passe de l'utilisateur.
     * @param role Le rôle de l'utilisateur (ex : "client" ou "admin").
     */
    public Utilisateur(int idUtilisateur, String nom, String prenom, String email, String motDePasse, String role) {
        this.idUtilisateur = idUtilisateur; // Initialisation de l'identifiant de l'utilisateur
        this.nom = nom; // Initialisation du nom de l'utilisateur
        this.prenom = prenom; // Initialisation du prénom de l'utilisateur
        this.email = email; // Initialisation de l'email de l'utilisateur
        this.motDePasse = motDePasse; // Initialisation du mot de passe de l'utilisateur
        this.role = role; // Initialisation du rôle de l'utilisateur
    }

    // **Getters et Setters** pour accéder et modifier les attributs de l'utilisateur

    /**
     * Getter pour l'identifiant de l'utilisateur.
     * @return L'identifiant unique de l'utilisateur.
     */
    public int getIdUtilisateur() {
        return idUtilisateur; // Retourne l'identifiant de l'utilisateur
    }

    /**
     * Setter pour l'identifiant de l'utilisateur.
     * @param idUtilisateur L'identifiant à attribuer à l'utilisateur.
     */
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur; // Définit l'identifiant de l'utilisateur
    }

    /**
     * Getter pour le nom de l'utilisateur.
     * @return Le nom de l'utilisateur.
     */
    public String getNom() {
        return nom; // Retourne le nom de l'utilisateur
    }

    /**
     * Setter pour le nom de l'utilisateur.
     * @param nom Le nom à attribuer à l'utilisateur.
     */
    public void setNom(String nom) {
        this.nom = nom; // Définit le nom de l'utilisateur
    }

    /**
     * Getter pour le prénom de l'utilisateur.
     * @return Le prénom de l'utilisateur.
     */
    public String getPrenom() {
        return prenom; // Retourne le prénom de l'utilisateur
    }

    /**
     * Setter pour le prénom de l'utilisateur.
     * @param prenom Le prénom à attribuer à l'utilisateur.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom; // Définit le prénom de l'utilisateur
    }

    /**
     * Getter pour l'email de l'utilisateur.
     * @return L'email de l'utilisateur.
     */
    public String getEmail() {
        return email; // Retourne l'email de l'utilisateur
    }

    /**
     * Setter pour l'email de l'utilisateur.
     * @param email L'email à attribuer à l'utilisateur.
     */
    public void setEmail(String email) {
        this.email = email; // Définit l'email de l'utilisateur
    }

    /**
     * Getter pour le mot de passe de l'utilisateur.
     * @return Le mot de passe de l'utilisateur.
     */
    public String getMotDePasse() {
        return motDePasse; // Retourne le mot de passe de l'utilisateur
    }

    /**
     * Setter pour le mot de passe de l'utilisateur.
     * @param motDePasse Le mot de passe à attribuer à l'utilisateur.
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse; // Définit le mot de passe de l'utilisateur
    }

    /**
     * Getter pour le rôle de l'utilisateur.
     * @return Le rôle de l'utilisateur ("client" ou "admin").
     */
    public String getRole() {
        return role; // Retourne le rôle de l'utilisateur
    }

    /**
     * Setter pour le rôle de l'utilisateur.
     * @param role Le rôle à attribuer à l'utilisateur.
     */
    public void setRole(String role) {
        this.role = role; // Définit le rôle de l'utilisateur
    }
}
