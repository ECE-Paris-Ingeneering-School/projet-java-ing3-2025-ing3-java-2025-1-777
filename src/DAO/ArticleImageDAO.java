package DAO;

import java.util.List;

/**
 * Interface définissant les opérations liées à la gestion des images des articles.
 * Cette interface permet de récupérer les chemins des images associées à un article donné.
 */
public interface ArticleImageDAO {

    /**
     * Récupère une liste de chemins d'images associés à un article, en fonction de son ID.
     *
     * @param articleId L'ID de l'article pour lequel on souhaite récupérer les images.
     * @return Une liste de chemins d'images associées à l'article spécifié.
     */
    List<String> findByArticleId(int articleId);
}
