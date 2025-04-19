package view;

import DAO.UtilisateurDAO;
import DAO.UtilisateurDAOImpl;
import model.Utilisateur;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre de création de compte pour un nouvel utilisateur.
 */
public class SignUpFrame extends JFrame {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signUpButton;

    private UtilisateurDAO utilisateurDAO;

    public SignUpFrame() {
        utilisateurDAO = new UtilisateurDAOImpl();
        initComponents();
    }

    private void initComponents() {
        setTitle("Création de compte");
        setSize(480, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Fond blanc du frame

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        headerPanel.setBackground(Color.WHITE);
        JLabel headerLabel = new JLabel("Création de compte");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setForeground(Color.BLACK);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Formulaire
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Labels et champs
        formPanel.add(new JLabel("Nom :"));
        nomField = new JTextField();
        formPanel.add(nomField);

        formPanel.add(new JLabel("Prénom :"));
        prenomField = new JTextField();
        formPanel.add(prenomField);

        formPanel.add(new JLabel("Email :"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Confirmez le mot de passe :"));
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField);

        signUpButton = new JButton("Créer le compte");
        signUpButton.setFocusPainted(false);
        signUpButton.setBackground(Color.WHITE);
        signUpButton.setForeground(new Color(150, 100, 80));
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(new JLabel()); // espace
        formPanel.add(signUpButton);

        add(formPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        footerPanel.setBackground(Color.WHITE);
        JLabel copyLabel = new JLabel("© 2025 Loro Piana");
        copyLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        copyLabel.setForeground(Color.DARK_GRAY);
        footerPanel.add(copyLabel);
        add(footerPanel, BorderLayout.SOUTH);

        // Action sur le bouton
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText().trim();
                String prenom = prenomField.getText().trim();
                String email = emailField.getText().trim();
                String motDePasse = new String(passwordField.getPassword());
                String confirmMotDePasse = new String(confirmPasswordField.getPassword());

                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || confirmMotDePasse.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpFrame.this, "Veuillez remplir tous les champs.");
                    return;
                }
                if (!motDePasse.equals(confirmMotDePasse)) {
                    JOptionPane.showMessageDialog(SignUpFrame.this, "Les mots de passe ne correspondent pas.");
                    return;
                }

                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNom(nom);
                utilisateur.setPrenom(prenom);
                utilisateur.setEmail(email);
                utilisateur.setMotDePasse(motDePasse);
                utilisateur.setRole("client");

                boolean success = utilisateurDAO.insert(utilisateur);
                if (success) {
                    JOptionPane.showMessageDialog(SignUpFrame.this, "Compte créé avec succès. Vous pouvez maintenant vous connecter.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(SignUpFrame.this, "Erreur lors de la création du compte. Vérifiez que l'email n'est pas déjà utilisé.");
                }
            }
        });
    }
}
