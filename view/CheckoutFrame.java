package view;

import Controlers.CartController;
import DAO.CommandeDAOImpl;
import model.Article;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class CheckoutFrame extends JFrame {
    public CheckoutFrame(CartController cartController) {
        setTitle("Validation de commande");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel récapitulatif
        JPanel recapPanel = new JPanel();
        recapPanel.setLayout(new BoxLayout(recapPanel, BoxLayout.Y_AXIS));
        recapPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Map.Entry<Article, Integer> entry : cartController.getPanier().getArticles().entrySet()) {
            Article article = entry.getKey();
            int quantite = entry.getValue();
            double prix = cartController.getPanier().calculerPrixArticle(article, quantite);

            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            itemPanel.add(new JLabel(article.getNom() + " x" + quantite));
            itemPanel.add(new JLabel(String.format("%.2f €", prix)));
            recapPanel.add(itemPanel);
        }

        // Panel total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(new JLabel("Total: " + String.format("%.2f €", cartController.getTotalAvecRemises())));

        // Panel formulaire
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.add(new JLabel("Adresse:"));
        JTextField addressField = new JTextField();
        formPanel.add(addressField);
        formPanel.add(new JLabel("Carte bancaire:"));
        JTextField cardField = new JTextField();
        formPanel.add(cardField);

        // Bouton de confirmation
        JButton confirmButton = new JButton("Confirmer la commande");
        confirmButton.addActionListener((ActionEvent e) -> {
            if (validateForm(addressField.getText(), cardField.getText())) {
                boolean success = new CommandeDAOImpl().creerCommande(
                        cartController.getPanier(),
                        addressField.getText()
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Commande validée avec succès !");
                    cartController.viderPanier();
                    dispose();
                }
            }
        });

        // Assemblage
        add(recapPanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(totalPanel, BorderLayout.SOUTH);
        add(confirmButton, BorderLayout.SOUTH);
    }

    private boolean validateForm(String address, String card) {
        if (address.isEmpty() || card.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}