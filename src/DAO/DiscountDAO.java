package DAO;

import model.Discount;
import java.util.List;
/**
 * DAO pour l’entité discount
 */
public interface DiscountDAO {
    Discount findById(int id);
    Discount findByArticle(int idArticle);
    List<Discount> findAll();
    boolean insert(Discount d);
    boolean update(Discount d);
    boolean delete(int idDiscount);
    Discount getDiscountForArticle(int idArticle);
}