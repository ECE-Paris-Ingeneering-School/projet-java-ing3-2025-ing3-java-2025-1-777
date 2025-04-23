package view;
import Controlers.CartController;
import Controlers.ProductController;
import Controlers.ShoppingController;
import model.Article;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CatalogFrame extends JFrame {
    private final ShoppingController shop = ShoppingController.getInstance();
    private final CartController cartController = shop.getCartController();
    private final ProductController prodCtrl = new ProductController();

    public CatalogFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Loro Piana - Catalogue");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        
        add(new NavigationBarPanel(cartController), BorderLayout.NORTH);

        // Grille produits
        JPanel grid = new JPanel(new GridLayout(0, 3, 20, 20));
        grid.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        grid.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        List<Article> list = shop.getCatalogue();
        for (Article a : list) {
            grid.add(new ProductCardPanel(a, prodCtrl, cartController));
        }

        add(new JScrollPane(grid), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CatalogFrame().setVisible(true));
    }
}
