package DAO;

import model.Marque;
import java.util.List;

/**
 * DAO pour l’entité Marque.
 */
public interface MarqueDAO {

    Marque findById(int id);

    List<Marque> findAll();

    boolean insert(Marque marque);

    boolean update(Marque marque);

    boolean delete(int id);
}