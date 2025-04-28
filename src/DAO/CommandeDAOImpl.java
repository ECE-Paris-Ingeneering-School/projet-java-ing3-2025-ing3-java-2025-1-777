package DAO;

import model.Commande;
import model.LigneCommande;
import model.Panier;
import Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface {@link CommandeDAO} pour la gestion des commandes dans la base de données.
 * Cette classe permet de créer, récupérer, annuler des commandes, et de mettre à jour le stock des articles.
 */
public class CommandeDAOImpl implements CommandeDAO {

    /**
     * Crée une nouvelle commande à partir du panier d'un utilisateur et d'une adresse de livraison.
     * La commande inclut également la création des lignes de commande et la mise à jour du stock des articles.
     *
     * @param panier         Le panier contenant les articles à commander.
     * @param adresseLivraison L'adresse de livraison pour la commande.
     * @return true si la commande a été créée avec succès, false sinon.
     */
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
             PreparedStatement psLigne = conn.prepareStatement(sqlLigne);
             PreparedStatement psStock = conn.prepareStatement(sqlStock)) {

            conn.setAutoCommit(false);

            // 1) Insertion de la commande
            psCommande.setInt(1, panier.getUserId());
            psCommande.setDouble(2, totalTTC);
            psCommande.setString(3, adresseLivraison);
            if (psCommande.executeUpdate() == 0) {
                conn.rollback();
                return false;
            }

            // 2) Récupération de l'ID généré pour la commande
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
                int qty = e.getValue();
                double prixLigne = e.getKey().getPrixUnitaire() * qty;

                // Insertion de la ligne de commande
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
            }

            conn.commit();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Annule une commande en mettant à jour son statut dans la base de données.
     *
     * @param commandeId L'ID de la commande à annuler.
     * @return true si la commande a été annulée avec succès, false sinon.
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
     * Récupère une commande à partir de son ID.
     *
     * @param id L'ID de la commande à récupérer.
     * @return La commande correspondante à l'ID, ou null si la commande n'est pas trouvée.
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
     * Récupère la liste des commandes passées par un utilisateur, en fonction de son ID.
     *
     * @param userId L'ID de l'utilisateur pour lequel récupérer les commandes.
     * @return Une liste de commandes passées par l'utilisateur.
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
