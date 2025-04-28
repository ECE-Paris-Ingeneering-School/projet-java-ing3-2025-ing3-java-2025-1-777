package view;

import DAO.UtilisateurDAO;
import DAO.UtilisateurDAOImpl;
import model.Utilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**  classe de la gestion des clients */
public class ClientManagementPanel extends JPanel {
    private final UtilisateurDAO userDao = new UtilisateurDAOImpl();
    private final DefaultTableModel model;
    private final JTable table;

    public ClientManagementPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "Nom", "Prénom", "Email", "Rôle"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPane.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        JButton addB  = new JButton("Ajouter");
        JButton editB = new JButton("Modifier");
        JButton delB  = new JButton("Supprimer");
        btnPane.add(addB); btnPane.add(editB); btnPane.add(delB);
        add(btnPane, BorderLayout.SOUTH);

        loadTable();

        addB.addActionListener(e -> {
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            ClientEditDialog dlg = new ClientEditDialog(owner, userDao, null);
            dlg.setVisible(true);
            loadTable();
        });
        editB.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un client à modifier");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            Utilisateur u = userDao.findById(id);
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            ClientEditDialog dlg = new ClientEditDialog(owner, userDao, u);
            dlg.setVisible(true);
            loadTable();
        });
        delB.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un client à supprimer");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Suppression", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                userDao.delete(id);
                loadTable();
            }
        });
    }

    /** Recharge la liste des clients depuis la BDD */
    private void loadTable() {
        model.setRowCount(0);
        List<Utilisateur> list = userDao.findAll();
        for (Utilisateur u : list) {
            model.addRow(new Object[]{u.getIdUtilisateur(), u.getNom(), u.getPrenom(), u.getEmail(), u.getRole()});
        }
    }
}