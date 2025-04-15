package dao;


import model.Article;
import java.util.List;

public interface ArticleDAO {
    List<Article> findAll();
    Article findById(int id);
    
}
