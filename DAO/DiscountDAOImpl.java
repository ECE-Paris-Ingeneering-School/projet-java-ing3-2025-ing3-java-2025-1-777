// DAO/DiscountDAOImpl.java
package DAO;

import model.Discount;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscountDAOImpl implements DiscountDAO {
    @Override
    public Discount getDiscountForArticle(int articleId) {
        String sql = "SELECT * FROM Discount WHERE id_article = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, articleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Discount(
                        rs.getInt("id_discount"),
                        rs.getString("description"),
                        rs.getDouble("taux"),
                        rs.getString("type"),
                        rs.getInt("id_article")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Discount> getAllDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        String sql = "SELECT * FROM Discount";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                discounts.add(new Discount(
                        rs.getInt("id_discount"),
                        rs.getString("description"),
                        rs.getDouble("taux"),
                        rs.getString("type"),
                        rs.getInt("id_article")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discounts;
    }

    @Override
    public boolean addDiscount(Discount discount) {
        String sql = "INSERT INTO Discount(description, taux, type, id_article) VALUES(?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, discount.getDescription());
            ps.setDouble(2, discount.getTaux());
            ps.setString(3, discount.getType());
            ps.setInt(4, discount.getIdArticle());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeDiscount(int discountId) {
        String sql = "DELETE FROM Discount WHERE id_discount = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, discountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}