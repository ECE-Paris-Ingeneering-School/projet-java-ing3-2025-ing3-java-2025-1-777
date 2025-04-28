package DAO;

import Utils.DBConnection;
import model.Article;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Implémentation JDBC de CartItemDAO.
 */
public class CartItemDAOImpl implements CartItemDAO {
    private final ArticleDAO articleDAO = new ArticleDAOImpl();
/**
 * Récupère tous les items du panier pour l'utilisateur .
 */
    @Override
    public Map<Article,Integer> findByUser(int userId) {
        String sql = "SELECT article_id, quantity FROM CartItem WHERE user_id = ?";
        Map<Article,Integer> map = new HashMap<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int artId = rs.getInt("article_id");
                    int qty   = rs.getInt("quantity");
                    var art = articleDAO.findById(artId);
                    if (art != null) map.put(art, qty);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return map;
    }
/**
 * Insère un nouvel article ou met à jour sa quantité.
 */
    @Override
    public boolean saveOrUpdate(int userId, int articleId, int quantity) {
        String sql = """
            INSERT INTO CartItem(user_id, article_id, quantity)
            VALUES (?,?,?)
            ON DUPLICATE KEY UPDATE quantity = ?
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, articleId);
            ps.setInt(3, quantity);
            ps.setInt(4, quantity);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
/**
 * Supprime un article du panier
 */
    @Override
    public boolean delete(int userId, int articleId) {
        String sql = "DELETE FROM CartItem WHERE user_id = ? AND article_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, articleId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
/**
 * Vide complètement le panier
 */
    @Override
    public boolean clear(int userId) {
        String sql = "DELETE FROM CartItem WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() >= 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}