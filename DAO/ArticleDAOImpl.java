package DAO;

import model.Article;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l’interface ArticleDAO utilisant JDBC.
 */
public class ArticleDAOImpl implements ArticleDAO {

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
                article.setIdArticle(rs.getInt("id_article"));
                article.setNom(rs.getString("nom"));
                article.setDescription(rs.getString("description"));
                article.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                article.setPrixBulk(rs.getDouble("prix_bulk"));
                article.setQuantiteBulk(rs.getInt("quantite_bulk"));
                article.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }

    @Override
    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM Article";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Article article = new Article();
                article.setIdArticle(rs.getInt("id_article"));
                article.setNom(rs.getString("nom"));
                article.setDescription(rs.getString("description"));
                article.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                article.setPrixBulk(rs.getDouble("prix_bulk"));
                article.setQuantiteBulk(rs.getInt("quantite_bulk"));
                article.setStock(rs.getInt("stock"));
                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public boolean insert(Article article) {
        String sql = "INSERT INTO Article(nom, description, prix_unitaire, prix_bulk, quantite_bulk, stock) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, article.getNom());
            ps.setString(2, article.getDescription());
            ps.setDouble(3, article.getPrixUnitaire());
            ps.setDouble(4, article.getPrixBulk());
            ps.setInt(5, article.getQuantiteBulk());
            ps.setInt(6, article.getStock());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Article article) {
        String sql = "UPDATE Article SET nom=?, description=?, prix_unitaire=?, prix_bulk=?, quantite_bulk=?, stock=? WHERE id_article=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, article.getNom());
            ps.setString(2, article.getDescription());
            ps.setDouble(3, article.getPrixUnitaire());
            ps.setDouble(4, article.getPrixBulk());
            ps.setInt(5, article.getQuantiteBulk());
            ps.setInt(6, article.getStock());
            ps.setInt(7, article.getIdArticle());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Article WHERE id_article = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Article> findByMarque(int idMarque) {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.* FROM Article a JOIN Article_Marque am ON a.id_article = am.id_article WHERE am.id_marque = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMarque);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Article article = new Article();
                article.setIdArticle(rs.getInt("id_article"));
                article.setNom(rs.getString("nom"));
                article.setDescription(rs.getString("description"));
                article.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                article.setPrixBulk(rs.getDouble("prix_bulk"));
                article.setQuantiteBulk(rs.getInt("quantite_bulk"));
                article.setStock(rs.getInt("stock"));
                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<Article> getAllArticles() {
        return List.of();
    }
}
