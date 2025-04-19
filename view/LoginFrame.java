package view;


import Controlers.ShoppingController;
import model.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;


    private final ShoppingController controller;

    public LoginFrame() {
        controller = new ShoppingController();
        initUI();
    }

    /**
     * Méthode principale de configuration de l'UI.
     */
    private void initUI() {
        setTitle("Connexion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 320);
        setLocationRelativeTo(null);

        // On applique un layout BorderLayout global
        setLayout(new BorderLayout());

        // header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

       

        JLabel headerLabel = new JLabel("Connexion");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setForeground(Color.BLACK);
        headerPanel.add(headerLabel);

        add(headerPanel, BorderLayout.NORTH);

        // formulaire (email, mot de passe)
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // On utilise un GridBagLayout pour organiser les champs
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(emailLabel, gbc);

        // Champ Email
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        emailField = new JTextField(20);
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(emailField, gbc);

        // Label Mot de passe
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);

        // Champ Mot de passe
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(passwordField, gbc);

        // Bouton Se connecter
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        loginButton = new JButton("Se connecter");
        loginButton.setFocusPainted(false);
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(new Color(150, 100, 80 ));
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(loginButton, gbc);

        // Bouton Créer un compte
        gbc.gridx = 2;
        gbc.gridy = 2;
        signUpButton = new JButton("Créer un compte");
        signUpButton.setFocusPainted(false);
        signUpButton.setBackground(Color.WHITE);
        signUpButton.setForeground(new Color(150, 100, 80 ));
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        formPanel.add(signUpButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel copyLabel = new JLabel("\u00A9 2025 Loro Piana");
        copyLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        copyLabel.setForeground(Color.DARK_GRAY);
        footerPanel.add(copyLabel);

        add(footerPanel, BorderLayout.SOUTH);

 
        initListeners();
    }

    /**
     * Initialise les événements 
     */
    private void initListeners() {
      
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
               
                Utilisateur utilisateur = controller.login(email, password);
                if (utilisateur != null) {
                    JOptionPane.showMessageDialog(
                            LoginFrame.this,
                            "Connexion réussie ! Bienvenue " + utilisateur.getPrenom() + " " + utilisateur.getNom(),
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                   
                    new HomeFrame().setVisible(true);
                    // Fermer la fenêtre de connexion
                    LoginFrame.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(
                            LoginFrame.this,
                            "Email ou mot de passe incorrect !",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            }
        });

        // Action "Créer un compte"
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir la fenêtre de création de compte 
                new SignUpFrame().setVisible(true);
            }
        });
    }
}
