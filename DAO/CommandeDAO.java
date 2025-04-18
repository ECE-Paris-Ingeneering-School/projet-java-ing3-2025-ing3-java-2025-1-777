package DAO;

import model.Commande;
import model.Panier;
import java.util.List;  // Ajoutez cet import


public interface CommandeDAO {
    boolean creerCommande(Panier panier, String adresseLivraison);
    Commande findById(int id);
    List<Commande> findByUser(int userId);
}