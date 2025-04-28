package DAO;

import Utils.DBConnection;
import model.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface {@link ArticleDAO} pour la gestion des articles dans la base de données.
 * Cette classe permet d'effectuer des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer) sur les articles.
 */
public class ArticleDAOImpl implements ArticleDAO {

    /**
     * Récupère un article à partir de son ID dans la base de données.
     *
     * @param id L'ID de l'article à récupérer.
     * @return L'article correspondant à l'ID, ou null si l'article n'est pas trouvé.
     */
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
                    // Récupère le chemin de l'image et le stocke dans la liste
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

    /**
     * Récupère tous les articles de la base de données.
     *
     * @return Une liste de tous les articles disponibles dans la base de données.
     */
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

    /**
     * Insère un nouvel article dans la base de données.
     *
     * @param article L'article à insérer dans la base de données.
     * @return true si l'insertion a réussi, false sinon.
     */
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
            // Si une image est présente, elle est ajoutée, sinon on laisse null
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

    /**
     * Met à jour un article existant dans la base de données.
     *
     * @param article L'article avec les nouvelles informations à mettre à jour.
     * @return true si la mise à jour a réussi, false sinon.
     */
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

    /**
     * Supprime un article de la base de données en fonction de son ID.
     *
     * @param id L'ID de l'article à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
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

    /**
     * Récupère les articles en fonction de l'ID de la marque.
     *
     * @param idMarque L'ID de la marque des articles à récupérer.
     * @return Une liste d'articles correspondant à la marque spécifiée.
     */
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

    /**
     * Récupère les articles d'une catégorie spécifique.
     * Cette méthode n'est pas encore implémentée et retourne une liste vide.
     *
     * @param category La catégorie des articles à récupérer.
     * @return Une liste vide (la méthode n'est pas implémentée).
     */
    @Override
    public List<Article> findByCategory(String category) {
        return List.of();
    }

    /**
     * Récupère tous les articles disponibles.
     * Cette méthode est un alias de la méthode {@link #findAll()}.
     *
     * @return Une liste de tous les articles dans le catalogue.
     */
    @Override
    public List<Article> getAllArticles() {
        return findAll();
    }
}
