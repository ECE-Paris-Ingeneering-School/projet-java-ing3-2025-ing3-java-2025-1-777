package DAO;

import model.Utilisateur;

/**
 * Interface DAO pour l’entité Utilisateur.
 */
public interface UtilisateurDAO extends GenericDAO<Utilisateur> {

    Utilisateur findByEmailAndPassword(String email, String motDePasse);

    boolean emailExists(String email);
}

