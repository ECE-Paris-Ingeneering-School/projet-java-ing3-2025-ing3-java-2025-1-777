package DAO;

import model.LigneCommande;
import java.util.List;

public interface LigneCommandeDAO {
    boolean insert(LigneCommande ligne);
    List<LigneCommande> findByCommandeId(int commandeId);
}
