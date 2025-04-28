package DAO;

import model.Article;
import java.util.List;

/**
 * Interface définissant les opérations de gestion des articles dans la base de données.
 * Elle étend l'interface générique {@link GenericDAO} pour les articles et fournit
 * des méthodes spécifiques pour récupérer des articles selon différents critères.
 */
public interface ArticleDAO extends GenericDAO<Article> {

    /**
     * Récupère une liste d'articles en fonction de l'ID de la marque.
     *
     * @param idMarque L'ID de la marque des articles à récupérer.
     * @return Une liste d'articles correspondant à la marque spécifiée.
     */
    List<Article> findByMarque(int idMarque);

    /**
     * Récupère une liste d'articles en fonction de leur catégorie.
     *
     * @param category La catégorie des articles à récupérer.
     * @return Une liste d'articles correspondant à la catégorie spécifiée.
     */
    List<Article> findByCategory(String category);

    /**
     * Récupère tous les articles du catalogue.
     *
     * @return Une liste de tous les articles disponibles dans le catalogue.
     */
    List<Article> getAllArticles();
}
