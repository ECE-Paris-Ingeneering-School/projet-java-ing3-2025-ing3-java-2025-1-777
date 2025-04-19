package view;

import Controlers.ShoppingController;
import Controlers.CartController;
import model.Article;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class ProductDetailFrame extends JFrame {
    private Article product;
    private ShoppingController controller;
    private CartController cartController;

    public ProductDetailFrame(Article product, ShoppingController controller) {
        this.product = product;
        this.controller = controller;
        this.cartController = cartController;
        initUI();
    }

    private void initUI() {
        setTitle(product.getNom() + " - Détails");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        // Grande image en haut
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon imageIcon = loadProductImage(product.getImagePath());
        if (imageIcon != null) {
            int newWidth = 700;
            int newHeight = (int)(((double) imageIcon.getIconHeight() / imageIcon.getIconWidth()) * newWidth);
            Image scaledImage = imageIcon.getImage()
                    .getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            imageLabel.setText("Aucune image disponible");
            imageLabel.setForeground(NavigationBarPanel.TEXT_COLOR);
        }
        add(imageLabel, BorderLayout.NORTH);

        // Détails du produit
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        detailsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel(product.getNom());
        nameLabel.setFont(new Font("Serif", Font.BOLD, 28));
        nameLabel.setForeground(NavigationBarPanel.TEXT_COLOR);

        JLabel priceLabel = new JLabel(String.format("%.2f €", product.getPrixUnitaire()));
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        priceLabel.setForeground(NavigationBarPanel.MENU_HOVER_COLOR);

        JTextArea descArea = new JTextArea(product.getDescription());
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        descArea.setForeground(NavigationBarPanel.TEXT_COLOR);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBorder(null);
        descScroll.getViewport().setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(priceLabel);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(descScroll);

        add(detailsPanel, BorderLayout.CENTER);

        // Footer : bouton Ajouter au panier + Retour
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JButton addToCartBtn = new JButton("Ajouter au panier");
        addToCartBtn.setFocusPainted(false);
        addToCartBtn.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        addToCartBtn.setForeground(NavigationBarPanel.MENU_HOVER_COLOR);
        addToCartBtn.setForeground(NavigationBarPanel.TEXT_COLOR);
        addToCartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addToCartBtn.addActionListener(e -> {
            Controlers.CartController.getInstance().ajouterAuPanier(product, 1);
            JOptionPane.showMessageDialog(this,
                    "« " + product.getNom() + " » a été ajouté au panier.",
                    "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        footerPanel.add(addToCartBtn);

        JButton backButton = new JButton("Retour");
        backButton.setFocusPainted(false);
        backButton.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        backButton.setForeground(NavigationBarPanel.MENU_HOVER_COLOR);
        backButton.setForeground(NavigationBarPanel.TEXT_COLOR);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> this.dispose());
        footerPanel.add(backButton);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private ImageIcon loadProductImage(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) return null;
        try {
            BufferedImage image;
            if (imagePath.startsWith("http")) {
                URL url = new URL(imagePath);
                image = ImageIO.read(url);
            } else {
                File file = new File(imagePath);
                image = ImageIO.read(file);
            }
            return new ImageIcon(image);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

