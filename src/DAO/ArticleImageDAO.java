package DAO;
import java.util.List;
/**
 * DAO pour l’image des articles.
 */
public interface ArticleImageDAO {
    List<String> findByArticleId(int articleId);
}

