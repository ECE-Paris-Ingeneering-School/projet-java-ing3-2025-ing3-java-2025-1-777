package DAO;

import model.LigneCommande;
import Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LigneCommandeDAOImpl implements LigneCommandeDAO {
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
