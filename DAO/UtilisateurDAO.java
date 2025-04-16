package DAO;

import model.Utilisateur;

/**
 * Interface DAO pour l’entité Utilisateur.
 */
public interface UtilisateurDAO extends GenericDAO<Utilisateur> {

    /**
     * Recherche un utilisateur via son email et son mot de passe.
     */
    Utilisateur findByEmailAndPassword(String email, String motDePasse);

    /**
     * Recherche un utilisateur par son email.
     */
    Utilisateur findByEmail(String email);

    /**
     * Vérifie si un email existe déjà en base.
     */
    boolean emailExists(String email);
}