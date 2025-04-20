package view;

import Controlers.ProductController;
import DAO.MarqueDAO;
import DAO.MarqueDAOImpl;
import model.Article;
import model.Marque;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

/**
 * Fenêtre détail produit : affiche nom, marque, prix, matière et description longue.
 */
public class ProductDetailFrame extends JFrame {

    private final Article          product;
    private final ProductController controller;


    public ProductDetailFrame(Article product, ProductController controller) {
        this.product    = product;
        this.controller = controller;
        initUI();
    }


    private void initUI() {
        setTitle(product.getNom() + " – Détails");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        // image
        JLabel img = new JLabel();
        img.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon ic = loadProductImage(product.getImagePath());
        if (ic != null) {
            int w = 700;
            int h = ic.getIconHeight() * w / ic.getIconWidth();
            Image scaled = ic.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            img.setIcon(new ImageIcon(scaled));
        } else {
            img.setText("Aucune image");
            img.setForeground(Color.DARK_GRAY);
        }
        add(img, BorderLayout.NORTH);


        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        info.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // nom article
        JLabel name = new JLabel(product.getNom());
        name.setFont(new Font("Serif", Font.BOLD, 28));
        name.setForeground(NavigationBarPanel.TEXT_COLOR);

        // marque
        MarqueDAO mdao = new MarqueDAOImpl();
        Marque marque = mdao.findById(product.getIdMarque());
        JLabel brand = new JLabel("Marque : " + (marque!=null ? marque.getNom() : "N/C"));
        brand.setFont(new Font("SansSerif", Font.PLAIN, 18));
        brand.setForeground(Color.DARK_GRAY);

        // prix unitaire
        JLabel price = new JLabel(String.format("Prix : %.2f €", product.getPrixUnitaire()));
        price.setFont(new Font("SansSerif", Font.PLAIN, 20));
        price.setForeground(Color.DARK_GRAY);

        // matière
        String matiere = extractMaterial(product.getDescription());
        JLabel matLbl = new JLabel("Matière : " + matiere);
        matLbl.setFont(new Font("SansSerif", Font.ITALIC, 16));
        matLbl.setForeground(Color.GRAY);

        // description longue
        JTextArea desc = new JTextArea(product.getDescription());
        desc.setFont(new Font("SansSerif", Font.PLAIN, 15));
        desc.setForeground(Color.DARK_GRAY);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JScrollPane sp = new JScrollPane(desc);
        sp.setBorder(null);


        info.add(name);
        info.add(Box.createVerticalStrut(6));
        info.add(brand);
        info.add(price);
        info.add(Box.createVerticalStrut(8));
        info.add(matLbl);
        info.add(Box.createVerticalStrut(15));
        info.add(sp);

        add(info, BorderLayout.CENTER);

        // bouton retour
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        JButton back = new JButton("Retour");
        back.addActionListener(e -> dispose());
        south.add(back);
        add(south, BorderLayout.SOUTH);
    }


    private ImageIcon loadProductImage(String path) {
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


    private String extractMaterial(String d) {
        if (d == null) return "N/C";
        int dot = d.indexOf('.');
        return (dot > 0 ? d.substring(0, dot) : d).trim();
    }
}