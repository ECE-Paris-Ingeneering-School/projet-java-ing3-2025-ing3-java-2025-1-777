package Controlers;

import DAO.ArticleDAO;
import DAO.ArticleDAOImpl;
import model.Article;

import java.util.List;


public class ProductController {

    private final ArticleDAO articleDAO;

    public ProductController() {
        this.articleDAO = new ArticleDAOImpl();
    }


    public List<Article> getCatalogue(){
        return articleDAO.findAll();
    }
    public Article getProductById(int id) {
        return articleDAO.findById(id);
    }
    public boolean createArticle(Article a) {
        return articleDAO.insert(a);
    }
    public boolean updateArticle(Article a) {
        return articleDAO.update(a);
    }
    public boolean deleteArticle(int id) {
        return articleDAO.delete(id);
    }


    public boolean addProduct(Article a) {
        return articleDAO.insert(a);
    }
    public boolean updateProduct(Article a){
        return articleDAO.update(a);
    }
    public boolean deleteProduct(int id){
        return articleDAO.delete(id);
    }
}


