package DAO;

import Utils.DBConnection;
import model.Discount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Implémentation JDBC du DAO DiscountDAO.
 */
public class DiscountDAOImpl implements DiscountDAO {
/**
 * Recherche la remise associée à un article.
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
 * Recherche une remise par son identifiant.
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
 * Récupère toutes les remises disponibles en BDD
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
 * Insère une nouvelle remise dans la BDD et met à jour son identifiant.
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
 * Met à jour une remise existante dans la BDD.
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
 * Supprime une remise en base par son identifiant.
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

    @Override
    public Discount getDiscountForArticle(int idArticle) {
        return findByArticle(idArticle);
    }
}