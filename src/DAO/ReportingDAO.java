package DAO;

import Utils.DBConnection;
import model.MonthlyStat;
import model.OrderRow;
import model.Totals;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 * Interface DAO pour le reporting.
 */
public class ReportingDAO {

    /** Récupère le nombre total de commandes et le chiffre d'affaires total. */
    public Totals getTotals() throws SQLException {
        String sql = "SELECT COUNT(*) cnt, SUM(total_commande) sum FROM Commande";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Totals(rs.getInt("cnt"), rs.getDouble("sum"));
            }
            return new Totals(0, 0.0);
        }
    }

    /** Récupère les stats mensuelles */
    public List<MonthlyStat> getMonthlyStats() throws SQLException {
        String sql =
                "SELECT YEAR(date_commande) y, MONTH(date_commande) m, " +
                        " COUNT(*) cnt, SUM(total_commande) sum " +
                        "FROM Commande GROUP BY y,m ORDER BY y,m";
        List<MonthlyStat> stats = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                stats.add(new MonthlyStat(
                        rs.getInt("y"),
                        rs.getInt("m"),
                        rs.getInt("cnt"),
                        rs.getDouble("sum")
                ));
            }
        }
        return stats;
    }

    /** Récupère la liste des commandes filtrées par statut */
    public List<OrderRow> getOrders(String statusFilter) throws SQLException {
        StringBuilder sb = new StringBuilder("SELECT id_commande, date_commande, total_commande, status FROM Commande ");
        if (!"TOUS".equals(statusFilter)) {
            sb.append("WHERE status = ? ");
        }
        sb.append("ORDER BY date_commande DESC");

        List<OrderRow> rows = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sb.toString())) {
            if (!"TOUS".equals(statusFilter)) {
                ps.setString(1, statusFilter);
            }
            try (ResultSet rs = ps.executeQuery()) {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                while (rs.next()) {
                    int id        = rs.getInt("id_commande");
                    Timestamp ts  = rs.getTimestamp("date_commande");
                    double total  = rs.getDouble("total_commande");
                    String status = rs.getString("status");

                    String due = "";
                    if ("EN_COURS".equals(status)) {
                        LocalDate d = ts.toLocalDateTime().toLocalDate().plusWeeks(2);
                        due = d.format(fmt);
                    }

                    rows.add(new OrderRow(
                            id,
                            ts.toLocalDateTime().format(fmt),
                            due,
                            total,
                            status
                    ));
                }
            }
        }
        return rows;
    }


}