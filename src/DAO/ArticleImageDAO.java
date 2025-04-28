
package DAO;
import java.util.List;
public interface ArticleImageDAO {
    List<String> findByArticleId(int articleId);
}

