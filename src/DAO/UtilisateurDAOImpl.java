package DAO;

import model.Utilisateur;
import Utils.DBConnection;

import javax.swing.*;
import java.sql.*;

/**
 * Implémentation de l’interface UtilisateurDAO en JDBC.
 */
public class UtilisateurDAOImpl implements UtilisateurDAO {

    private String email;
    private String motDePasse;

    @Override
    public Utilisateur findById(int id) {
        Utilisateur user = null;
        String sql = "SELECT * FROM Utilisateur WHERE id_utilisateur = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Utilisateur();
                user.setIdUtilisateur(rs.getInt("id_utilisateur"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setMotDePasse(rs.getString("mot_de_passe"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    @Override
    public Utilisateur findByEmailAndPassword(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
        Utilisateur user = null;
        String sql = "SELECT * FROM Utilisateur WHERE email = ? AND mot_de_passe = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email.trim());
            ps.setString(2, motDePasse.trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Utilisateur();
                user.setIdUtilisateur(rs.getInt("id_utilisateur"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setMotDePasse(rs.getString("mot_de_passe"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean emailExists(String email) {
        return false;
    }


    @Override
    public java.util.List<Utilisateur> findAll() {
        // Implémenter si besoin
        return null;
    }


    @Override
    public boolean update(Utilisateur user) {
        // Implémenter si besoin
        return false;
    }

    @Override
    public boolean delete(int id) {
        // Implémenter si besoin
        return false;
    }
    // UtilisateurDAOImpl.java (modification de la méthode insert)
    @Override
    public boolean insert(Utilisateur user) {
        String sql = "INSERT INTO Utilisateur (nom, prenom, email, mot_de_passe, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getMotDePasse());
            ps.setString(5, user.getRole());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erreur SQL : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        }

}



