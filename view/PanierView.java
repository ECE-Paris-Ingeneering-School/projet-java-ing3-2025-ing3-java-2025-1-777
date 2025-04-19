package view;

import Controlers.CartController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class PanierView extends JFrame {
    private CartController cartController;

    public PanierView(CartController cartController) {
        this.cartController = cartController;
        initUI();
        setTitle("Panier – Loro Piana");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initUI() {
        // Couleur de fond générale
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        // === HEADER AVEC LOGO TEXTE ===
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        header.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        // Logo texte
        JLabel logoText = new JLabel("Loro Piana");
        logoText.setFont(new Font("Snell Roundhand", Font.PLAIN, 30));
        logoText.setForeground(NavigationBarPanel.TEXT_COLOR);
        // facultatif : ajouter un petit espacement interne
        logoText.setBorder(new EmptyBorder(5, 10, 5, 10));

        header.add(logoText);
        add(header, BorderLayout.NORTH);

        // === LISTE DES ARTICLES ===
        JPanel itemsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        itemsPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        itemsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        cartController.getPanier().getArticles().forEach((article, quantite) -> {
            JPanel itemPanel = new JPanel(new BorderLayout(10, 0));
            itemPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
            itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, NavigationBarPanel.LINE_COLOR));

            JLabel nameLabel = new JLabel(article.getNom() + " x" + quantite);
            nameLabel.setForeground(NavigationBarPanel.TEXT_COLOR);
            nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            itemPanel.add(nameLabel, BorderLayout.CENTER);

            JButton deleteButton = new JButton("Supprimer");
            deleteButton.setFocusPainted(false);
            deleteButton.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
            deleteButton.setForeground(NavigationBarPanel.TEXT_COLOR);
            deleteButton.setBorder(BorderFactory.createLineBorder(NavigationBarPanel.LINE_COLOR));
            deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            deleteButton.addActionListener(e -> {
                cartController.supprimerArticle(article);
                getContentPane().removeAll();
                initUI();
                revalidate();
                repaint();
            });
            itemPanel.add(deleteButton, BorderLayout.EAST);

            itemsPanel.add(itemPanel);
        });

        add(new JScrollPane(itemsPanel) {{
            setBorder(null);
            getViewport().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        }}, BorderLayout.CENTER);

        // === FOOTER : TOTAL & VALIDATION ===
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        footerPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        footerPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel totalLabel = new JLabel("Total : " + cartController.getPanier().calculerTotal() + " €");
        totalLabel.setForeground(NavigationBarPanel.TEXT_COLOR);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        footerPanel.add(totalLabel);

        JButton checkoutButton = new JButton("Valider la commande");
        checkoutButton.setFocusPainted(false);
        checkoutButton.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        checkoutButton.setForeground(NavigationBarPanel.MENU_HOVER_COLOR);
        checkoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        checkoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkoutButton.addActionListener(e -> new CheckoutFrame(cartController).setVisible(true));
        footerPanel.add(checkoutButton);

        add(footerPanel, BorderLayout.SOUTH);
    }
}
