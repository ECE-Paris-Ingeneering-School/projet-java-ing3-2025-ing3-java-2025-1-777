package DAO;

import model.Commande;
import model.LigneCommande;
import model.Panier;
import Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAOImpl implements CommandeDAO {

    @Override
    public boolean creerCommande(Panier panier, String adresseLivraison) {
        if (panier == null || panier.getArticles() == null) {
            throw new IllegalArgumentException("Le panier est invalide");
        }

        // Calcul du total HT et TTC
        double totalHT = panier.calculerTotalHT();
        double totalTTC = totalHT * 1.20;

        String sqlCommande = """
            INSERT INTO Commande
              (id_utilisateur, date_commande, date_livraison, total_commande, adresse_livraison, status)
            VALUES (?, NOW(), DATE_ADD(NOW(), INTERVAL 15 DAY), ?, ?, 'EN_COURS')
            """;
        String sqlLigne = """
            INSERT INTO LigneCommande
              (id_commande, id_article, quantite, prix_total)
            VALUES (?, ?, ?, ?)
            """;
        String sqlStock = """
            UPDATE Article
               SET stock = stock - ?
             WHERE id_article = ?
               AND stock >= ?
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psCommande = conn.prepareStatement(sqlCommande, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psLigne   = conn.prepareStatement(sqlLigne);
             PreparedStatement psStock   = conn.prepareStatement(sqlStock)) {

            conn.setAutoCommit(false);

            // 1) Insert de la commande
            psCommande.setInt(1, panier.getUserId());
            psCommande.setDouble(2, totalTTC);
            psCommande.setString(3, adresseLivraison);
            if (psCommande.executeUpdate() == 0) {
                conn.rollback();
                return false;
            }

            // 2) Récupération de l'ID généré
            int cmdId;
            try (ResultSet rs = psCommande.getGeneratedKeys()) {
                if (rs.next()) {
                    cmdId = rs.getInt(1);
                } else {
                    conn.rollback();
                    return false;
                }
            }

            // 3) Insertion des lignes et mise à jour du stock
            for (var e : panier.getArticles().entrySet()) {
                int artId = e.getKey().getIdArticle();
                int qty   = e.getValue();
                double prixLigne = e.getKey().getPrixUnitaire() * qty;

                // Ligne de commande
                psLigne.setInt(1, cmdId);
                psLigne.setInt(2, artId);
                psLigne.setInt(3, qty);
                psLigne.setDouble(4, prixLigne);
                psLigne.executeUpdate();

                // Mise à jour stock
                psStock.setInt(1, qty);
                psStock.setInt(2, artId);
                psStock.setInt(3, qty);
                if (psStock.executeUpdate() != 1) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean updateStock(Connection conn, int articleId, int quantite) throws SQLException {
        String sql = """
            UPDATE Article
               SET stock = stock - ?
             WHERE id_article = ?
               AND stock >= ?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantite);
            ps.setInt(2, articleId);
            ps.setInt(3, quantite);
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean annulerCommande(int commandeId) {
        String sql = "UPDATE Commande SET status='ANNULEE' WHERE id_commande = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, commandeId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Commande findById(int id) {
        Commande cmd = null;
        String sql = """
            SELECT id_commande, id_utilisateur,
                   date_commande, date_livraison,
                   adresse_livraison, total_commande, status
              FROM Commande
             WHERE id_commande = ?
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cmd = new Commande();
                    cmd.setIdCommande(rs.getInt("id_commande"));
                    cmd.setIdUtilisateur(rs.getInt("id_utilisateur"));
                    cmd.setDateCommande(rs.getTimestamp("date_commande"));
                    cmd.setDateLivraison(rs.getTimestamp("date_livraison"));
                    cmd.setAdresseLivraison(rs.getString("adresse_livraison"));
                    cmd.setTotalCommande(rs.getDouble("total_commande"));
                    cmd.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cmd;
    }

    @Override
    public List<Commande> findByUser(int userId) {
        List<Commande> list = new ArrayList<>();
        String sql = """
            SELECT id_commande, id_utilisateur,
                   date_commande, date_livraison,
                   adresse_livraison, total_commande, status
              FROM Commande
             WHERE id_utilisateur = ?
          ORDER BY date_commande DESC
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Commande cmd = new Commande();
                    cmd.setIdCommande(rs.getInt("id_commande"));
                    cmd.setIdUtilisateur(rs.getInt("id_utilisateur"));
                    cmd.setDateCommande(rs.getTimestamp("date_commande"));
                    cmd.setDateLivraison(rs.getTimestamp("date_livraison"));
                    cmd.setAdresseLivraison(rs.getString("adresse_livraison"));
                    cmd.setTotalCommande(rs.getDouble("total_commande"));
                    cmd.setStatus(rs.getString("status"));
                    list.add(cmd);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
