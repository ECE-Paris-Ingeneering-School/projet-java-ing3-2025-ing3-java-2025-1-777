package DAO;

import Utils.DBConnection;
import model.Marque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation JDBC de MarqueDAO.
 */
public class MarqueDAOImpl implements MarqueDAO {

    @Override
    public Marque findById(int id) {
        Marque marque = null;
        String sql = "SELECT * FROM Marque WHERE id_marque = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    marque = new Marque(
                            rs.getInt("id_marque"),
                            rs.getString("nom")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marque;
    }

    @Override
    public List<Marque> findAll() {
        List<Marque> marques = new ArrayList<>();
        String sql = "SELECT * FROM Marque ORDER BY nom";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                marques.add(new Marque(
                        rs.getInt("id_marque"),
                        rs.getString("nom")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marques;
    }

    @Override
    public boolean insert(Marque marque) {
        String sql = "INSERT INTO Marque (nom) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, marque.getNom());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        marque.setIdMarque(keys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Marque marque) {
        String sql = "UPDATE Marque SET nom = ? WHERE id_marque = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, marque.getNom());
            ps.setInt(2, marque.getIdMarque());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Marque WHERE id_marque = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}