package view;

import Controlers.ProductController;
import Controlers.ShoppingController;
import model.Article;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Fenêtre catalogue.

 */
public class CatalogFrame extends JFrame {

    private final ShoppingController shoppingController;
    private final ProductController  productController;


    public CatalogFrame(ProductController productController) {
        this.shoppingController = new ShoppingController();
        this.productController  = new ProductController();
        initUI();
    }


    private void initUI() {
        setTitle("Loro Piana - Catalogue");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        setLayout(new BorderLayout());


        add(new NavigationBarPanel(), BorderLayout.NORTH);


        JPanel catalogPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        catalogPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        catalogPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        List<Article> products = shoppingController.getCatalogue();
        for (Article p : products) {
            catalogPanel.add(new ProductCardPanel(p, productController));
        }

        JScrollPane scroll = new JScrollPane(
                catalogPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setBorder(null);
        scroll.getViewport().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        add(scroll, BorderLayout.CENTER);

        //footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footer.add(new JLabel("© 2025 Loro Piana - All Rights Reserved"));
        add(footer, BorderLayout.SOUTH);
    }
}
