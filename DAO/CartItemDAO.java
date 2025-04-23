package DAO;

import model.Article;
import java.util.Map;

/**
 * DAO pour gérer les lignes du panier en base.
 */
public interface CartItemDAO {
    
    Map<Article,Integer> findByUser(int userId);

    boolean saveOrUpdate(int userId, int articleId, int quantity);

    boolean delete(int userId, int articleId);

    boolean clear(int userId);
}
