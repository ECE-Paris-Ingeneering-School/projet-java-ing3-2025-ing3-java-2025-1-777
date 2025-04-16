package view;
import Controlers.ProductController;
import model.Article;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Controlers.ShoppingController;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class ProductCardPanel extends JPanel {
    private Article product;
    private ProductController controller;
    private ShoppingController shoppingController;  // Ajoutez cette ligne

    private static final int CARD_WIDTH = 300;
    private static final int CARD_HEIGHT = 400;

    public ProductCardPanel(Article product, ShoppingController shoppingController) {
        this.product = product;
        this.shoppingController = shoppingController;
        initUI();
    }

    private void initUI() {
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setLayout(new BorderLayout());

        // Image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon productIcon = loadProductImage(product.getImagePath());
        if (productIcon != null) {
            Image scaledImage = productIcon.getImage().getScaledInstance(CARD_WIDTH, 250, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            imageLabel.setIcon(generatePlaceholder());
        }
        add(imageLabel, BorderLayout.NORTH);

        //  nom et prix du produit
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel(product.getNom());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nameLabel.setForeground(Color.BLACK);

        JLabel priceLabel = new JLabel(String.format("%.2f €", product.getPrixUnitaire()));
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        priceLabel.setForeground(Color.DARK_GRAY);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(priceLabel);
        add(infoPanel, BorderLayout.CENTER);

     
        MouseAdapter clickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ProductDetailFrame(product, shoppingController).setVisible(true);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        };
        addMouseListener(clickListener);
        imageLabel.addMouseListener(clickListener);
        nameLabel.addMouseListener(clickListener);
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

    private Icon generatePlaceholder() {
        BufferedImage placeholder = new BufferedImage(CARD_WIDTH, 250, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = placeholder.createGraphics();
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, CARD_WIDTH, 250);
        g2.setColor(Color.DARK_GRAY);
        g2.drawString("No Image", CARD_WIDTH / 2 - 30, 125);
        g2.dispose();
        return new ImageIcon(placeholder);
    }
}


