package view;

import Utils.DBConnection;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class ReportingPanel extends JPanel {
    private final JLabel totalOrdersLabel  = new JLabel("Commandes totales : …");
    private final JLabel totalRevenueLabel = new JLabel("Chiffre d'affaires : …");
    private final DefaultTableModel monthlyModel;
    private final DefaultTableModel ordersModel;
    private final JTable  ordersTable;
    private final JComboBox<String> statusFilter;

    public ReportingPanel() {
        setLayout(new BorderLayout(10,10));
        setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        // === PREMIÈRE LIGNE : résumés ===
        JPanel summary = new JPanel(new GridLayout(1,2,10,10));
        summary.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        totalOrdersLabel.setFont(bold(16));
        totalRevenueLabel.setFont(bold(16));
        summary.add(totalOrdersLabel);
        summary.add(totalRevenueLabel);
        add(summary, BorderLayout.NORTH);

        // === CENTRE : deux onglets (mensuel / commandes) ===
        JTabbedPane tabs = new JTabbedPane();

        //  statistiques mensuelles --
        String[] monthCols = {"Année", "Mois", "Nb Cmd", "CA (€)"};
        monthlyModel = new DefaultTableModel(monthCols,0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable monthTable = new JTable(monthlyModel);
        tabs.addTab("Par mois", wrapScroll(monthTable));

        //  liste des commandes --
        JPanel ordersPanel = new JPanel(new BorderLayout(5,5));
        ordersPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        // filtre de statut
        statusFilter = new JComboBox<>(new String[]{"TOUS","PAYÉ","EN_COURS"});
        statusFilter.addActionListener(e -> reloadOrders());
        JPanel filterPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPane.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        filterPane.add(new JLabel("Filtrer statut :"));
        filterPane.add(statusFilter);
        ordersPanel.add(filterPane, BorderLayout.NORTH);

        String[] orderCols = {"ID","Date Cmd","Échéance","Total TTC","Statut"};
        ordersModel = new DefaultTableModel(orderCols,0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        ordersTable = new JTable(ordersModel);
        // renderer pour colorer la colonne statut
        ordersTable.getColumnModel().getColumn(4)
                .setCellRenderer(new StatusRenderer());
        ordersPanel.add(wrapScroll(ordersTable), BorderLayout.CENTER);

        tabs.addTab("Toutes les commandes", ordersPanel);
        add(tabs, BorderLayout.CENTER);

        loadStatistics();
        reloadOrders();
    }

    private void loadStatistics() {
        try (Connection c = DBConnection.getConnection()) {
            // totaux
            var totSt = c.prepareStatement(
                    "SELECT COUNT(*) cnt, SUM(total_commande) sum FROM Commande"
            );
            try (ResultSet rs = totSt.executeQuery()) {
                if(rs.next()) {
                    totalOrdersLabel.setText("Commandes totales : " + rs.getInt("cnt"));
                    totalRevenueLabel.setText(
                            "Chiffre d'affaires : " +
                                    NumberFormat.getCurrencyInstance(Locale.FRANCE)
                                            .format(rs.getDouble("sum"))
                    );
                }
            }
            // mensuel
            var monSt = c.prepareStatement(
                    "SELECT YEAR(date_commande) y, MONTH(date_commande) m, " +
                            "       COUNT(*) cnt, SUM(total_commande) sum " +
                            "FROM Commande GROUP BY y,m ORDER BY y,m"
            );
            try (ResultSet rs = monSt.executeQuery()) {
                monthlyModel.setRowCount(0);
                DateTimeFormatter monthFmt = DateTimeFormatter.ofPattern("MMMM", Locale.FRANCE);
                while(rs.next()) {
                    int y = rs.getInt("y"), m = rs.getInt("m");
                    monthlyModel.addRow(new Object[]{
                            y,
                            Month.of(m).getDisplayName(TextStyle.FULL, Locale.FRANCE),
                            rs.getInt("cnt"),
                            String.format("%.2f", rs.getDouble("sum"))
                    });
                }
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erreur BDD : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void reloadOrders() {
        String filter = statusFilter.getSelectedItem().toString();
        ordersModel.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (Connection c = DBConnection.getConnection()) {
            var ps = c.prepareStatement(
                    "SELECT id_commande, date_commande, total_commande, status " +
                            "FROM Commande " +
                            (filter.equals("TOUS") ? "" : "WHERE status = ? ") +
                            "ORDER BY date_commande DESC"
            );
            if(!filter.equals("TOUS")) ps.setString(1, filter);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    int id      = rs.getInt("id_commande");
                    Timestamp dt= rs.getTimestamp("date_commande");
                    String status= rs.getString("status");
                    double tot  = rs.getDouble("total_commande");
                    // calcul échéance si paiement en cours
                    String due = "";
                    if("EN_COURS".equals(status)) {
                        LocalDate d = dt.toLocalDateTime().toLocalDate().plusWeeks(2);
                        due = d.format(fmt);
                    }
                    ordersModel.addRow(new Object[]{
                            id,
                            dt.toLocalDateTime().format(fmt),
                            due,
                            String.format("%.2f €", tot),
                            status
                    });
                }
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    // rend les statuts en couleur
    private static class StatusRenderer extends DefaultTableCellRenderer {
        @Override public Component getTableCellRendererComponent(
                JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            super.getTableCellRendererComponent(t,val,sel,foc,r,c);
            String s = val.toString();
            setText(s.equals("EN_COURS") ? "En cours" : "Payé");
            setForeground(s.equals("PAYÉ") ? new Color(0,128,0) : new Color(200,100,0));
            return this;
        }
    }

    // utilitaires
    private JScrollPane wrapScroll(JTable tbl) {
        var sp = new JScrollPane(tbl);
        tbl.setFillsViewportHeight(true);
        return sp;
    }
    private Font bold(int sz) {
        return new Font("SansSerif", Font.BOLD, sz);
    }
}