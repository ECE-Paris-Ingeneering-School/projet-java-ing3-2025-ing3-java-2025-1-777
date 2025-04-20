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
        String sql = "SELECT * FROM Marque WHERE id_marque = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new Marque(rs.getInt("id_marque"), rs.getString("nom"));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Marque> findAll() {
        List<Marque> list = new ArrayList<>();
        String sql = "SELECT * FROM Marque ORDER BY nom";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next())
                list.add(new Marque(rs.getInt("id_marque"), rs.getString("nom")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public boolean insert(Marque m) {
        String sql = "INSERT INTO Marque(nom) VALUES(?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNom());
            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) m.setIdMarque(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean update(Marque m) {
        String sql = "UPDATE Marque SET nom = ? WHERE id_marque = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getNom());
            ps.setInt(2, m.getIdMarque());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Marque WHERE id_marque = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }


    @Override
    public Marque findByName(String name) {
        String sql = "SELECT * FROM Marque WHERE nom = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new Marque(rs.getInt("id_marque"), rs.getString("nom"));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean insertBatch(List<Marque> list) {
        String sql = "INSERT IGNORE INTO Marque(nom) VALUES(?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            for (Marque m : list) {
                ps.setString(1, m.getNom());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}