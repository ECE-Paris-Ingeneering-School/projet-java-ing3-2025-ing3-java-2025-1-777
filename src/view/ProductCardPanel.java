package view;

import Controlers.ProductController;
import model.Article;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

/**
 * Carte produit : image, nom, prix.

 */
public class ProductCardPanel extends JPanel {

    private final Article          product;
    private final ProductController controller;

    private static final int CARD_W = 300;
    private static final int CARD_H = 400;


    public ProductCardPanel(Article product, ProductController controller) {
        this.product    = product;
        this.controller = controller;
        buildUI();
    }


    private void buildUI() {
        setPreferredSize(new Dimension(CARD_W, CARD_H));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setLayout(new BorderLayout());

        // image
        JLabel imgLbl = new JLabel();
        imgLbl.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = loadImage(product.getImagePath());
        if (icon != null) {
            Image scaled = icon.getImage().getScaledInstance(CARD_W, 250, Image.SCALE_SMOOTH);
            imgLbl.setIcon(new ImageIcon(scaled));
        } else {
            imgLbl.setText("No Image");
            imgLbl.setForeground(Color.GRAY);
        }
        add(imgLbl, BorderLayout.NORTH);


        JPanel info = new JPanel();
        info.setBackground(Color.WHITE);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel name = new JLabel(product.getNom());
        name.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel price = new JLabel(String.format("%.2f €", product.getPrixUnitaire()));
        price.setFont(new Font("SansSerif", Font.PLAIN, 16));
        price.setForeground(Color.DARK_GRAY);

        info.add(name);
        info.add(Box.createVerticalStrut(5));
        info.add(price);
        add(info, BorderLayout.CENTER);

        // clic → détail produit
        MouseAdapter click = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                new ProductDetailFrame(product, controller).setVisible(true);
            }
            @Override public void mouseEntered(MouseEvent e) { setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
            @Override public void mouseExited (MouseEvent e) { setCursor(Cursor.getDefaultCursor()); }
        };
        addMouseListener(click); imgLbl.addMouseListener(click); name.addMouseListener(click);
    }


    private ImageIcon loadImage(String path) {
        if (path == null || path.isBlank()) return null;
        try {

            InputStream is = getClass().getResourceAsStream(path);
            BufferedImage img;
            if (is != null) {
                img = ImageIO.read(is);
            } else {
                img = ImageIO.read(new File(path));
            }
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

}

