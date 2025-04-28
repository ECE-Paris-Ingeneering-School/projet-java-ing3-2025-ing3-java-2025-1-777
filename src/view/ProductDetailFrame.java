package view;

import Controlers.CartController;
import Controlers.ProductController;
import DAO.DiscountDAOImpl;
import model.Article;
import model.Discount;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.net.URL;

/** classe qui permet d'afficher les détails des produits*/
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
    /** Initialisation de l'interface graphique*/
    private void initUI() {
        setTitle(product.getNom() + " – Détails");
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        add(new NavigationBarPanel(cartController), BorderLayout.NORTH);

        JPanel west = new JPanel(new BorderLayout());
        west.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        west.setBorder(new EmptyBorder(20,20,20,20));

        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setPreferredSize(new Dimension(400, 400));

        ImageIcon icon = loadProductImage(product.getImagePath());
        if (icon != null) {
            Image img = icon.getImage()
                    .getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } else {
            imgLabel.setText("Pas d'image");
            imgLabel.setForeground(Color.DARK_GRAY);
        }
        west.add(imgLabel, BorderLayout.NORTH);
        add(west, BorderLayout.WEST);

        JPanel center = new JPanel();
        center.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(40,20,20,20));

        JLabel name = new JLabel(product.getNom());
        name.setFont(new Font("Serif", Font.BOLD, 32));
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(name);
        center.add(Box.createVerticalStrut(20));

        JLabel price = new JLabel(String.format("Prix unitaire : %.2f €", product.getPrixUnitaire()));
        price.setFont(new Font("SansSerif", Font.PLAIN, 20));
        price.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(price);
        center.add(Box.createVerticalStrut(10));

        int bulkQty = product.getQuantiteBulk();
        if (bulkQty > 0) {
            double bulkPrice = product.getPrixBulk();
            double perUnit   = bulkPrice / bulkQty;
            JLabel bulkLabel = new JLabel(
                    String.format("Prix en vrac (%d pcs) : %.2f € (%.2f €/pc)",
                            bulkQty, bulkPrice, perUnit)
            );
            bulkLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
            bulkLabel.setForeground(NavigationBarPanel.TEXT_COLOR);
            bulkLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            center.add(bulkLabel);
            center.add(Box.createVerticalStrut(10));
        }

        JLabel stock = new JLabel(
                String.format("Stock disponible : %d pièce(s)", product.getStock())
        );
        stock.setFont(new Font("SansSerif", Font.PLAIN, 18));
        stock.setForeground(NavigationBarPanel.TEXT_COLOR);
        stock.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(stock);
        center.add(Box.createVerticalStrut(10));

        Discount promo = new DiscountDAOImpl().findByArticle(product.getIdArticle());
        if (promo != null) {
            double taux    = promo.getTaux();
            double reduced = product.getPrixUnitaire() * (1 - taux/100.0);
            JLabel promoLbl = new JLabel(
                    String.format("Promotion : −%.0f%% → %.2f €", taux, reduced)
            );
            promoLbl.setFont(new Font("SansSerif", Font.BOLD, 20));
            promoLbl.setForeground(Color.RED);
            promoLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            center.add(promoLbl);
            center.add(Box.createVerticalStrut(10));
        }

        JTextArea desc = new JTextArea(product.getDescription());
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setFont(new Font("SansSerif", Font.PLAIN, 16));
        desc.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        JScrollPane scrollDesc = new JScrollPane(desc);
        scrollDesc.setBorder(null);
        scrollDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollDesc.setPreferredSize(new Dimension(600, 150));
        center.add(scrollDesc);

        add(center, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        footer.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        footer.setBorder(new EmptyBorder(10,20,20,20));

        footer.add(new JLabel("Taille :"));
        String[] tailles = { "XS", "S", "M", "L", "XL" };
        JComboBox<String> sizeCombo = new JComboBox<>(tailles);
        footer.add(sizeCombo);

        footer.add(new JLabel("Quantité :"));
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, product.getStock(), 1));
        footer.add(spinner);

        JButton addBtn = new JButton("Ajouter au panier");
        addBtn.addActionListener(e -> {
            int qty = (Integer) spinner.getValue();
            // on n'utilise QUE les deux arguments (Article, int)
            if (cartController.ajouterAuPanier(product, qty)) {
                JOptionPane.showMessageDialog(this,
                        qty + " × " + product.getNom() + " ajouté(s).",
                        "Ajouté", JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(this,
                        "Échec de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE
                );
            }
        });
        footer.add(addBtn);

        JButton back = new JButton("Retour");
        back.addActionListener(e -> dispose());
        footer.add(back);

        add(footer, BorderLayout.SOUTH);
    }

    /**
     * Charge l'image depuis le classpath (resources) ou depuis un fichier local.
     */
    private ImageIcon loadProductImage(String path) {
        if (path == null || path.isBlank()) return null;

        String rp = path.startsWith("/") ? path : "/" + path;
        URL url = getClass().getResource(rp);
        if (url != null) {
            return new ImageIcon(url);
        }

        File f = new File(path);
        if (f.exists()) {
            return new ImageIcon(f.getAbsolutePath());
        }
        return null;
    }
}