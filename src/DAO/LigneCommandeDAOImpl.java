package DAO;

import model.LigneCommande;
import Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface {@link LigneCommandeDAO} pour la gestion des lignes de commande dans la base de données.
 * Cette classe permet d'insérer des lignes de commande et de récupérer les lignes associées à une commande spécifique.
 */
public class LigneCommandeDAOImpl implements LigneCommandeDAO {

    /**
     * Insère une nouvelle ligne de commande dans la base de données.
     *
     * @param ligne L'objet {@link LigneCommande} à insérer dans la base de données.
     * @return true si l'insertion a réussi, false sinon.
     */
    @Override
    public boolean insert(LigneCommande ligne) {
        String sql = "INSERT INTO LigneCommande (id_commande, id_article, quantite, prix_total) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ligne.getIdCommande());
            ps.setInt(2, ligne.getIdArticle());
            ps.setInt(3, ligne.getQuantite());
            ps.setDouble(4, ligne.getPrixTotal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère les lignes de commande associées à une commande spécifique, identifiée par son ID.
     *
     * @param commandeId L'ID de la commande pour laquelle récupérer les lignes de commande.
     * @return Une liste de lignes de commande correspondant à l'ID de la commande spécifiée.
     */
    @Override
    public List<LigneCommande> findByCommandeId(int commandeId) {
        List<LigneCommande> lignes = new ArrayList<>();
        String sql = "SELECT * FROM LigneCommande WHERE id_commande = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, commandeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LigneCommande l = new LigneCommande();
                l.setIdLigne(rs.getInt("id_ligne"));
                l.setIdCommande(rs.getInt("id_commande"));
                l.setIdArticle(rs.getInt("id_article"));
                l.setQuantite(rs.getInt("quantite"));
                l.setPrixTotal(rs.getDouble("prix_total"));
                lignes.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lignes;
    }
}
