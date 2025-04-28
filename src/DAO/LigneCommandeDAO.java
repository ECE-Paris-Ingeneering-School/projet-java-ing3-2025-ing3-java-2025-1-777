package DAO;

import model.LigneCommande;
import java.util.List;

/**
 * Interface définissant les opérations liées à la gestion des lignes de commande dans la base de données.
 * Cette interface permet d'insérer des lignes de commande et de récupérer les lignes associées à une commande donnée.
 */
public interface LigneCommandeDAO {

    /**
     * Insère une nouvelle ligne de commande dans la base de données.
     *
     * @param ligne L'objet `LigneCommande` à insérer dans la base de données.
     * @return true si l'insertion a réussi, false sinon.
     */
    boolean insert(LigneCommande ligne);

    /**
     * Récupère les lignes de commande associées à une commande spécifique, identifiée par son ID.
     *
     * @param commandeId L'ID de la commande pour laquelle récupérer les lignes de commande.
     * @return Une liste de lignes de commande correspondant à l'ID de la commande spécifiée.
     */
    List<LigneCommande> findByCommandeId(int commandeId);
}
