package view;

import Controlers.ProductController;
import DAO.MarqueDAO;
import DAO.MarqueDAOImpl;
import model.Article;
import model.Marque;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * BrandsFrame : liste des marques
 */
public class BrandsFrame extends JFrame {

    private final MarqueDAO        marqueDAO        = new MarqueDAOImpl();
    private final ProductController prodCtrl         = new ProductController();
    private final JPanel           brandListPanel   = new JPanel();
    private final JPanel           productsGridPanel= new JPanel();

    public BrandsFrame() {
        setTitle("Loro Piana – Marques");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);


        add(new NavigationBarPanel(), BorderLayout.NORTH);


        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(250);
        split.setResizeWeight(0);
        split.setBorder(null);
        split.setBackground(NavigationBarPanel.BACKGROUND_COLOR);


        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JLabel title = new JLabel("Marques");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(new EmptyBorder(10, 15, 10, 15));
        left.add(title, BorderLayout.NORTH);

        brandListPanel.setLayout(new BoxLayout(brandListPanel, BoxLayout.Y_AXIS));
        brandListPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        brandListPanel.setBorder(new EmptyBorder(0, 15, 0, 15));

        List<Marque> marques = marqueDAO.findAll();
        for (Marque m : marques) {
            JLabel lbl = new JLabel(m.getNom());
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
            lbl.setForeground(NavigationBarPanel.TEXT_COLOR);
            lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lbl.setBorder(new EmptyBorder(8, 0, 8, 0));

            lbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    reloadProducts(m);

                    for (Component c : brandListPanel.getComponents()) {
                        if (c instanceof JComponent jc) {
                            jc.setOpaque(false);
                            jc.setBackground(null);
                        }
                    }

                    lbl.setOpaque(true);
                    lbl.setBackground(new Color(230, 220, 200));
                }
            });
            brandListPanel.add(lbl);
        }

        left.add(new JScrollPane(
                        brandListPanel,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
                BorderLayout.CENTER);

        split.setLeftComponent(left);


        productsGridPanel.setLayout(new GridLayout(0, 3, 20, 20));
        productsGridPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        productsGridPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane sp = new JScrollPane(
                productsGridPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        sp.setBorder(null);
        sp.getViewport().setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        split.setRightComponent(sp);
        add(split, BorderLayout.CENTER);

        // footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        footer.setBorder(new EmptyBorder(10,10,10,10));
        footer.add(new JLabel("© 2025 Loro Piana – All Rights Reserved"));
        add(footer, BorderLayout.SOUTH);


        if (!marques.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                Component first = brandListPanel.getComponent(0);
                first.dispatchEvent(new MouseEvent(
                        first,
                        MouseEvent.MOUSE_CLICKED,
                        System.currentTimeMillis(),
                        0, 1, 1, 1, false
                ));
            });
        }
    }

    /** Recharge la grille avec tous les articles de la marque sélectionnée. */
    private void reloadProducts(Marque m) {
        productsGridPanel.removeAll();
        int count = 0;
        for (Article a : prodCtrl.getCatalogue()) {
            if (a.getIdMarque() == m.getIdMarque()) {
                productsGridPanel.add(new ProductCardPanel(a, prodCtrl));
                count++;
            }
        }
        if (count == 0) {
            JLabel none = new JLabel("Aucun article pour “" + m.getNom() + "”", SwingConstants.CENTER);
            none.setFont(new Font("SansSerif", Font.BOLD, 18));
            none.setForeground(Color.DARK_GRAY);
            JPanel wrap = new JPanel(new BorderLayout());
            wrap.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
            wrap.add(none, BorderLayout.CENTER);
            productsGridPanel.add(wrap);
        }
        productsGridPanel.revalidate();
        productsGridPanel.repaint();
    }
}