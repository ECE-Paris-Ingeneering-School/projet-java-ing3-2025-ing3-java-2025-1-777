package DAO;

import Utils.DBConnection;
import java.sql.*;
import java.util.*;

/**
 * Implémentation JDBC du DAO ArticleImageDAO.
 * Permet de récupérer les chemins d'images associés à un article.
 */
public class ArticleImageDAOImpl implements ArticleImageDAO {
/**
 * Récupère la liste des chemins d'image pour un article .
 */
    @Override
    public List<String> findByArticleId(int articleId) {
        List<String> path = new ArrayList<>();
        String sql = "SELECT image_path FROM Article  WHERE id_article = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, articleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    path.add(rs.getString("image_path"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return path;
    }
}
