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

/** classe de gestion des articles par l'admin */
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

        JPanel btnPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        btnPane.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JButton addBtn = new JButton("Ajouter");
        JButton editBtn = new JButton("Modifier");
        JButton delBtn = new JButton("Supprimer");

        btnPane.add(addBtn);
        btnPane.add(editBtn);
        btnPane.add(delBtn);

        add(btnPane, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            ArticleEditDialog dlg = new ArticleEditDialog(owner, prodCtrl, null);
            dlg.setVisible(true);
            loadTable();
        });

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un article à modifier.");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            Article art = prodCtrl.getProductById(id);
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            ArticleEditDialog dlg = new ArticleEditDialog(owner, prodCtrl, art);
            dlg.setVisible(true);
            loadTable();
        });

        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un article à supprimer.");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            int choice = JOptionPane.showConfirmDialog(this, "Supprimer l'article sélectionné?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                prodCtrl.deleteArticle(id);
                loadTable();
            }
        });

        loadTable();
    }

    /** Recharge les données de la table */
    private void loadTable() {
        model.setRowCount(0);
        List<Article> list = prodCtrl.getCatalogue();
        for (Article a : list) {
            Marque m = marqueDAO.findById(a.getIdMarque());
            String brand = (m != null ? m.getNom() : "");
            model.addRow(new Object[]{
                    a.getIdArticle(),
                    a.getNom(),
                    brand,
                    a.getDescription(),
                    a.getPrixUnitaire(),
                    a.getPrixBulk(),
                    a.getQuantiteBulk(),
                    a.getStock()
            });
        }
    }
}
