package view;

import Controlers.ProductController;
import DAO.DiscountDAO;
import DAO.DiscountDAOImpl;
import model.Article;
import model.Discount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** classe de gestion des soldes */
public class DiscountManagementPanel extends JPanel {
    private final DiscountDAO discountDAO    = new DiscountDAOImpl();
    private final ProductController prodCtrl = new ProductController();
    private final DefaultTableModel model;
    private final JTable table;

    public DiscountManagementPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        String[] cols = {"ID", "Article", "Description", "Taux", "Type"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPane.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        JButton addB = new JButton("Ajouter");
        JButton editB = new JButton("Modifier");
        JButton delB = new JButton("Supprimer");
        btnPane.add(addB);
        btnPane.add(editB);
        btnPane.add(delB);
        add(btnPane, BorderLayout.SOUTH);

        loadTable();

        addB.addActionListener(e -> {
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            DiscountEditDialog dlg = new DiscountEditDialog(owner, discountDAO, prodCtrl, null);
            dlg.setVisible(true);
            loadTable();
        });
        editB.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un rabais");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            Discount d = discountDAO.findById(id);
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            DiscountEditDialog dlg = new DiscountEditDialog(owner, discountDAO, prodCtrl, d);
            dlg.setVisible(true);
            loadTable();
        });
        delB.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un rabais");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "Voulez-vous supprimer ce rabais?", "Suppression", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                discountDAO.delete(id);
                loadTable();
            }
        });
    }

    /** Recharge la table avec les données dans la BDD */
    private void loadTable() {
        model.setRowCount(0);
        List<Discount> list = discountDAO.findAll();
        for (Discount d : list) {
            Article a = prodCtrl.getProductById(d.getIdArticle());
            String name = (a != null ? a.getNom() : "");
            model.addRow(new Object[]{
                    d.getIdDiscount(),
                    name,
                    d.getDescription(),
                    d.getTaux(),
                    d.getType()
            });
        }
    }
}
