package DAO;

import model.Commande;
import model.LigneCommande;
import model.Panier;
import model.Article;
import util.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;  // Import ajouté ici

public class CommandeDAOImpl implements CommandeDAO {

    @Override
    public boolean creerCommande(Panier panier, String adresseLivraison) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Création de la commande principale avec le total calculé
            String sqlCommande = "INSERT INTO Commande(id_utilisateur, date_commande, total_commande) VALUES(?, NOW(), ?)";
            try (PreparedStatement psCommande = conn.prepareStatement(sqlCommande, Statement.RETURN_GENERATED_KEYS)) {
                psCommande.setInt(1, panier.getUserId());
                psCommande.setDouble(2, panier.calculerTotal());

                if (psCommande.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }

                // 2. Récupération ID commande et création des lignes
                try (ResultSet rs = psCommande.getGeneratedKeys()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }
                    int commandeId = rs.getInt(1);

                    String sqlLigne = "INSERT INTO LigneCommande(id_commande, id_article, quantite, prix_total) VALUES(?, ?, ?, ?)";
                    try (PreparedStatement psLigne = conn.prepareStatement(sqlLigne)) {
                        for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
                            Article article = entry.getKey();
                            int quantite = entry.getValue();
                            double prixTotal = panier.calculerPrixArticle(article, quantite);

                            psLigne.setInt(1, commandeId);
                            psLigne.setInt(2, article.getIdArticle());
                            psLigne.setInt(3, quantite);
                            psLigne.setDouble(4, prixTotal);
                            psLigne.addBatch();

                            if (!updateStock(conn, article.getIdArticle(), quantite)) {
                                conn.rollback();
                                return false;
                            }
                        }
                        psLigne.executeBatch();
                    }
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
    private boolean updateStock(Connection conn, int articleId, int quantite) throws SQLException {
        String sql = "UPDATE Article SET stock = stock - ? WHERE id_article = ? AND stock >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantite);
            ps.setInt(2, articleId);
            ps.setInt(3, quantite); // Vérifie que le stock est suffisant

            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public Commande findById(int id) {
        Commande commande = null;
        String sql = "SELECT * FROM Commande WHERE id_commande = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                commande = new Commande();
                commande.setIdCommande(rs.getInt("id_commande"));

                // Utilisation directe de java.sql.Timestamp
                commande.setDateCommande(rs.getTimestamp("date_commande"));

                commande.setIdUtilisateur(rs.getInt("id_utilisateur"));
                commande.setTotalCommande(rs.getDouble("total_commande"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commande;
    }

    @Override
    public List<Commande> findByUser(int userId) {
        List<Commande> commandes = new ArrayList<Commande>();
        String sql = "SELECT * FROM Commande WHERE id_utilisateur = ? ORDER BY date_commande DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setIdCommande(rs.getInt("id_commande"));
                commande.setIdUtilisateur(rs.getInt("id_utilisateur"));

                // Utilisation directe de java.sql.Timestamp
                commande.setDateCommande(rs.getTimestamp("date_commande"));

                commande.setTotalCommande(rs.getDouble("total_commande"));
                commandes.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }

    // ... autres méthodes ...
}