package view;

import Controlers.ProductController;
import Controlers.ShoppingController;
import model.Article;
import model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CatalogFrame extends JFrame {
    private ShoppingController shoppingController; // Un seul contrôleur nécessaire
    private static final Color BACKGROUND_COLOR = new Color(253, 247, 240);

    // Constructeur principal
    public CatalogFrame(ShoppingController shoppingController) {
        this.shoppingController = shoppingController;
        initUI();
    }

    // Constructeur avec utilisateur (à implémenter plus tard)
    public CatalogFrame(Utilisateur utilisateur, ShoppingController controller) {
        this.shoppingController = controller;
        initUI();
        // Ajoutez ici les personnalisations pour l'utilisateur connecté
    }

    private void initUI() {
        setTitle("Loro Piana - Catalogue");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        NavigationBarPanel navBar = new NavigationBarPanel();
        add(navBar, BorderLayout.NORTH);

        JPanel catalogPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        catalogPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        catalogPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // BOUCLE CORRIGÉE - Utilisation de shoppingController
        List<Article> products = shoppingController.getCatalogue();
        for (Article product : products) {
            ProductCardPanel card = new ProductCardPanel(product, shoppingController);
            catalogPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(catalogPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
