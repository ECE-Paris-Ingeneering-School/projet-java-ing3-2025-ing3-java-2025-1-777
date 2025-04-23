package view;

import Controlers.CartController;
import Controlers.ShoppingController;
import DAO.CommandeDAOImpl;
import model.Article;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class CheckoutFrame extends JFrame {
    private final ShoppingController shop = ShoppingController.getInstance();
    private final CartController cartController;
    private final JTextField addressField;
    private final JTextField cityField;
    private final JTextField postalField;
    private final JTextField cardNumberField;
    private final JTextField expiryField;
    private final JTextField cvvField;

    public CheckoutFrame(CartController cartController) {
        this.cartController = cartController;

        setTitle("Validation de la commande - Loro Piana");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        setLayout(new BorderLayout(10, 10));

        // HEADER 
        JLabel title = new JLabel("Récapitulatif de votre commande", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        
        JPanel center = new JPanel();
        center.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(10, 10, 10, 10));

        //  RÉCAPITULATIF DES ARTICLES
        JPanel recapPanel = new JPanel();
        recapPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        recapPanel.setLayout(new BoxLayout(recapPanel, BoxLayout.Y_AXIS));
        recapPanel.setBorder(BorderFactory.createTitledBorder("Vos articles"));

        double total = 0;
        for (Map.Entry<Article, Integer> entry : cartController.getPanier().getArticles().entrySet()) {
            Article art = entry.getKey();
            int qty = entry.getValue();
            double linePrice = shop.calculerPrixArticleAvecRemise(art, qty);
            total += linePrice;
            JLabel line = new JLabel(qty + " × " + art.getNom() + "   →   "
                    + String.format("%.2f €", linePrice));
            recapPanel.add(line);
        }
        JLabel totalLabel = new JLabel("Total : " + String.format("%.2f €", total));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        recapPanel.add(Box.createVerticalStrut(10));
        recapPanel.add(totalLabel);

        center.add(recapPanel);
        center.add(Box.createVerticalStrut(20));

        // LIVRAISON ET PAIEMENT
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createTitledBorder("Livraison & paiement"));

        formPanel.add(new JLabel("Adresse :"));
        addressField = new JTextField(); formPanel.add(addressField);

        formPanel.add(new JLabel("Ville :"));
        cityField = new JTextField(); formPanel.add(cityField);

        formPanel.add(new JLabel("Code postal :"));
        postalField = new JTextField(); formPanel.add(postalField);

        formPanel.add(new JLabel("N° de carte :"));
        cardNumberField = new JTextField(); formPanel.add(cardNumberField);

        formPanel.add(new JLabel("Exp. (MM/AA) :"));
        expiryField = new JTextField(); formPanel.add(expiryField);

        formPanel.add(new JLabel("CVV :"));
        cvvField = new JTextField(); formPanel.add(cvvField);

        center.add(formPanel);
        add(new JScrollPane(center), BorderLayout.CENTER);

        //FOOTER 
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JButton validateButton = new JButton("Valider la commande");
        validateButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        validateButton.addActionListener(this::onValidate);
        footer.add(validateButton);

        add(footer, BorderLayout.SOUTH);
    }

    private void onValidate(ActionEvent e) {
        if (addressField.getText().isBlank() ||
                cityField.getText().isBlank() ||
                postalField.getText().isBlank() ||
                cardNumberField.getText().isBlank() ||
                expiryField.getText().isBlank() ||
                cvvField.getText().isBlank()
        ) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fullAddress = addressField.getText().trim()
                + ", " + cityField.getText().trim()
                + " "   + postalField.getText().trim();

        boolean ok = new CommandeDAOImpl().creerCommande(
                cartController.getPanier(),
                fullAddress
        );
        if (ok) {
            JOptionPane.showMessageDialog(this,
                    "Commande enregistrée avec succès !",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);

            cartController.viderPanier();
            dispose();

            SwingUtilities.invokeLater(() -> new AccountFrame().setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this,
                    "Échec de l'enregistrement de la commande.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
