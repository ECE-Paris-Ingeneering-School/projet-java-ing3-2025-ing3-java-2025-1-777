package view;

import Controlers.ProductController;
import Controlers.ShoppingController;
import model.Article;
import model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CatalogFrame extends JFrame {
    private ProductController controller;
    private static final Color BACKGROUND_COLOR = new Color(253, 247, 240);

    public CatalogFrame(ProductController controller) {
        this.controller = controller;
        initUI();
    }

    public CatalogFrame(Utilisateur utilisateur, ShoppingController controller) {
      
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

        List<Article> products = controller.getCatalogue();
        for (Article product : products) {
            ProductCardPanel card = new ProductCardPanel(product, controller);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductController controller = new ProductController();
            new CatalogFrame(controller).setVisible(true);
        });
    }
}
