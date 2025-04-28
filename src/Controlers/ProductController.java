package Controlers;

import DAO.ArticleDAO;
import DAO.ArticleDAOImpl;
import model.Article;

import java.util.List;

/**
 * Contrôleur qui gère les opérations liées aux articles produits dans l'application.
 * Ce contrôleur interagit avec la couche DAO pour récupérer, créer, mettre à jour et supprimer des articles.
 */
public class ProductController {

    // L'interface pour interagir avec les articles dans la base de données.
    private final ArticleDAO articleDAO;

    /**
     * Constructeur du contrôleur de produits.
     * Initialise l'interface ArticleDAO pour gérer les articles dans la base de données.
     */
    public ProductController() {
        this.articleDAO = new ArticleDAOImpl();
    }

    /**
     * Retourne la liste complète des articles du catalogue.
     *
     * @return Une liste d'articles disponibles dans le catalogue.
     */
    public List<Article> getCatalogue(){
        return articleDAO.findAll();
    }

    /**
     * Retourne un article spécifique à partir de son identifiant.
     *
     * @param id L'ID de l'article à récupérer.
     * @return L'article correspondant à l'ID, ou null si non trouvé.
     */
    public Article getProductById(int id) {
        return articleDAO.findById(id);
    }

    /**
     * Crée un nouvel article dans la base de données.
     *
     * @param a L'article à créer.
     * @return true si l'article a été ajouté avec succès, false sinon.
     */
    public boolean createArticle(Article a) {
        return articleDAO.insert(a);
    }

    /**
     * Met à jour un article existant dans la base de données.
     *
     * @param a L'article avec les informations mises à jour.
     * @return true si l'article a été mis à jour avec succès, false sinon.
     */
    public boolean updateArticle(Article a) {
        return articleDAO.update(a);
    }

    /**
     * Supprime un article de la base de données en utilisant son ID.
     *
     * @param id L'ID de l'article à supprimer.
     * @return true si l'article a été supprimé avec succès, false sinon.
     */
    public boolean deleteArticle(int id) {
        return articleDAO.delete(id);
    }

    /**
     * Ajoute un produit (article) à la base de données.
     * Cette méthode est similaire à la méthode createArticle.
     *
     * @param a L'article à ajouter.
     * @return true si l'article a été ajouté avec succès, false sinon.
     */
    public boolean addProduct(Article a) {
        return articleDAO.insert(a);
    }

    /**
     * Met à jour un produit (article) dans la base de données.
     * Cette méthode est similaire à la méthode updateArticle.
     *
     * @param a L'article avec les informations mises à jour.
     * @return true si l'article a été mis à jour avec succès, false sinon.
     */
    public boolean updateProduct(Article a){
        return articleDAO.update(a);
    }

    /**
     * Supprime un produit (article) de la base de données en utilisant son ID.
     * Cette méthode est similaire à la méthode deleteArticle.
     *
     * @param id L'ID de l'article à supprimer.
     * @return true si l'article a été supprimé avec succès, false sinon.
     */
    public boolean deleteProduct(int id){
        return articleDAO.delete(id);
    }
}
