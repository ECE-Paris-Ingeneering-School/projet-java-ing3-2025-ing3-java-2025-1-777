package DAO;

import Utils.DBConnection;
import model.Article;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Implémentation JDBC de CartItemDAO.
 * Nécessite une table SQL CartItem(user_id INT, article_id INT, quantity INT,
 *   PRIMARY KEY(user_id,article_id))
 */
public class CartItemDAOImpl implements CartItemDAO {
    private final ArticleDAO articleDAO = new ArticleDAOImpl();

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