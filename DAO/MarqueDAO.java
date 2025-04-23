package DAO;

import model.Marque;
import java.util.List;

/**
 * DAO pour l’entité Marque.
 */
public interface MarqueDAO {
    // Récupère la marque d’un article
    Marque findById(int id);

    //Liste de toutes les marques.
    List<Marque> findAll();
  
    boolean insert(Marque marque);

    boolean update(Marque marque);

    boolean delete(int id);
}
