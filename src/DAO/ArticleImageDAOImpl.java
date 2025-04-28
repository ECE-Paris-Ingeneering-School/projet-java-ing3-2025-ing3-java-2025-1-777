package DAO;

import Utils.DBConnection;
import java.sql.*;
import java.util.*;

/**
 * Implémentation de l'interface {@link ArticleImageDAO} pour la gestion des images des articles.
 * Cette classe permet de récupérer les chemins des images associés à un article dans la base de données.
 */
public class ArticleImageDAOImpl implements ArticleImageDAO {

    /**
     * Récupère une liste des chemins d'images associés à un article, en fonction de son ID.
     *
     * @param articleId L'ID de l'article pour lequel on souhaite récupérer les chemins d'images.
     * @return Une liste de chaînes de caractères représentant les chemins des images de l'article.
     */
    @Override
    public List<String> findByArticleId(int articleId) {
        List<String> path = new ArrayList<>();
        String sql = "SELECT image_path FROM Article WHERE id_article = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, articleId);
            try (ResultSet rs = ps.executeQuery()) {
                // Ajoute chaque chemin d'image trouvé dans la liste
                while (rs.next()) {
                    path.add(rs.getString("image_path"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return path;
    }
}
