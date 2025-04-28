package DAO;

import model.LigneCommande;
import java.util.List;
/**
 * DAO pour la ligne de commande.
 */
public interface LigneCommandeDAO {
    boolean insert(LigneCommande ligne);
    List<LigneCommande> findByCommandeId(int commandeId);
}