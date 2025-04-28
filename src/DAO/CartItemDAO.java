package DAO;

import model.Article;
import java.util.Map;

/**
 * DAO pour gérer les lignes du panier en base.
 */
public interface CartItemDAO {
    /**
     * Charge tous les articles du panier d’un utilisateur.
     * @return map Article→quantité
     */
    Map<Article,Integer> findByUser(int userId);

    /**
     * Insère ou met à jour la quantité d’un article pour un utilisateur.
     */
    boolean saveOrUpdate(int userId, int articleId, int quantity);

    /**
     * Supprime un article du panier de l’utilisateur.
     */
    boolean delete(int userId, int articleId);

    /**
     * Vide complètement le panier de l’utilisateur.
     */
    boolean clear(int userId);
}