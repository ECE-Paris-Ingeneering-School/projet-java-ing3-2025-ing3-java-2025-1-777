package DAO;

import model.Commande;
import model.Panier;
import java.util.List;

public interface CommandeDAO {

    boolean creerCommande(Panier panier,String adresseLiv);

    Commande findById(int id);

    List<Commande> findByUser(int userId);
    boolean annulerCommande(int idCommande);

}