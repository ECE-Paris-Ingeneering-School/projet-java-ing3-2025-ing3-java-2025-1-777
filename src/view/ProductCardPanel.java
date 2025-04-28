package view;

import Controlers.CartController;
import Controlers.ProductController;
import DAO.DiscountDAOImpl;
import DAO.MarqueDAOImpl;
import DAO.MarqueDAO;
import model.Article;
import model.Discount;
import model.Marque;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

/** classe qui permet d'afficher de la grille des articles */

public class ProductCardPanel extends JPanel {
    private final Article product;
    private final ProductController controller;
    private final CartController cartController;

    public ProductCardPanel(Article product, ProductController controller, CartController cartController) {
        this.product = product;
        this.controller = controller;
        this.cartController = cartController;
        initUI();
    }
    /** Initialisation de l'interface graphique*/
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

        JPanel info = new JPanel();
        info.setBackground(Color.WHITE);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(new EmptyBorder(10,10,10,10));

        MarqueDAO marqueDAO = new MarqueDAOImpl();
        Marque m = marqueDAO.findById(product.getIdMarque());
        if (m != null) {
            JLabel brand = new JLabel("Marque : " + m.getNom());
            brand.setFont(new Font("SansSerif", Font.ITALIC, 12));
            brand.setForeground(Color.DARK_GRAY);
            info.add(brand);
            info.add(Box.createVerticalStrut(5));
        }

        JLabel name = new JLabel(product.getNom());
        name.setFont(new Font("SansSerif", Font.BOLD, 18));
        info.add(name);
        info.add(Box.createVerticalStrut(5));

        JLabel price = new JLabel(String.format("Prix : %.2f €", product.getPrixUnitaire()));
        price.setFont(new Font("SansSerif", Font.PLAIN, 16));
        info.add(price);
        info.add(Box.createVerticalStrut(5));

        Discount promo = new DiscountDAOImpl().findByArticle(product.getIdArticle());
        if (promo != null) {
            double sold = product.getPrixUnitaire() * (1 - promo.getTaux()/100.0);
            JLabel sale = new JLabel(String.format("Solde : -%.0f%% → %.2f €", promo.getTaux(), sold));
            sale.setFont(new Font("SansSerif", Font.BOLD, 14));
            sale.setForeground(Color.RED);
            info.add(sale);
            info.add(Box.createVerticalStrut(5));
        }

        add(info, BorderLayout.CENTER);

        MouseAdapter click = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                new ProductDetailFrame(product, controller, cartController).setVisible(true);
            }
            @Override public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        };
        addMouseListener(click);
        imageLabel.addMouseListener(click);
        name.addMouseListener(click);
    }
/** Images des articles*/
    private ImageIcon loadProductImage(String path) {
        if (path == null || path.isBlank()) return null;

        String resourcePath = path.startsWith("/") ? path : "/" + path;
        URL url = getClass().getResource(resourcePath);
        if (url != null) {
            return new ImageIcon(url);
        }

        File f = new File(path);
        if (f.exists()) {
            return new ImageIcon(f.getAbsolutePath());
        }

        return null;
    }
/** Place de l'image en gris si cette dernière est introuvable*/
    private Icon generatePlaceholder() {
        BufferedImage ph = new BufferedImage(300, 250, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = ph.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,0,300,250);
        g.setColor(Color.DARK_GRAY);
        g.drawString("No Image", 120, 125);
        g.dispose();
        return new ImageIcon(ph);
    }
}