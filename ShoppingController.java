package Controlers;


import DAO.ArticleDAO;
import DAO.ArticleDAOImpl;
import DAO.UtilisateurDAO;
import DAO.UtilisateurDAOImpl;
import model.Article;
import model.Utilisateur;
import java.util.List;

/**
 * Contrôleur principal de l’application Shopping.
 */
public class ShoppingController {
    private UtilisateurDAO utilisateurDAO;
    private ArticleDAO articleDAO;

    public ShoppingController() {
        this.utilisateurDAO = new UtilisateurDAOImpl();
        this.articleDAO = new ArticleDAOImpl();
    }

    /**
     * Authentifie un utilisateur à partir de son email et mot de passe.
     */
    public Utilisateur login(String email, String motDePasse) {
        return utilisateurDAO.findByEmailAndPassword(email, motDePasse);
    }

    
    public List<Article> getCatalogue() {
        return articleDAO.findAll();
    }

  
    public double calculerPrixArticle(Article article, int quantite) {
        double prixTotal = 0.0;
        int quantiteBulk = article.getQuantiteBulk();
        if (quantite >= quantiteBulk) {
            int nbBulk = quantite / quantiteBulk;
            int reste = quantite % quantiteBulk;
            prixTotal = nbBulk * article.getPrixBulk() + reste * article.getPrixUnitaire();
        } else {
            prixTotal = quantite * article.getPrixUnitaire();
        }
        return prixTotal;
    }
}
