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
}

