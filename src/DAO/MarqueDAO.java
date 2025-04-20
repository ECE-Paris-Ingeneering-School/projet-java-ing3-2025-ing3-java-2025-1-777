package DAO;

import model.Marque;
import java.util.List;

public interface MarqueDAO extends GenericDAO<Marque> {


    Marque findByName(String name);


    boolean insertBatch(List<Marque> list);
}
