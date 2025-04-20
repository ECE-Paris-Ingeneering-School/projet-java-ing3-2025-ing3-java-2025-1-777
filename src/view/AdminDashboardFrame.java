package view;

import Controlers.ProductController;
import Controlers.ShoppingController;
import model.Article;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tableau de bord administrateur.

 */
public class AdminDashboardFrame extends JFrame {

    private final ShoppingController shopCtrl;
    private final ProductController  prodCtrl;


    public AdminDashboardFrame(ShoppingController shopCtrl) {
        this.shopCtrl = shopCtrl;
        this.prodCtrl = new ProductController();
        initUI();
    }

    // UI principale
    private void initUI() {
        setTitle("Administration – Loro Piana");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new NavigationBarPanel(), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Inventaire",   createInventoryPanel());
        tabs.addTab("Rabais",       placeholder("Gestion des offres de rabais"));
        tabs.addTab("Clients",      placeholder("Gestion des fiches clients"));
        tabs.addTab("Statistiques", placeholder("Statistiques de ventes"));
        add(tabs, BorderLayout.CENTER);
    }

    //INVENTAIRE
    private JComponent createInventoryPanel() {


        String[] cols = {"ID", "Nom", "Prix (€)", "Stock"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        loadTable(model);
        JScrollPane scroll = new JScrollPane(table);

        /* boutons CRUD */
        JButton add  = new JButton("Ajouter");
        JButton edit = new JButton("Modifier");
        JButton del  = new JButton("Supprimer");

        //AJOUTER
        add.addActionListener(e -> {
            ArticleEditDialog dlg = new ArticleEditDialog(AdminDashboardFrame.this,prodCtrl,null);
            dlg.setVisible(true);
            loadTable(model);
        });

        // MODIFIER
        edit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(AdminDashboardFrame.this,
                        "Sélectionnez une ligne à modifier");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            Article articleToEdit = prodCtrl.getProductById(id);

            ArticleEditDialog dlg = new ArticleEditDialog(
                    AdminDashboardFrame.this,
                    prodCtrl,
                    articleToEdit
            );
            dlg.setVisible(true);
            loadTable(model);
        });

        //SUPPRIMER
        del.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this,"Sélectionnez une ligne"); return; }

            int id = (int) model.getValueAt(row, 0);
            int ok = JOptionPane.showConfirmDialog(this,
                    "Supprimer l’article "+id+" ?", "Confirmer", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION && prodCtrl.deleteProduct(id)) loadTable(model);
        });

        //assemblage
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(add); south.add(edit); south.add(del);

        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(10,10,10,10));
        pane.add(scroll, BorderLayout.CENTER);
        pane.add(south,  BorderLayout.SOUTH);
        return pane;
    }

    //recharge du tableau
    private void loadTable(DefaultTableModel m) {
        m.setRowCount(0);
        for (Article a : prodCtrl.getCatalogue())
            m.addRow(new Object[]{ a.getIdArticle(), a.getNom(), a.getPrixUnitaire(), a.getStock() });
    }

    // boîte de dialogue
    private Article showProductDialog(Article base) {
        JTextField tfNom   = new JTextField(base != null ? base.getNom() : "");
        JTextField tfPrix  = new JTextField(base != null ? String.valueOf(base.getPrixUnitaire()) : "");
        JTextField tfStock = new JTextField(base != null ? String.valueOf(base.getStock()) : "");

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Nom"));   form.add(tfNom);
        form.add(new JLabel("Prix (€)")); form.add(tfPrix);
        form.add(new JLabel("Stock")); form.add(tfStock);

        int res = JOptionPane.showConfirmDialog(this, form,
                (base == null ? "Nouvel article" : "Modifier article"),
                JOptionPane.OK_CANCEL_OPTION);

        if (res != JOptionPane.OK_OPTION) return null;
        try {
            String nom  = tfNom.getText().trim();
            double prix = Double.parseDouble(tfPrix.getText().trim().replace(',','.'));
            int stock   = Integer.parseInt(tfStock.getText().trim());

            if (nom.isEmpty()) throw new IllegalArgumentException();
            Article a = (base != null) ? base : new Article();
            a.setNom(nom); a.setPrixUnitaire(prix); a.setStock(stock);
            return a;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Données invalides",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // onglets placeholders
    private JComponent placeholder(String txt) {
        JLabel l = new JLabel(txt, SwingConstants.CENTER);
        l.setFont(new Font("SansSerif", Font.ITALIC, 18));
        l.setForeground(Color.GRAY);
        JPanel p = new JPanel(new BorderLayout());
        p.add(l, BorderLayout.CENTER);
        return p;
    }
}
