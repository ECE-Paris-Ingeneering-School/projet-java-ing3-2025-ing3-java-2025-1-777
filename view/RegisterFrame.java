package view;

import Controlers.ShoppingController;
import model.Utilisateur;
import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private ShoppingController shoppingController;

    public RegisterFrame(ShoppingController shoppingController) {
        this.shoppingController = shoppingController;
        initUI();
    }

    private void initUI() {
        setTitle("Inscription - Loro Piana");
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom:"));
        panel.add(prenomField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe:"));
        panel.add(passwordField);

        JButton registerButton = new JButton("S'inscrire");
        registerButton.addActionListener(e -> {
            Utilisateur newUser = new Utilisateur();
            newUser.setNom(nomField.getText());
            newUser.setPrenom(prenomField.getText());
            newUser.setEmail(emailField.getText());
            newUser.setMotDePasse(new String(passwordField.getPassword())); // À hasher plus tard
            newUser.setRole("client");

            if(shoppingController.register(newUser)) {
                JOptionPane.showMessageDialog(this, "Inscription réussie !");
                dispose();
                new LoginFrame(shoppingController).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de l'inscription",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(registerButton);
        add(panel);
    }
}