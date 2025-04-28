package DAO;

import model.Commande;
import model.Panier;
import Utils.DBConnection;
import model.Discount;
import DAO.DiscountDAOImpl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Implémentation JDBC de CommandeDAO.
 */
public class CommandeDAOImpl implements CommandeDAO {
/**
 * Crée une commande et ses lignes associées, met à jour ses stocks.
 */
@Override
public boolean creerCommande(Panier panier, String adresseLivraison) {
    if (panier == null || panier.getArticles() == null) {
        throw new IllegalArgumentException("Le panier est invalide");
    }

    double totalHT = 0;
    double totalTTC = 0;

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

        // 1) Insertion de la commande
        psCommande.setInt(1, panier.getUserId());
        psCommande.setDouble(2, totalTTC);
        psCommande.setString(3, adresseLivraison);
        if (psCommande.executeUpdate() == 0) {
            conn.rollback();
            return false;
        }

        // 2) Récupération de l'ID généré de la commande
        int cmdId;
        try (ResultSet rs = psCommande.getGeneratedKeys()) {
            if (rs.next()) {
                cmdId = rs.getInt(1);
            } else {
                conn.rollback();
                return false;
            }
        }

        // 3) Insertion des lignes de commande et mise à jour du stock
        for (var e : panier.getArticles().entrySet()) {
            int artId = e.getKey().getIdArticle();
            int qty   = e.getValue();
            double prixLigne = e.getKey().getPrixUnitaire() * qty;

            // Appliquer le discount si disponible
            Discount discount = new DiscountDAOImpl().getDiscountForArticle(artId);
            if (discount != null) {
                double unitPrice = e.getKey().getPrixUnitaire();
                unitPrice = unitPrice * (1 - discount.getTaux() / 100); // Appliquer la remise
                prixLigne = unitPrice * qty; // Recalculer le prix de la ligne après application de la remise
            }

            // Insérer la ligne de commande
            psLigne.setInt(1, cmdId);
            psLigne.setInt(2, artId);
            psLigne.setInt(3, qty);
            psLigne.setDouble(4, prixLigne);
            psLigne.executeUpdate();

            // Mise à jour du stock
            psStock.setInt(1, qty);
            psStock.setInt(2, artId);
            psStock.setInt(3, qty);
            if (psStock.executeUpdate() != 1) {
                conn.rollback();
                return false;
            }

            // Mettre à jour le total HT de la commande avec le prix après remise
            totalHT += prixLigne;
        }

        // Calcul de la TVA et du total TTC
        totalTTC = totalHT * 1.20; // Ajout de la TVA (20%)

        // Mise à jour du total dans la commande
        psCommande.setDouble(2, totalTTC); // Mettre à jour le total de la commande avec la TVA
        psCommande.executeUpdate();

        conn.commit();
        return true;

    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}

    /**
 * Met à jour le stock d'un article dans la BDD
 */
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
/**
 * Annule une commande en modifiant son statut.
 */
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
/**
 * Recherche une commande par son identifiant.
 */
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
/**
 * Récupère toutes les commandes passées par un utilisateur.
 */
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
