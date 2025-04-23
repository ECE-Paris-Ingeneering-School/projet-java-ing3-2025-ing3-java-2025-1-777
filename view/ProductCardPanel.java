package view;

import Controlers.CartController;
import Controlers.ProductController;
import DAO.DiscountDAO;
import DAO.DiscountDAOImpl;
import DAO.MarqueDAO;
import DAO.MarqueDAOImpl;
import model.Article;
import model.Discount;
import model.Marque;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

/**
 *  carte produit
 */
public class ProductCardPanel extends JPanel {
    private final Article product;
    private final ProductController controller;
    private final CartController cartController;

    public ProductCardPanel(Article product,ProductController controller,CartController cartController) {
        this.product = product;
        this.controller = controller;
        this.cartController = cartController;
        initUI();
    }

    private void initUI() {
        setPreferredSize(new Dimension(300, 400));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setLayout(new BorderLayout());

        
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = loadProductImage(product.getImagePath());
        if (icon != null) {
            Image scaled = icon.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setIcon(generatePlaceholder());
        }
        add(imageLabel, BorderLayout.NORTH);

        // Infos produit
        JPanel info = new JPanel();
        info.setBackground(Color.WHITE);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(10, 10, 10, 10));

        //Marque
        MarqueDAO marqueDAO = new MarqueDAOImpl();
        Marque marque = marqueDAO.findById(product.getIdMarque());
        if (marque != null) {
            JLabel brandLabel = new JLabel("Marque : " + marque.getNom());
            brandLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
            brandLabel.setForeground(Color.DARK_GRAY);
            info.add(brandLabel);
            info.add(Box.createVerticalStrut(5));
        }

        //Nom
        JLabel nameLabel = new JLabel(product.getNom());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nameLabel.setForeground(Color.BLACK);
        info.add(nameLabel);
        info.add(Box.createVerticalStrut(5));

        //Prix unitaire
        JLabel priceLabel = new JLabel(String.format("Prix : %.2f €", product.getPrixUnitaire()));
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        priceLabel.setForeground(Color.DARK_GRAY);
        info.add(priceLabel);
        info.add(Box.createVerticalStrut(5));

        //Prix en solde
        DiscountDAO discountDAO = new DiscountDAOImpl();
        Discount promo = discountDAO.findByArticle(product.getIdArticle());
        if (promo != null) {
            double taux = promo.getTaux();
            double soldPrice = product.getPrixUnitaire() * (1 - taux / 100.0);
            JLabel saleLabel = new JLabel(
                    String.format("Solde : -%.0f%% → %.2f €", taux, soldPrice)
            );
            saleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            saleLabel.setForeground(Color.RED);
            info.add(saleLabel);
            info.add(Box.createVerticalStrut(5));
        }

        add(info, BorderLayout.CENTER);

        MouseAdapter click = new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
                new ProductDetailFrame(product, controller, cartController).setVisible(true);
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
        addMouseListener(click);
        imageLabel.addMouseListener(click);
        nameLabel.addMouseListener(click);
    }

    private ImageIcon loadProductImage(String path) {
        if (path == null || path.isBlank()) return null;
        try {
            BufferedImage img = path.startsWith("http")
                    ? ImageIO.read(new URL(path))
                    : ImageIO.read(new File(path));
            return new ImageIcon(img);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Icon generatePlaceholder() {
        BufferedImage ph = new BufferedImage(300, 250, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = ph.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 300, 250);
        g.setColor(Color.DARK_GRAY);
        g.drawString("No Image", 120, 125);
        g.dispose();
        return new ImageIcon(ph);
    }
}

