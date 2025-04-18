package view;

import Controlers.ShoppingController;
import model.Article;
import model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Affichage du catalogue avec les produits 
 */
public class CatalogFrame extends JFrame {
    private final ShoppingController shoppingController;

    public CatalogFrame(Utilisateur utilisateur, ShoppingController controller) {
        this.shoppingController = controller;
        initUI(utilisateur);
    }

    private void initUI(Utilisateur utilisateur) {
        setTitle("Loro Piana - Catalogue");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        
        add(new NavigationBarPanel(), BorderLayout.NORTH);

        //  grille de produits 
        JPanel catalogPanel = new JPanel();
        catalogPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        catalogPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        catalogPanel.setLayout(new GridLayout(0, 3, 20, 20));

        List<Article> products = shoppingController.getCatalogue();
        for (Article product : products) {
            ProductCardPanel card = new ProductCardPanel(product, shoppingController);
            catalogPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(
                catalogPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel footerLabel = new JLabel("© 2025 Loro Piana - All Rights Reserved");
        footerLabel.setForeground(Color.GRAY);
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }
}
