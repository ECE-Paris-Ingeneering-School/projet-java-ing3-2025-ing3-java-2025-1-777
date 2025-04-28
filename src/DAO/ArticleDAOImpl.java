package DAO;

import Utils.DBConnection;
import model.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOImpl implements ArticleDAO {

    @Override
    public Article findById(int id) {
        Article article = null;
        String sql = "SELECT * FROM Article WHERE id_article = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    article = new Article();
                    article.setIdArticle(rs.getInt("id_article"));
                    article.setNom(rs.getString("nom"));
                    article.setDescription(rs.getString("description"));
                    article.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                    article.setPrixBulk(rs.getDouble("prix_bulk"));
                    article.setQuantiteBulk(rs.getInt("quantite_bulk"));
                    article.setStock(rs.getInt("stock"));
                    article.setIdMarque(rs.getInt("id_marque"));
                    // on récupère le chemin unique et on le stocke dans la liste
                    String img = rs.getString("image_path");
                    if (img != null) {
                        List<String> paths = new ArrayList<>();
                        paths.add(img);
                        article.setImagePaths(paths);
                    }
                }
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
                Article a = new Article();
                a.setIdArticle(rs.getInt("id_article"));
                a.setNom(rs.getString("nom"));
                a.setDescription(rs.getString("description"));
                a.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                a.setPrixBulk(rs.getDouble("prix_bulk"));
                a.setQuantiteBulk(rs.getInt("quantite_bulk"));
                a.setStock(rs.getInt("stock"));
                a.setIdMarque(rs.getInt("id_marque"));
                String img = rs.getString("image_path");
                if (img != null) {
                    List<String> paths = new ArrayList<>();
                    paths.add(img);
                    a.setImagePaths(paths);
                }
                articles.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public boolean insert(Article article) {
        String sql = "INSERT INTO Article "
                + "(nom, description, prix_unitaire, prix_bulk, quantite_bulk, stock, id_marque, image_path) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, article.getNom());
            ps.setString(2, article.getDescription());
            ps.setDouble(3, article.getPrixUnitaire());
            ps.setDouble(4, article.getPrixBulk());
            ps.setInt(5, article.getQuantiteBulk());
            ps.setInt(6, article.getStock());
            ps.setInt(7, article.getIdMarque());
            // on prend la première image du panier, ou null
            if (article.getImagePath() != null) {
                ps.setString(8, article.getImagePath());
            } else {
                ps.setNull(8, Types.VARCHAR);
            }

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        article.setIdArticle(keys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Article article) {
        String sql = "UPDATE Article SET "
                + "nom = ?, description = ?, prix_unitaire = ?, prix_bulk = ?, "
                + "quantite_bulk = ?, stock = ?, id_marque = ?, image_path = ? "
                + "WHERE id_article = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, article.getNom());
            ps.setString(2, article.getDescription());
            ps.setDouble(3, article.getPrixUnitaire());
            ps.setDouble(4, article.getPrixBulk());
            ps.setInt(5, article.getQuantiteBulk());
            ps.setInt(6, article.getStock());
            ps.setInt(7, article.getIdMarque());
            if (article.getImagePath() != null) {
                ps.setString(8, article.getImagePath());
            } else {
                ps.setNull(8, Types.VARCHAR);
            }
            ps.setInt(9, article.getIdArticle());

            return ps.executeUpdate() == 1;
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
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Article> findByMarque(int idMarque) {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM Article WHERE id_marque = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMarque);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Article a = new Article();
                    a.setIdArticle(rs.getInt("id_article"));
                    a.setNom(rs.getString("nom"));
                    a.setDescription(rs.getString("description"));
                    a.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                    a.setPrixBulk(rs.getDouble("prix_bulk"));
                    a.setQuantiteBulk(rs.getInt("quantite_bulk"));
                    a.setStock(rs.getInt("stock"));
                    a.setIdMarque(rs.getInt("id_marque"));
                    String img = rs.getString("image_path");
                    if (img != null) {
                        List<String> paths = new ArrayList<>();
                        paths.add(img);
                        a.setImagePaths(paths);
                    }
                    articles.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<Article> findByCategory(String category) {
        return List.of();
    }

    @Override
    public List<Article> getAllArticles() {
        return findAll();
    }
}