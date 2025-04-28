package DAO;
import model.Article;

import java.util.List;
/**
 * DAO pour l’entité Article.
 */

public interface ArticleDAO extends GenericDAO<Article> {

    List<Article> findByMarque(int idMarque);

    List<Article> findByCategory(String category);

    List<Article> getAllArticles();

}