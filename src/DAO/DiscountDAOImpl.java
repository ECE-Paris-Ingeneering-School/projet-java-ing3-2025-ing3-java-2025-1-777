package DAO;

import Utils.DBConnection;
import model.Discount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface {@link DiscountDAO} pour la gestion des remises dans la base de données.
 * Cette classe permet de rechercher, insérer, mettre à jour et supprimer des remises, ainsi que de récupérer
 * les remises associées à des articles spécifiques.
 */
public class DiscountDAOImpl implements DiscountDAO {

    /**
     * Récupère la remise associée à un article spécifié par son ID.
     *
     * @param idArticle L'ID de l'article pour lequel récupérer la remise.
     * @return La remise correspondante à l'article, ou null si aucune remise n'est trouvée.
     */
    @Override
    public Discount findByArticle(int idArticle) {
        String sql = "SELECT id_discount, description, taux, id_article FROM Discount WHERE id_article = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idArticle);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Discount d = new Discount();
                    d.setIdDiscount(rs.getInt("id_discount"));
                    d.setDescription(rs.getString("description"));
                    d.setTaux(rs.getDouble("taux"));
                    d.setIdArticle(rs.getInt("id_article"));
                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupère une remise à partir de son ID.
     *
     * @param id L'ID de la remise à récupérer.
     * @return La remise correspondante à l'ID, ou null si non trouvée.
     */
    @Override
    public Discount findById(int id) {
        String sql = "SELECT id_discount, description, taux, id_article FROM Discount WHERE id_discount = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Discount d = new Discount();
                    d.setIdDiscount(rs.getInt("id_discount"));
                    d.setDescription(rs.getString("description"));
                    d.setTaux(rs.getDouble("taux"));
                    d.setIdArticle(rs.getInt("id_article"));
                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupère toutes les remises disponibles dans la base de données.
     *
     * @return Une liste de toutes les remises présentes dans la base de données.
     */
    @Override
    public List<Discount> findAll() {
        List<Discount> list = new ArrayList<>();
        String sql = "SELECT id_discount, description, taux, id_article FROM Discount";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Discount d = new Discount();
                d.setIdDiscount(rs.getInt("id_discount"));
                d.setDescription(rs.getString("description"));
                d.setTaux(rs.getDouble("taux"));
                d.setIdArticle(rs.getInt("id_article"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Insère une nouvelle remise dans la base de données.
     *
     * @param d La remise à insérer.
     * @return true si l'insertion a réussi, false sinon.
     */
    @Override
    public boolean insert(Discount d) {
        String sql = "INSERT INTO Discount(description, taux, id_article) VALUES(?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getDescription());
            ps.setDouble(2, d.getTaux());
            ps.setInt(3, d.getIdArticle());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        d.setIdDiscount(keys.getInt(1));
                    }
                }
            }
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Met à jour une remise existante dans la base de données.
     *
     * @param d La remise avec les nouvelles informations à mettre à jour.
     * @return true si la mise à jour a réussi, false sinon.
     */
    @Override
    public boolean update(Discount d) {
        String sql = "UPDATE Discount SET description = ?, taux = ? WHERE id_discount = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d.getDescription());
            ps.setDouble(2, d.getTaux());
            ps.setInt(3, d.getIdDiscount());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Supprime une remise de la base de données en fonction de son ID.
     *
     * @param idDiscount L'ID de la remise à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    @Override
    public boolean delete(int idDiscount) {
        String sql = "DELETE FROM Discount WHERE id_discount = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idDiscount);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère la remise actuellement applicable à un article spécifique.
     * Cette méthode pointe vers {@link #findByArticle(int)} pour la logique de récupération de remise.
     *
     * @param idArticle L'ID de l'article pour lequel récupérer la remise.
     * @return La remise applicable à l'article, ou null si aucune remise n'est disponible.
     */
    @Override
    public Discount getDiscountForArticle(int idArticle) {
        return findByArticle(idArticle);
    }
}
