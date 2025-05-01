package view;

import Controlers.CartController;
import DAO.CommandeDAO;
import DAO.CommandeDAOImpl;
import model.Article;
import DAO.DiscountDAOImpl;
import model.Commande;
import model.Discount;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

public class CheckoutFrame extends JFrame {
    private final CartController cartController;
    private JPanel recapPanel; 

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

      
        JLabel title = new JLabel("Récapitulatif de votre commande", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(10, 10, 10, 10));

        
        recapPanel = new JPanel();
        recapPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        recapPanel.setLayout(new BoxLayout(recapPanel, BoxLayout.Y_AXIS));
        recapPanel.setBorder(BorderFactory.createTitledBorder("Vos articles"));

  
        for (Map.Entry<Article, Integer> entry : cartController.getPanier().getArticles().entrySet()) {
            Article art = entry.getKey();
            int qty = entry.getValue();

            // Appliquer la remise
            Discount discount = new DiscountDAOImpl().getDiscountForArticle(art.getIdArticle());
            double unitPrice = art.getPrixUnitaire();
            if (discount != null) {
                unitPrice = unitPrice * (1 - discount.getTaux() / 100); // Appliquer la remise
            }

            int bulkQty = art.getQuantiteBulk();
            double bulkPrice = art.getPrixBulk();
            double linePrice;
            if (bulkQty > 0 && qty >= bulkQty) {
                int groups = qty / bulkQty;
                int remainder = qty % bulkQty;
                linePrice = groups * bulkPrice + remainder * unitPrice;
            } else {
                linePrice = qty * unitPrice;
            }

            
            JPanel articlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            articlePanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
            JLabel articleLabel = new JLabel(qty + " × " + art.getNom() + "   →   " + String.format("%.2f €", linePrice));
            articlePanel.add(articleLabel);
            JButton removeButton = new JButton("Supprimer");
            removeButton.addActionListener(e -> removeArticle(art)); 
            articlePanel.add(removeButton);
            recapPanel.add(articlePanel);
        }


        double totalTTC = cartController.calculerTotalTTC();
        JLabel totalLabel = new JLabel("Total (TTC) : " + String.format("%.2f €", totalTTC));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        recapPanel.add(Box.createVerticalStrut(10));
        recapPanel.add(totalLabel);

        center.add(recapPanel);
        center.add(Box.createVerticalStrut(20));

        // FORMULAIRE DE LIVRAISON & PAIEMENT
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

        formPanel.add(new JLabel("Date d'expiration (MM/AA) :"));
        expiryField = new JTextField(); formPanel.add(expiryField);

        formPanel.add(new JLabel("CVV :"));
        cvvField = new JTextField(); formPanel.add(cvvField);

        center.add(formPanel);
        add(new JScrollPane(center), BorderLayout.CENTER);

  
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JButton validateButton = new JButton("Valider la commande");
        validateButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        validateButton.addActionListener(this::onValidate);
        footer.add(validateButton);

        add(footer, BorderLayout.SOUTH);
    }

    /**
     * Supprime un article du panier et met à jour l'affichage.
     *
     * @param article L'article à supprimer du panier.
     */
    private void removeArticle(Article article) {
      
        cartController.supprimerArticle(article);

      
        recapPanel.removeAll(); 
        for (Map.Entry<Article, Integer> entry : cartController.getPanier().getArticles().entrySet()) {
            Article art = entry.getKey();
            int qty = entry.getValue();
            int bulkQty = art.getQuantiteBulk();
            double unit = art.getPrixUnitaire();
            double bulkPrice = art.getPrixBulk();
            double linePrice;
            if (bulkQty > 0 && qty >= bulkQty) {
                int groups = qty / bulkQty;
                int remainder = qty % bulkQty;
                linePrice = groups * bulkPrice + remainder * unit;
            } else {
                linePrice = qty * unit;
            }

          
            JPanel articlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            articlePanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
            JLabel articleLabel = new JLabel(qty + " × " + art.getNom() + "   →   " + String.format("%.2f €", linePrice));
            articlePanel.add(articleLabel);
            JButton removeButton = new JButton("Supprimer");
            removeButton.addActionListener(e -> removeArticle(art)); 
            articlePanel.add(removeButton);
            recapPanel.add(articlePanel);
        }
        recapPanel.revalidate();
        recapPanel.repaint();
        double totalTTC = cartController.calculerTotalTTC();
        JLabel totalLabel = new JLabel("Total (TTC) : " + String.format("%.2f €", totalTTC));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        recapPanel.add(Box.createVerticalStrut(10));
        recapPanel.add(totalLabel);
    }

    private void onValidate(ActionEvent e) {
        // Validation des champs saisis par l'utilisateur
        if (addressField.getText().isBlank() ||
                cityField.getText().isBlank()    ||
                postalField.getText().isBlank()  ||
                cardNumberField.getText().isBlank() ||
                expiryField.getText().isBlank()  ||
                cvvField.getText().isBlank()
        ) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (!cityField.getText().matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "La ville doit contenir uniquement des lettres.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

      
        if (!postalField.getText().matches("[0-9]{5}")) {
            JOptionPane.showMessageDialog(this, "Le code postal doit contenir 5 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        if (!cardNumberField.getText().replace(" ", "").matches("[0-9]{16}")) {
            JOptionPane.showMessageDialog(this, "Le numéro de carte doit contenir 16 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

      
        if (!expiryField.getText().matches("[0-9]{2}/[0-9]{2}")) {
            JOptionPane.showMessageDialog(this, "La date d'expiration doit être au format MM/AA.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (!cvvField.getText().matches("[0-9]{3}")) {
            JOptionPane.showMessageDialog(this, "Le CVV doit contenir 3 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fullAddress = addressField.getText().trim()
                + ", " + cityField.getText().trim()
                + " " + postalField.getText().trim();


        CommandeDAO dao = new CommandeDAOImpl();
        boolean ok = dao.creerCommande(cartController.getPanier(), fullAddress);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Échec de l'enregistrement de la commande.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Commande enregistrée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        cartController.viderPanier();
        dispose();

        // Récupère la dernière commande et affiche la ConfirmationFrame
        int userId = cartController.getPanier().getUserId();
        List<Commande> commandes = dao.findByUser(userId);
        if (!commandes.isEmpty()) {
            int lastId = commandes.get(0).getIdCommande();
            SwingUtilities.invokeLater(() -> new ConfirmationFrame(lastId).setVisible(true));
        }
    }
}
