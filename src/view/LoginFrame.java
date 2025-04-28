// src/view/LoginFrame.java
package view;

import Controlers.ShoppingController;
import model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Fenêtre de connexion.
 */
public class LoginFrame extends JFrame {

    public static Utilisateur currentUser;

    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton signUpButton;

    // On récupère l’instance du singleton au lieu de faire "new"
    private final ShoppingController controller;

    public LoginFrame() {
        // Récupère l’unique instance
        controller = ShoppingController.getInstance();

        // Initialisation des composants
        emailField     = new JTextField(20);
        passwordField  = new JPasswordField(20);
        loginButton    = new JButton("Se connecter");
        signUpButton   = new JButton("Créer un compte");

        initUI();
    }

    private void initUI() {
        setTitle("Connexion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 320);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        headerPanel.setBackground(Color.WHITE);
        JLabel headerLabel = new JLabel("Connexion");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(emailField, gbc);

        // Mot de passe
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        formPanel.add(passwordField, gbc);

        // Boutons
        gbc.gridy = 2; gbc.gridx = 1; gbc.gridwidth = 1;
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(new Color(150,100,80));
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(loginButton, gbc);

        gbc.gridx = 2;
        signUpButton.setBackground(Color.WHITE);
        signUpButton.setForeground(new Color(150,100,80));
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(signUpButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.add(new JLabel("\u00A9 2025 Loro Piana"));
        add(footerPanel, BorderLayout.SOUTH);

        initListeners();
    }

    private void initListeners() {
        loginButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText().trim();
            String pwd   = new String(passwordField.getPassword()).trim();

            Utilisateur u = controller.login(email, pwd);
            if (u != null) {
                currentUser = u;
                JOptionPane.showMessageDialog(
                        LoginFrame.this,
                        "Connexion réussie ! Bienvenue " + u.getPrenom() + " " + u.getNom(),
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );
                if ("admin".equalsIgnoreCase(u.getRole())) {
                    new AdminDashboardFrame().setVisible(true);
                } else {
                    new HomeFrame().setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        LoginFrame.this,
                        "Email ou mot de passe incorrect !",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        signUpButton.addActionListener(e -> new SignUpFrame().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}