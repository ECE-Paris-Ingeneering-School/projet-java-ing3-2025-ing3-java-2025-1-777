package Controlers;


import DAO.ArticleDAO;
import DAO.ArticleDAOImpl;
import model.Article;
import java.util.List;

public class ProductController {
    private ArticleDAO articleDAO;

    public ProductController() {
        articleDAO = new ArticleDAOImpl();
    }

    public List<Article> getCatalogue() {
        return articleDAO.findAll();
    }

    public Article getProductById(int id) {
        return articleDAO.findById(id);
    }
}

