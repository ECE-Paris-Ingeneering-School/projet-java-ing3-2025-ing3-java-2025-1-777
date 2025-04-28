package DAO;

import model.Commande;
import model.Panier;
import java.util.List;

/**
 * Interface définissant les opérations liées à la gestion des commandes dans la base de données.
 * Cette interface permet de créer, rechercher et annuler des commandes.
 */
public interface CommandeDAO {

    /**
     * Crée une nouvelle commande à partir du panier d'un utilisateur et de l'adresse de livraison.
     *
     * @param panier      Le panier contenant les articles à commander.
     * @param adresseLiv  L'adresse de livraison de la commande.
     * @return true si la commande a été créée avec succès, false sinon.
     */
    boolean creerCommande(Panier panier, String adresseLiv);

    /**
     * Récupère une commande à partir de son ID.
     *
     * @param id L'ID de la commande à récupérer.
     * @return La commande correspondante à l'ID, ou null si la commande n'est pas trouvée.
     */
    Commande findById(int id);

    /**
     * Récupère une liste de commandes associées à un utilisateur, en fonction de son ID.
     *
     * @param userId L'ID de l'utilisateur pour lequel récupérer ses commandes.
     * @return Une liste de commandes passées par l'utilisateur spécifié.
     */
    List<Commande> findByUser(int userId);

    /**
     * Annule une commande existante.
     *
     * @param idCommande L'ID de la commande à annuler.
     * @return true si la commande a été annulée avec succès, false sinon.
     */
    boolean annulerCommande(int idCommande);
}
