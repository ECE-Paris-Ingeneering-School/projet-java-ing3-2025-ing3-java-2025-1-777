package view;

import DAO.ReportingDAO;
import model.Totals;
import model.MonthlyStat;
import model.OrderRow;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

/** classe de reporting (toutes les commandes ou filtrées par mois)*/

public class ReportingPanel extends JPanel {
    private final ReportingDAO dao = new ReportingDAO();

    private final JLabel totalOrdersLabel  = new JLabel();
    private final JLabel totalRevenueLabel = new JLabel();
    private final DefaultTableModel monthlyModel;
    private final DefaultTableModel ordersModel;
    private final JTable ordersTable;
    private final JComboBox<String> statusFilter;

    public ReportingPanel() {
        setLayout(new BorderLayout(10,10));
        setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JPanel summary = new JPanel(new GridLayout(1,2,10,10));
        summary.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        totalOrdersLabel.setFont(bold(16));
        totalRevenueLabel.setFont(bold(16));
        summary.add(totalOrdersLabel);
        summary.add(totalRevenueLabel);
        add(summary, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        monthlyModel = new DefaultTableModel(new String[]{"Année","Mois","Nb Cmd","CA (€)"},0) {
            @Override
            public boolean isCellEditable(int r,int c){
                return false;
            }
        };
        tabs.addTab("Par mois", wrapScroll(new JTable(monthlyModel)));

        ordersModel = new DefaultTableModel(new String[]{"ID","Date Cmd","Échéance","Total TTC","Statut"},0) {
            @Override public boolean isCellEditable(int r,int c){
                return false;
            }
        };
        ordersTable = new JTable(ordersModel);
        ordersTable.getColumnModel().getColumn(4).setCellRenderer((tbl, val, sel, fcs, row, col) -> {
                    JLabel lbl = (JLabel) new DefaultTableCellRenderer().getTableCellRendererComponent(tbl,"Payé",sel,fcs,row,col);
                    lbl.setForeground(new Color(0,128,0));
                    return lbl;
        });

        JPanel ordersPanel = new JPanel(new BorderLayout(5,5));
        ordersPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        statusFilter = new JComboBox<>(new String[]{"TOUS","PAYÉ","EN_COURS"});
        statusFilter.addActionListener(e -> reloadOrders());
        JPanel fp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fp.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        fp.add(new JLabel("Filtrer statut :"));
        fp.add(statusFilter);
        ordersPanel.add(fp, BorderLayout.NORTH);
        ordersPanel.add(wrapScroll(ordersTable), BorderLayout.CENTER);

        tabs.addTab("Toutes les commandes", ordersPanel);
        add(tabs, BorderLayout.CENTER);

        loadStatistics();
        reloadOrders();
    }
    /** Mise à jour du chiffre d'affaires */

    private void loadStatistics() {
        try {
            Totals t = dao.getTotals();
            totalOrdersLabel.setText("Commandes totales : " + t.getCount());
            totalRevenueLabel.setText("Chiffre d'affaires : " + NumberFormat.getCurrencyInstance(Locale.FRANCE).format(t.getSum()));
            monthlyModel.setRowCount(0);
            for (MonthlyStat ms : dao.getMonthlyStats()) {
                monthlyModel.addRow(new Object[]{ms.year, Month.of(ms.month).getDisplayName(TextStyle.FULL, Locale.FRANCE), ms.count, String.format("%.2f", ms.sum)});
            }
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur BDD : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE
            );
        }
    }
/** Charge les commandes */
    private void reloadOrders() {
        try {
            ordersModel.setRowCount(0);
            for (OrderRow or : dao.getOrders(statusFilter.getSelectedItem().toString())) {
                ordersModel.addRow(new Object[]{
                        or.id, or.dateCmd, or.due,
                        String.format("%.2f €", or.total),
                        or.status
                });
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    private JScrollPane wrapScroll(JTable tbl) {
        JScrollPane sp = new JScrollPane(tbl);
        tbl.setFillsViewportHeight(true);
        return sp;
    }
    private Font bold(int sz) {
        return new Font("SansSerif", Font.BOLD, sz);
    }
}