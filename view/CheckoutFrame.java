package view;

import Controlers.CartController;
import DAO.CommandeDAOImpl;
import model.Article;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class CheckoutFrame extends JFrame {
    private final CartController cartController;

    public CheckoutFrame(CartController cartController) {
        this.cartController = cartController;
        initUI();
        setTitle("Validation de commande");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        // ================= PARTIE RÉCAPITULATIF =================
        JPanel recapPanel = createRecapPanel();
        add(recapPanel, BorderLayout.NORTH);

        // ================= FORMULAIRE DE LIVRAISON/PAIEMENT =================
        JPanel formPanel = createFormPanel();
        add(new JScrollPane(formPanel), BorderLayout.CENTER);

        // ================= BOUTONS =================
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createRecapPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JLabel title = new JLabel("Récapitulatif de votre commande");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(15));

        // Articles du panier
        for (Map.Entry<Article, Integer> entry : cartController.getPanier().getArticles().entrySet()) {
            Article article = entry.getKey();
            int quantite = entry.getValue();
            double prix = cartController.getPanier().calculerPrixArticle(article, quantite);

            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
            itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JLabel nameLabel = new JLabel(article.getNom() + " x" + quantite);
            nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

            JLabel priceLabel = new JLabel(String.format("%.2f €", prix));
            priceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

            itemPanel.add(nameLabel, BorderLayout.WEST);
            itemPanel.add(priceLabel, BorderLayout.EAST);
            panel.add(itemPanel);
        }

        panel.add(Box.createVerticalStrut(20));

        // Total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JLabel totalLabel = new JLabel("Total: " + String.format("%.2f €", cartController.getTotalAvecRemises()));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        totalPanel.add(totalLabel);
        panel.add(totalPanel);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 15, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        // Champs du formulaire
        String[] fields = {
                "Nom complet", "Adresse email",
                "Adresse de livraison", "Complément d'adresse",
                "Code postal", "Ville",
                "Pays", "Téléphone",
                "Numéro de carte", "Titulaire de la carte",
                "Date d'expiration (MM/AA)", "Code de sécurité"
        };

        for (String field : fields) {
            JLabel label = new JLabel(field + ":");
            label.setFont(new Font("SansSerif", Font.PLAIN, 14));
            panel.add(label);

            JTextField textField = new JTextField();
            panel.add(textField);
        }

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton cancelButton = new JButton("Annuler");
        styleButton(cancelButton, NavigationBarPanel.LINE_COLOR);
        cancelButton.addActionListener(e -> dispose());

        JButton confirmButton = new JButton("Confirmer la commande");
        styleButton(confirmButton, new Color(56, 142, 60));
        confirmButton.addActionListener(this::processOrder);

        panel.add(cancelButton);
        panel.add(confirmButton);

        return panel;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 35));
    }

    private void processOrder(ActionEvent e) {
        // Validation utilisateur connecté
        if (cartController.getPanier().getUserId() <= 0) {
            showError("Aucun utilisateur connecté");
            return;
        }

        // Validation du stock
        if (!cartController.isStockAvailable()) {
            showError("Stock insuffisant pour certains articles");
            return;
        }

        // Validation des champs (à implémenter)
        if (!validateFields()) {
            return;
        }

        // Enregistrement de la commande
        try {
            boolean success = new CommandeDAOImpl().creerCommande(
                    cartController.getPanier(),
                    "Adresse de livraison" // Remplacer par les données du formulaire
            );

            if (success) {
                showSuccess();
                cartController.viderPanier();
                dispose();
            } else {
                throw new Exception("Échec de la commande");
            }
        } catch (Exception ex) {
            showError("Erreur lors de la commande: " + ex.getMessage());
        }
    }

    private boolean validateFields() {
        // Implémentez la validation des champs ici
        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess() {
        JOptionPane.showMessageDialog(this,
                "Votre commande a été validée avec succès!",
                "Confirmation",
                JOptionPane.INFORMATION_MESSAGE);
    }
}