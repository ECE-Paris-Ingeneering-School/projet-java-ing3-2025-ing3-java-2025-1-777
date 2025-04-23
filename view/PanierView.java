package view;

import Controlers.CartController;
import model.Article;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PanierView extends JFrame {
    private final CartController cartController;
    private final JPanel linesPanel = new JPanel();
    private final List<Line> lines = new ArrayList<>();

    private final JLabel htLabel  = new JLabel();
    private final JLabel tvaLabel = new JLabel();
    private final JLabel ttcLabel = new JLabel();

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
        setLayout(new BorderLayout());
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        // HEADER
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        header.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        JLabel logo = new JLabel("Loro Piana");
        logo.setFont(new Font("Snell Roundhand", Font.PLAIN, 30));
        logo.setForeground(NavigationBarPanel.TEXT_COLOR);
        header.add(logo);
        add(header, BorderLayout.NORTH);
        JLabel title = new JLabel("Récapitulatif de votre panier", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        //lignes
        linesPanel.setLayout(new BoxLayout(linesPanel, BoxLayout.Y_AXIS));
        linesPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        linesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rebuildLines();
        add(new JScrollPane(linesPanel) {{
            setBorder(null);
            getViewport().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        }}, BorderLayout.CENTER);

        // FOOTER
        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        footer.setBorder(new EmptyBorder(10, 20, 10, 20));

        footer.add(Box.createVerticalStrut(15));

        // Total
        htLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tvaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ttcLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        footer.add(htLabel);
        footer.add(Box.createVerticalStrut(5));
        footer.add(tvaLabel);
        footer.add(Box.createVerticalStrut(5));
        footer.add(ttcLabel);
        footer.add(Box.createVerticalStrut(15));

        // Valider la commande
        JButton checkout = new JButton("Valider la commande");
        checkout.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        checkout.setForeground(NavigationBarPanel.MENU_HOVER_COLOR);
        checkout.setBorder(BorderFactory.createLineBorder(NavigationBarPanel.LINE_COLOR));
        checkout.addActionListener(e -> new CheckoutFrame(cartController).setVisible(true));
        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnWrap.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        btnWrap.add(checkout);
        footer.add(btnWrap);

        add(footer, BorderLayout.SOUTH);

        updateTotals();
    }

    private void rebuildLines() {
        linesPanel.removeAll();
        lines.clear();
        for (var entry : cartController.getPanier().getArticles().entrySet()) {
            Line line = new Line(entry.getKey(), entry.getValue());
            lines.add(line);
            linesPanel.add(line.panel);
        }
        linesPanel.revalidate();
        linesPanel.repaint();
    }

    private void updateTotals() {
        double ht  = lines.stream().mapToDouble(l -> l.currentLineTotal).sum();
        double tva = ht * 0.20;
        double ttc = ht + tva;
        htLabel.setText(String.format("Sous‑total (HT) : %.2f €", ht));
        tvaLabel.setText(      String.format("TVA (20%%) : %.2f €", tva));
        ttcLabel.setText(      String.format("Total (TTC) : %.2f €", ttc));
    }

    
    private class Line {
        final Article art;
        final JPanel panel;
        final JSpinner spinner;
        final JLabel lineDetail;
        final JLabel bulkLineRed;
        double currentLineTotal;

        Line(Article art, int initQty) {
            this.art = art;
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
            panel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, NavigationBarPanel.LINE_COLOR));

        
            JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            row1.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

            
            JLabel thumb = new JLabel();
            ImageIcon icon = loadThumbnail(art.getImagePath(), 50, 50);
            thumb.setIcon(icon);
            row1.add(thumb);

            spinner = new JSpinner(new SpinnerNumberModel(initQty, 0, art.getStock(), 1));
            spinner.addChangeListener(new ChangeListener() {
                @Override 
                public void stateChanged(ChangeEvent e) {
                    int q = (Integer) spinner.getValue();
                    cartController.supprimerArticle(art);
                    if (q>0) cartController.ajouterAuPanier(art, q);
                    updateLine();
                    updateTotals();
                }
            });
            row1.add(spinner);

            JLabel name = new JLabel(art.getNom());
            name.setForeground(NavigationBarPanel.TEXT_COLOR);
            row1.add(name);

            JLabel unit = new JLabel(String.format("(%.2f € chacun)", art.getPrixUnitaire()));
            unit.setForeground(NavigationBarPanel.TEXT_COLOR);
            row1.add(unit);

            JButton del = new JButton("Supprimer");
            del.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
            del.setForeground(NavigationBarPanel.TEXT_COLOR);
            del.setBorder(BorderFactory.createLineBorder(NavigationBarPanel.LINE_COLOR));
            del.addActionListener(e -> {
                cartController.supprimerArticle(art);
                rebuildLines();
                updateTotals();
            });
            row1.add(del);

            panel.add(row1);

            
            bulkLineRed = new JLabel();
            bulkLineRed.setForeground(Color.RED);
            bulkLineRed.setFont(bulkLineRed.getFont().deriveFont(Font.BOLD));
            panel.add(bulkLineRed);

            
            lineDetail = new JLabel();
            lineDetail.setForeground(NavigationBarPanel.TEXT_COLOR);
            panel.add(lineDetail);

            updateLine();
        }

        void updateLine() {
            int q = (Integer) spinner.getValue();
            int bulkQty = art.getQuantiteBulk();
            double unitPrice = art.getPrixUnitaire();
            double bulkPrice = art.getPrixBulk();

            if (bulkQty > 0 && q >= bulkQty) {
                int packs = q / bulkQty;
                int rest = q % bulkQty;
                double totalPack = packs * bulkPrice;
                double totalRest = rest  * unitPrice;
                currentLineTotal = totalPack + totalRest;

                bulkLineRed.setText(
                        String.format("Prix pour %d pièces (lot) : %.2f €", bulkQty, bulkPrice)
                );
                lineDetail.setText(
                        String.format("→ %d×%.2f€ + %d×%.2f€ = %.2f€",
                                packs, bulkPrice, rest, unitPrice, currentLineTotal)
                );
            } else {
                currentLineTotal = q * unitPrice;
                bulkLineRed.setText(" ");
                lineDetail.setText(
                        String.format("→ %d×%.2f€ = %.2f€", q, unitPrice, currentLineTotal)
                );
            }
        }

        private ImageIcon loadThumbnail(String path, int w, int h) {
            if (path == null || path.isBlank()) {
                BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = img.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, w, h);
                g.dispose();
                return new ImageIcon(img);
            }
            try {
                BufferedImage original = path.startsWith("http")
                        ? ImageIO.read(new URL(path))
                        : ImageIO.read(new File(path));
                Image scaled = original.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
