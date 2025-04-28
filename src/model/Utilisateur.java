package model;
/**
 * Classe qui représente un utilisateur dans la BDD.
 */
public class Utilisateur {
    private int idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String role;
/** Constructeur par défaut*/
    public Utilisateur() {
    }
    /**
     * Constructeur avec paramètres
     */
    public Utilisateur(int idUtilisateur, String nom, String prenom, String email, String motDePasse, String role) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }
    /** getter qui retourne l'identifiant de l'utilisateur.
     */
    public int getIdUtilisateur() {
        return idUtilisateur;
    }
/** setter qui définit l'identifiant de l'utilisateur.*/
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    /** getter qui retourne le nom */
    public String getNom() {
        return nom;
    }
    /** setter qui définit le nom */
    public void setNom(String nom) {
        this.nom = nom;
    }
    /** getter qui retourne le prenom */
    public String getPrenom() {
        return prenom;
    }
    /** setter qui définit le prenom */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    /** getter qui retourne l'adresse email */
    public String getEmail() {
        return email;
    }
    /** setter qui définit l'adresse email */
    public void setEmail(String email) {
        this.email = email;
    }
    /** getter qui retourne le mot de passe */
    public String getMotDePasse() {
        return motDePasse;
    }
    /** setter qui définit le mot de passe */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    /** getter qui retourne le role de l'utilisateur*/
    public String getRole() {
        return role;
    }
/** setter qui définit le role de l'utilisateur*/
    public void setRole(String role) {
        this.role = role;
    }
}
