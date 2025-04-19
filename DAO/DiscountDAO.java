// DAO/DiscountDAO.java
package DAO;

import model.Discount;
import java.util.List;

public interface DiscountDAO {
    Discount getDiscountForArticle(int articleId);
    List<Discount> getAllDiscounts();
    boolean addDiscount(Discount discount);
    boolean removeDiscount(int discountId);
}