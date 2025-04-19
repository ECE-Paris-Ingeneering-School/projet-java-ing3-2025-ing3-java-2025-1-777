package DAO;

import model.Utilisateur;
import util.DBConnection;

import javax.swing.*;
import java.sql.*;

/**
 * Implémentation de l’interface UtilisateurDAO
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
    public Utilisateur findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM Utilisateur WHERE email = ? AND mot_de_passe = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password); // À hasher en production

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utilisateur user = new Utilisateur();
                // Remplissage COMPLET des champs
                user.setIdUtilisateur(rs.getInt("id_utilisateur"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setMotDePasse(rs.getString("mot_de_passe")); // Normalement pas nécessaire après login
                user.setRole(rs.getString("role")); // Important pour les vérifications

                System.out.println("Utilisateur trouvé: ID=" + user.getIdUtilisateur()); // Debug
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche utilisateur:");
            e.printStackTrace();
        }
        System.out.println("Aucun utilisateur trouvé pour: " + email); // Debug
        return null;
    }


    @Override
    public java.util.List<Utilisateur> findAll() {
        // Implémenter si besoin
        return null;
    }


    @Override
    public boolean update(Utilisateur user) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
    //modification de la méthode insert
    @Override
    public boolean insert(Utilisateur user) {
        String sql = "INSERT INTO Utilisateur (nom, prenom, email, mot_de_passe, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getMotDePasse()); // Doit être déjà hashé
            ps.setString(5, user.getRole() != null ? user.getRole() : "client");

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            // Récupération de l'ID généré
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setIdUtilisateur(rs.getInt(1));
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Utilisateur WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Utilisateur findByEmail(String email) {
        Utilisateur user = null;
        String sql = "SELECT * FROM Utilisateur WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
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



}



