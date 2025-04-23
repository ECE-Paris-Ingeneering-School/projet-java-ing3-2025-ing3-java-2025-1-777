package view;

import Controlers.CartController;
import Controlers.ProductController;
import Controlers.ShoppingController;
import DAO.DiscountDAOImpl;
import model.Article;
import model.Discount;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

/**
 * Fenêtre de détail du produit.
 */
public class ProductDetailFrame extends JFrame {
    private final Article product;
    private final ProductController productController;
    private final CartController cartController;

    public ProductDetailFrame(Article product, ProductController productController, CartController cartController) {
        this.product = product;
        this.productController = productController;
        this.cartController = cartController;
        initUI();
    }

    private void initUI() {
        setTitle(product.getNom() + " – Détails");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        // Barre de navigation
        add(new NavigationBarPanel(cartController), BorderLayout.NORTH);

       
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon rawIcon = loadProductImage(product.getImagePath());
        if (rawIcon != null) {
            int heroWidth = getWidth();
            int heroHeight = (int)((double) rawIcon.getIconHeight() / rawIcon.getIconWidth() * heroWidth);
            Image scaled = rawIcon.getImage().getScaledInstance(heroWidth, heroHeight, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setText("Aucune image disponible");
            imageLabel.setForeground(Color.DARK_GRAY);
        }
        add(imageLabel, BorderLayout.NORTH);

    
        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        details.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nom
        JLabel nameLabel = new JLabel(product.getNom(), SwingConstants.CENTER);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 28));
        nameLabel.setForeground(Color.BLACK);
        details.add(nameLabel);
        details.add(Box.createVerticalStrut(10));

        // Prix unitaire
        JLabel unitPrice = new JLabel(
                String.format("Prix unitaire : %.2f €", product.getPrixUnitaire()),
                SwingConstants.CENTER
        );
        unitPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
        unitPrice.setFont(new Font("SansSerif", Font.PLAIN, 20));
        details.add(unitPrice);
        details.add(Box.createVerticalStrut(10));

        // Prix en vrac 
        int bulkQty = product.getQuantiteBulk();
        double bulkPrice = product.getPrixBulk();
        double perUnitBulk = bulkPrice / bulkQty;
        JLabel bulkLabel = new JLabel(
                String.format("Prix en vrac (%d pcs) : %.2f € (soit %.2f €/pc)", bulkQty, bulkPrice, perUnitBulk),
                SwingConstants.CENTER
        );
        bulkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bulkLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        bulkLabel.setForeground(NavigationBarPanel.TEXT_COLOR);
        details.add(bulkLabel);
        details.add(Box.createVerticalStrut(10));

        // Stock disponible
        JLabel stockLabel = new JLabel(
                String.format("Stock disponible : %d pièce(s)", product.getStock()),
                SwingConstants.CENTER
        );
        stockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        stockLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        stockLabel.setForeground(NavigationBarPanel.TEXT_COLOR);
        details.add(stockLabel);
        details.add(Box.createVerticalStrut(10));

        // Promotion éventuelle
        Discount promo = new DiscountDAOImpl().findByArticle(product.getIdArticle());
        if (promo != null) {
            double taux = promo.getTaux();
            double prixReduit = product.getPrixUnitaire() * (1 - taux/100);
            JLabel promoLabel = new JLabel(
                    String.format("Promotion : -%.0f%% → %.2f €", taux, prixReduit),
                    SwingConstants.CENTER
            );
            promoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            promoLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
            promoLabel.setForeground(new Color(200, 0, 0));
            details.add(promoLabel);
            details.add(Box.createVerticalStrut(10));
        }

        // Description
        JTextArea desc = new JTextArea(product.getDescription());
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        desc.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JScrollPane scrollDesc = new JScrollPane(desc);
        scrollDesc.setBorder(null);
        scrollDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        details.add(scrollDesc);

        add(details, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        footer.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        // Quantité
        JSpinner qtySpinner = new JSpinner(
                new SpinnerNumberModel(1, 1, product.getStock(), 1)
        );
        footer.add(new JLabel("Quantité :"));
        footer.add(qtySpinner);

        // Ajouter au panier
        JButton addToCart = new JButton("Ajouter au panier");
        addToCart.addActionListener((ActionEvent e) -> {
            int qty = (Integer) qtySpinner.getValue();
            boolean ok = cartController.ajouterAuPanier(product, qty);
            if (ok) {
                JOptionPane.showMessageDialog(
                        this,
                        qty + " × " + product.getNom() + " ajouté(s) au panier.",
                        "Ajouté", JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Impossible d'ajouter le produit.", "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        footer.add(addToCart);

        // Bouton Retour
        JButton back = new JButton("Retour");
        back.addActionListener(e -> dispose());
        footer.add(back);

        add(footer, BorderLayout.SOUTH);
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
}
