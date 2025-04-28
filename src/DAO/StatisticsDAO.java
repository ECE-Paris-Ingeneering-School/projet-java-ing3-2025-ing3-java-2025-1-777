package DAO;

import Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Interface DAO pour les statistiques.
 */
public class StatisticsDAO {

    public record Totals(int totalOrders, double totalRevenue) {}

    public record MonthlyStat(int year, String monthName, int orderCount, double revenue) {}

    /** Récupère le nombre total de commandes et le CA total */
    public Totals fetchTotals() throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt, SUM(total_commande) AS sum FROM Commande";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Totals(rs.getInt("cnt"), rs.getDouble("sum"));
            }
            return new Totals(0, 0);
        }
    }

    /** Récupère détails mensuels groupés par année et mois */
    public List<MonthlyStat> fetchMonthlyStats() throws SQLException {
        String sql = """
            SELECT YEAR(date_commande) AS y,
                   MONTH(date_commande) AS m,
                   COUNT(*)        AS cnt,
                   SUM(total_commande) AS sum
              FROM Commande
             GROUP BY y, m
             ORDER BY y, m
            """;
        var result = new ArrayList<MonthlyStat>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            java.time.format.TextStyle style = java.time.format.TextStyle.FULL;
            java.util.Locale locale = java.util.Locale.FRENCH;
            while (rs.next()) {
                int y = rs.getInt("y");
                int m = rs.getInt("m");
                String month = java.time.Month.of(m).getDisplayName(style, locale);
                result.add(new MonthlyStat(y, month, rs.getInt("cnt"), rs.getDouble("sum")));
            }
        }
        return result;
    }
}