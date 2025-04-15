package View;


import Controlers.ProductController;
import model.Article;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CatalogFrame extends JFrame {
    private ProductController controller;

    public CatalogFrame(ProductController controller) {
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle("Loro Piana - Catalogue");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barre de navigation en haut
        NavigationBarPanel navBar = new NavigationBarPanel();
        add(navBar, BorderLayout.NORTH);

        // P affichage des cards de produits
        JPanel catalogPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        catalogPanel.setBackground(Color.WHITE);
        catalogPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        List<Article> products = controller.getCatalogue();
        for (Article product : products) {
            ProductCardPanel card = new ProductCardPanel(product, controller);
            catalogPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(catalogPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JLabel footerLabel = new JLabel("© 2025 Loro Piana - All Rights Reserved");
        footerLabel.setForeground(Color.GRAY);
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductController controller = new ProductController();
            new CatalogFrame(controller).setVisible(true);
        });
    }
}
