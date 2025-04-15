package View;

import Controlers.ProductController;
import model.Article;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class ProductDetailFrame extends JFrame {
    private Article product;
    private ProductController controller;

    public ProductDetailFrame(Article product, ProductController controller) {
        this.product = product;
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle(product.getNom() + " - Détails");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Grande image en haut
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon imageIcon = loadProductImage(product.getImagePath());
        if (imageIcon != null) {
            int newWidth = 700;
            int newHeight = (int)(((double) imageIcon.getIconHeight() / imageIcon.getIconWidth()) * newWidth);
            Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            imageLabel.setText("Aucune image disponible");
            imageLabel.setForeground(Color.DARK_GRAY);
        }
        add(imageLabel, BorderLayout.NORTH);

        // Détails du produit
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel nameLabel = new JLabel(product.getNom());
        nameLabel.setFont(new Font("Serif", Font.BOLD, 28));
        nameLabel.setForeground(Color.BLACK);

        JLabel priceLabel = new JLabel(String.format("%.2f €", product.getPrixUnitaire()));
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        priceLabel.setForeground(Color.DARK_GRAY);

        JTextArea descArea = new JTextArea(product.getDescription());
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        descArea.setForeground(Color.DARK_GRAY);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(Color.WHITE);

        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBorder(null);

        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(priceLabel);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(descScroll);

        add(detailsPanel, BorderLayout.CENTER);

        // Bouton Retour
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        JButton backButton = new JButton("Retour");
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
