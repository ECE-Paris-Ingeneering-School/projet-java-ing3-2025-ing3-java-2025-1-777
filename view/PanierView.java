package view;

import Controlers.CartController;
import model.Article;
import javax.swing.*;
import java.awt.*;

// Dans view/PanierView.java
public class PanierView extends JFrame {
    private CartController cartController;

    public PanierView(CartController cartController) {
        this.cartController = cartController;
        initUI();
        setSize(800, 600); // ou un `pack()` + contenu bien dimensionné
        setLocationRelativeTo(null); // centrer
        setVisible(true);
    }



    private void initUI() {
        setLayout(new BorderLayout());

        // Liste des articles
        JPanel itemsPanel = new JPanel(new GridLayout(0, 1));
        cartController.getPanier().getArticles().forEach((article, quantite) -> {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.add(new JLabel(article.getNom() + " x" + quantite), BorderLayout.CENTER);

            // Bouton supprimer
            JButton deleteButton = new JButton("Supprimer");
            deleteButton.addActionListener(e -> {
                cartController.supprimerArticle(article);
                initUI(); // Rafraîchir l'interface
            });
            itemPanel.add(deleteButton, BorderLayout.EAST);
            itemsPanel.add(itemPanel);
        });

        // Total et validation
        JPanel footerPanel = new JPanel();
        footerPanel.add(new JLabel("Total : " + cartController.getPanier().calculerTotal() + " €"));
        JButton checkoutButton = new JButton("Valider la commande");
        checkoutButton.addActionListener(e -> new CheckoutFrame(cartController).setVisible(true));
        footerPanel.add(checkoutButton);

        add(new JScrollPane(itemsPanel), BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }
}