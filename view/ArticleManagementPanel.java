package view;

import Controlers.ProductController;
import DAO.MarqueDAO;
import DAO.MarqueDAOImpl;
import model.Article;
import model.Marque;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ArticleManagementPanel extends JPanel {
    private final ProductController prodCtrl = new ProductController();
    private final MarqueDAO marqueDAO = new MarqueDAOImpl();
    private final DefaultTableModel model;
    private final JTable table;

    public ArticleManagementPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        String[] cols = {"ID", "Nom", "Marque", "Description", "Prix Uni", "Prix Bulk", "Qty Bulk", "Stock"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadTable();
    }

    private void loadTable() {
        model.setRowCount(0);
        List<Article> list = prodCtrl.getCatalogue();
        for (Article a : list) {
            Marque m = marqueDAO.findById(a.getIdMarque());
            String brand = (m != null ? m.getNom() : "");
            model.addRow(new Object[]{
                    a.getIdArticle(), a.getNom(), brand,
                    a.getDescription(), a.getPrixUnitaire(),
                    a.getPrixBulk(), a.getQuantiteBulk(), a.getStock()
            });
        }
    }
}
