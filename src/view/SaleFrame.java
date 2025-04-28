// src/view/SaleFrame.java
package view;

import Controlers.ProductController;
import Controlers.ShoppingController;
import DAO.DiscountDAO;
import DAO.DiscountDAOImpl;
import model.Article;
import model.Discount;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SaleFrame extends JFrame {
    public SaleFrame() {
        setTitle("Articles en Soldes – Loro Piana");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new NavigationBarPanel(ShoppingController.getInstance().getCartController()),
                BorderLayout.NORTH);

        JPanel grid = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        grid.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        ProductController prodCtrl = new ProductController();
        DiscountDAO discDao = new DiscountDAOImpl();
        List<Article> all = prodCtrl.getCatalogue();
        for (Article a : all) {
            Discount d = discDao.findByArticle(a.getIdArticle());
            if (d != null && d.getTaux() > 0) {
                // on réutilise ProductCardPanel, qui affiche prix unitaire + stock
                grid.add(new ProductCardPanel(a, prodCtrl, ShoppingController.getInstance().getCartController()));
            }
        }

        add(new JScrollPane(grid), BorderLayout.CENTER);
    }
}
