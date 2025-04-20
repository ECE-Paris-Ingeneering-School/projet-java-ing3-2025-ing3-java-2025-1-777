package model;

/**
 * Classe représentant un client dans le système.
 * Un client possède un identifiant, un nom, un prénom, un email, un mot de passe et un rôle (client ou administrateur).
 */
public class Client {
    // Déclaration des attributs représentant les caractéristiques d'un client
    private int idClient; // Identifiant unique du client
    private String nom; // Nom de famille du client
    private String prenom; // Prénom du client
    private String email; // Adresse email du client
    private String motDePasse; // Mot de passe du client pour la connexion
    private String role; // Le rôle du client : "client" ou "admin"

    /**
     * Constructeur par défaut.
     * Il permet de créer un client sans initialiser ses attributs.
     */
    public Client() {}

    /**
     * Constructeur avec paramètres pour initialiser un client avec toutes ses informations.
     * @param idClient L'identifiant unique du client.
     * @param nom Le nom du client.
     * @param prenom Le prénom du client.
     * @param email L'adresse email du client.
     * @param motDePasse Le mot de passe du client.
     * @param role Le rôle du client ("client" ou "admin").
     */
    public Client(int idClient, String nom, String prenom, String email, String motDePasse, String role) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    // **Getters et Setters** pour accéder et modifier les attributs du client

    /**
     * Getter pour l'ID du client.
     * @return L'identifiant unique du client.
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     * Setter pour l'ID du client.
     * @param idClient L'identifiant à attribuer au client.
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    /**
     * Getter pour le nom du client.
     * @return Le nom du client.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter pour le nom du client.
     * @param nom Le nom à attribuer au client.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter pour le prénom du client.
     * @return Le prénom du client.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter pour le prénom du client.
     * @param prenom Le prénom à attribuer au client.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter pour l'email du client.
     * @return L'email du client.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter pour l'email du client.
     * @param email L'email à attribuer au client.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter pour le mot de passe du client.
     * @return Le mot de passe du client.
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Setter pour le mot de passe du client.
     * @param motDePasse Le mot de passe à attribuer au client.
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Getter pour le rôle du client.
     * @return Le rôle du client ("client" ou "admin").
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter pour le rôle du client.
     * @param role Le rôle à attribuer au client ("client" ou "admin").
     */
    public void setRole(String role) {
        this.role = role;
    }
}
