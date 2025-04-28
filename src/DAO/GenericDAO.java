package DAO;

import java.util.List;

/**
 * Interface générique définissant les opérations de base pour la gestion des entités dans la base de données.
 * Cette interface peut être utilisée pour toute entité (type T) dans l'application, permettant de
 * réaliser des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer) sur ces entités.
 *
 * @param <T> Le type d'entité sur laquelle l'interface opère (par exemple, Article, Commande, etc.).
 */
public interface GenericDAO<T> {

    /**
     * Récupère une entité de type T à partir de son ID.
     *
     * @param id L'ID de l'entité à récupérer.
     * @return L'entité correspondante à l'ID, ou null si l'entité n'est pas trouvée.
     */
    T findById(int id);

    /**
     * Récupère toutes les entités de type T.
     *
     * @return Une liste de toutes les entités de type T présentes dans la base de données.
     */
    List<T> findAll();

    /**
     * Insère une nouvelle entité de type T dans la base de données.
     *
     * @param obj L'entité à insérer.
     * @return true si l'insertion a réussi, false sinon.
     */
    boolean insert(T obj);

    /**
     * Met à jour une entité existante de type T dans la base de données.
     *
     * @param obj L'entité avec les nouvelles informations à mettre à jour.
     * @return true si la mise à jour a réussi, false sinon.
     */
    boolean update(T obj);

    /**
     * Supprime une entité de type T de la base de données en fonction de son ID.
     *
     * @param id L'ID de l'entité à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    boolean delete(int id);
}
