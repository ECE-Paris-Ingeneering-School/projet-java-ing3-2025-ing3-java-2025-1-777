package view;

import Controlers.ProductController;
import DAO.MarqueDAO;
import DAO.MarqueDAOImpl;
import model.Article;
import model.Marque;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Page Recherche
 */
public class ArticleSearchFrame extends JFrame {

    private final ProductController prodCtrl = new ProductController();
    private final MarqueDAO         marqueDAO = new MarqueDAOImpl();

    private final JPanel    catalogPanel = new JPanel();
    private final JTextField searchField = new JTextField(30);
    private final Map<Integer,String> marqueMap = new HashMap<>();

    public ArticleSearchFrame() {
        setTitle("Recherche d’articles – Loro Piana");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);


        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(new NavigationBarPanel());

        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchBar.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JButton btSearch = new JButton("Rechercher");
        btSearch.addActionListener(e -> refreshGrid());
        searchField.addActionListener(e -> refreshGrid());

        searchBar.add(searchField);
        searchBar.add(btSearch);
        top.add(searchBar);

        add(top, BorderLayout.NORTH);


        catalogPanel.setLayout(new GridLayout(0, 3, 20, 20));
        catalogPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        catalogPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JScrollPane scroll = new JScrollPane(
                catalogPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setBorder(null);
        scroll.getViewport().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        add(scroll, BorderLayout.CENTER);


        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        footer.setBorder(new EmptyBorder(10,10,10,10));
        footer.add(new JLabel("© 2025 Loro Piana – All Rights Reserved", JLabel.CENTER));
        add(footer, BorderLayout.SOUTH);


        for (Marque m : marqueDAO.findAll()) {
            marqueMap.put(m.getIdMarque(), m.getNom().toLowerCase());
        }


        refreshGrid();
    }

    /**
     * Refiltre et ré-affiche les vignettes selon la recherche.
     */
    private void refreshGrid() {
        String q = searchField.getText().trim().toLowerCase();
        catalogPanel.removeAll();

        int count = 0;
        for (Article a : prodCtrl.getCatalogue()) {
            String nomArt   = a.getNom().toLowerCase();
            String nomMarq  = marqueMap.getOrDefault(a.getIdMarque(), "");

            boolean match = q.isEmpty()
                    || nomArt.contains(q)
                    || nomMarq.contains(q);

            if (match) {
                catalogPanel.add(new ProductCardPanel(a, prodCtrl));
                count++;
            }
        }

        if (count == 0) {
            JLabel none = new JLabel("Aucun résultat", SwingConstants.CENTER);
            none.setFont(new Font("SansSerif", Font.BOLD, 20));
            none.setForeground(Color.DARK_GRAY);
            // occupe une ligne entière
            JPanel p = new JPanel(new BorderLayout());
            p.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
            p.add(none, BorderLayout.CENTER);
            catalogPanel.add(p);
        }

        catalogPanel.revalidate();
        catalogPanel.repaint();
    }
}