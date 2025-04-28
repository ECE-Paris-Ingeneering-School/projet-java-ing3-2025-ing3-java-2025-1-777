package DAO;

import model.Marque;
import java.util.List;

/**
 * DAO pour l’entité Marque.
 */
public interface MarqueDAO {
    /**
     * Récupère la marque d’un ID donné.
     * @param id identifiant de la marque
     * @return la Marque ou null si introuvable
     */
    Marque findById(int id);

    /**
     * Liste toutes les marques.
     * @return liste de toutes les marques
     */
    List<Marque> findAll();

    /**
     * Insère une nouvelle marque.
     * @param marque la marque à créer
     * @return true si succès
     */
    boolean insert(Marque marque);

    /**
     * Met à jour une marque existante.
     * @param marque la marque modifiée
     * @return true si succès
     */
    boolean update(Marque marque);

    /**
     * Supprime une marque.
     * @param id identifiant de la marque à supprimer
     * @return true si succès
     */
    boolean delete(int id);
}