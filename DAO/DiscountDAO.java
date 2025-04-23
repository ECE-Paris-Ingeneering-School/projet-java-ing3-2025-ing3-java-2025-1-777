package DAO;

import model.Discount;
import java.util.List;

public interface DiscountDAO {
    Discount findByArticle(int idArticle);
    List<Discount> findAll();
    boolean insert(Discount d);
    boolean update(Discount d);
    boolean delete(int idDiscount);

    Discount findById(int id);

    Discount getDiscountForArticle(int idArticle);
}
