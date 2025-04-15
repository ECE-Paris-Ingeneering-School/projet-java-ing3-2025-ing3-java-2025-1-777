package dao;

import model.Article;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOImpl implements ArticleDAO {

    @Override
    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM Article";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Article article = new Article();
                article.setId_article(rs.getInt("id_article"));
                article.setNom(rs.getString("nom"));
                article.setDescription(rs.getString("description"));
                article.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                article.setPrixBulk(rs.getDouble("prix_bulk"));
                article.setQuantiteBulk(rs.getInt("quantite_bulk"));
                article.setStock(rs.getInt("stock"));
                article.setImagePath(rs.getString("image_path")); // Nouveau champ
                articles.add(article);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return articles;
    }

    @Override
    public Article findById(int id) {
        Article article = null;
        String sql = "SELECT * FROM Article WHERE id_article = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                article = new Article();
                article.setId_article(rs.getInt("id_article"));
                article.setNom(rs.getString("nom"));
                article.setDescription(rs.getString("description"));
                article.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                article.setPrixBulk(rs.getDouble("prix_bulk"));
                article.setQuantiteBulk(rs.getInt("quantite_bulk"));
                article.setStock(rs.getInt("stock"));
                article.setImagePath(rs.getString("image_path"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return article;
    }
}
