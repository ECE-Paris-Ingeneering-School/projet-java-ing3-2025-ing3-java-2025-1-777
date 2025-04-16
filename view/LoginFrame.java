package view;

import Controlers.ShoppingController;
import model.Utilisateur;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import DAO.UtilisateurDAO;
import DAO.UtilisateurDAOImpl;

public class LoginFrame extends JFrame {
    private ShoppingController shoppingController;

    public LoginFrame(ShoppingController shoppingController) {
        this.shoppingController = shoppingController;
        initUI();
    }

    private void initUI() {
        setTitle("Connexion - Loro Piana");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Mot de passe:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Se connecter");
        JButton registerButton = new JButton("Créer un compte");

        loginButton.addActionListener(e -> handleLogin(emailField.getText(), new String(passwordField.getPassword())));
        registerButton.addActionListener(e -> new RegisterFrame(shoppingController).setVisible(true));

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
    }

    private void handleLogin(String email, String password) {
        Utilisateur user = shoppingController.login(email, password);
        if (user != null) {
            shoppingController.setCurrentUser(user);
            new CatalogFrame(user, shoppingController).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Email ou mot de passe incorrect",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean register(Utilisateur user) {
        try {
            UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();
            return utilisateurDAO.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}