package DAO;
import model.Article;

import java.util.List;

/**
 * Interface DAO pour l’entité Article.
 */
public interface ArticleDAO extends GenericDAO<Article> {
    // Ajoutez d'autres méthodes spécifiques, par exemple une recherche par marque
    List<Article> findByMarque(int idMarque);

    List<Article> getAllArticles();
}
