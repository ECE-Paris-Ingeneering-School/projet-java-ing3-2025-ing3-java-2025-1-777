import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginSignupGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginSignupGUI().createLoginFrame());
    }

    public void createLoginFrame() {
        JFrame frame = new JFrame("Shopping App - Connexion / Inscription");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel emailLabel = new JLabel("Email :");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Mot de passe :");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Connexion");
        JButton signupButton = new JButton("Inscription");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        // === Actions des boutons ===
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = String.valueOf(passwordField.getPassword());
            // TODO: Connexion réelle via contrôleur/DAO
            JOptionPane.showMessageDialog(frame, "Connexion avec : " + email);
            frame.dispose();
            showClientDashboard(email);
        });

        signupButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = String.valueOf(passwordField.getPassword());
            // TODO: Inscription réelle via contrôleur/DAO
            JOptionPane.showMessageDialog(frame, "Inscription avec : " + email);
        });
    }

    public void showClientDashboard(String userEmail) {
        JFrame frame = new JFrame("Espace Client - Bienvenue " + userEmail);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Onglet Catalogue
        JPanel cataloguePanel = new JPanel(new BorderLayout());
        cataloguePanel.add(new JLabel("Catalogue des produits disponible ici..."), BorderLayout.CENTER);
        tabbedPane.addTab("Catalogue", cataloguePanel);

        // Onglet Panier
        JPanel panierPanel = new JPanel(new BorderLayout());
        panierPanel.add(new JLabel("Contenu de votre panier ici..."), BorderLayout.CENTER);
        tabbedPane.addTab("Panier", panierPanel);

        // Onglet Historique
        JPanel historiquePanel = new JPanel(new BorderLayout());
        historiquePanel.add(new JLabel("Historique de vos commandes ici..."), BorderLayout.CENTER);
        tabbedPane.addTab("Historique", historiquePanel);

        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }
}
