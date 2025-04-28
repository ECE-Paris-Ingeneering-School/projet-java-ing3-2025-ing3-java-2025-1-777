package DAO;

import model.Discount;
import java.util.List;

/**
 * Interface définissant les opérations liées à la gestion des remises dans la base de données.
 * Cette interface permet de rechercher, insérer, mettre à jour et supprimer des remises.
 */
public interface DiscountDAO {

    /**
     * Récupère une remise à partir de son ID.
     *
     * @param id L'ID de la remise à récupérer.
     * @return La remise correspondant à l'ID, ou null si non trouvée.
     */
    Discount findById(int id);

    /**
     * Récupère la remise associée à un article spécifié par son ID.
     *
     * @param idArticle L'ID de l'article pour lequel récupérer la remise.
     * @return La remise correspondant à l'article spécifié, ou null si non trouvée.
     */
    Discount findByArticle(int idArticle);

    /**
     * Récupère toutes les remises disponibles dans la base de données.
     *
     * @return Une liste de toutes les remises présentes dans la base de données.
     */
    List<Discount> findAll();

    /**
     * Insère une nouvelle remise dans la base de données.
     *
     * @param d La remise à insérer.
     * @return true si l'insertion a réussi, false sinon.
     */
    boolean insert(Discount d);

    /**
     * Met à jour une remise existante dans la base de données.
     *
     * @param d La remise avec les nouvelles informations à mettre à jour.
     * @return true si la mise à jour a réussi, false sinon.
     */
    boolean update(Discount d);

    /**
     * Supprime une remise de la base de données en fonction de son ID.
     *
     * @param idDiscount L'ID de la remise à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    boolean delete(int idDiscount);

    /**
     * Récupère la remise actuellement applicable à un article spécifique.
     *
     * @param idArticle L'ID de l'article pour lequel récupérer la remise.
     * @return La remise applicable à l'article, ou null si aucune remise n'est disponible.
     */
    Discount getDiscountForArticle(int idArticle);
}
