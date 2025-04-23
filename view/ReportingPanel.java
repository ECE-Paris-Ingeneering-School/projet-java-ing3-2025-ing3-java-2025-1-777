package view;

import Utils.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * Panel de reporting 
 */
public class ReportingPanel extends JPanel {
    private final JLabel totalOrdersLabel;
    private final JLabel totalRevenueLabel;
    private final DefaultTableModel monthlyModel;

    public ReportingPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        totalOrdersLabel  = new JLabel("Commandes totales : 0");
        totalOrdersLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalRevenueLabel = new JLabel("Chiffre d'affaires : 0,00 €");
        totalRevenueLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        topPanel.add(totalOrdersLabel);
        topPanel.add(totalRevenueLabel);
        add(topPanel, BorderLayout.NORTH);

        // Tableau mensuel 
        String[] cols = {"Année", "Mois", "Nb Commandes", "CA Mois (€)"};
        monthlyModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable monthlyTable = new JTable(monthlyModel);
        add(new JScrollPane(monthlyTable), BorderLayout.CENTER);

      
        loadStatistics();
    }

    private void loadStatistics() {
        try (Connection conn = DBConnection.getConnection()) {
            // Totaux
            String totalSql = "SELECT COUNT(*) AS cnt, SUM(total_commande) AS sum FROM Commande";
            try (PreparedStatement ps = conn.prepareStatement(totalSql);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalOrdersLabel.setText("Commandes totales : " + rs.getInt("cnt"));
                    totalRevenueLabel.setText(
                            String.format("Chiffre d'affaires : %.2f €", rs.getDouble("sum"))
                    );
                }
            }

            // Détails par mois
            String monthlySql = ""
                    + "SELECT YEAR(date_commande) AS y, MONTH(date_commande) AS m, "
                    + "       COUNT(*) AS cnt, SUM(total_commande) AS sum "
                    + "FROM Commande "
                    + "GROUP BY y, m "
                    + "ORDER BY y, m";
            try (PreparedStatement ps = conn.prepareStatement(monthlySql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int year = rs.getInt("y");
                    int month = rs.getInt("m");
                    int count = rs.getInt("cnt");
                    double sum = rs.getDouble("sum");
                    monthlyModel.addRow(new Object[]{year, month, count, String.format("%.2f", sum)});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors du chargement des statistiques : " + e.getMessage(),
                    "Erreur BDD", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
