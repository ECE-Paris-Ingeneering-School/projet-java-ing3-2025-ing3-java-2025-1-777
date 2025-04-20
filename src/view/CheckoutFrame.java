package view;

import Controlers.CartController;
import DAO.CommandeDAOImpl;
import model.Article;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class CheckoutFrame extends JFrame {

    public CheckoutFrame(CartController cartController) {
        setTitle("Validation de commande");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        //logo
        JPanel header = new JPanel();
        header.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(10, 10, 0, 10));

        JLabel brandLabel = new JLabel("Loro Piana");
        brandLabel.setFont(new Font("Snell Roundhand", Font.PLAIN, 28));
        brandLabel.setForeground(NavigationBarPanel.TEXT_COLOR);
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(brandLabel);

        add(header, BorderLayout.NORTH);



        // RÉCAPITULATIF PANIER

        JPanel recap = new JPanel();
        recap.setLayout(new BoxLayout(recap, BoxLayout.Y_AXIS));
        recap.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        recap.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (Map.Entry<Article, Integer> e : cartController.getPanier().getArticles().entrySet()) {
            Article a = e.getKey();
            int qte   = e.getValue();
            JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
            line.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

            JLabel name = new JLabel(a.getNom() + " x" + qte);
            name.setForeground(NavigationBarPanel.TEXT_COLOR);
            line.add(name);

            JLabel price = new JLabel(String.format("%.2f €", a.getPrixUnitaire()*qte));
            price.setForeground(NavigationBarPanel.TEXT_COLOR);
            line.add(price);

            recap.add(line);
        }
        add(recap, BorderLayout.CENTER);

        // FORMULAIRE
        JPanel form = new JPanel(new GridLayout(8, 2, 6, 6));
        form.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        form.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] lbls = {
                "Nom :", "Prénom :", "Adresse :", "Ville :",
                "Code postal :", "Numéro de carte :", "Date d'expiration :", "CVV :"
        };
        JTextField[] fields = new JTextField[lbls.length];

        for (int i = 0; i < lbls.length; i++) {
            JLabel l = new JLabel(lbls[i]);
            l.setForeground(NavigationBarPanel.TEXT_COLOR);
            fields[i] = new JTextField();
            form.add(l);
            form.add(fields[i]);
        }
        add(form, BorderLayout.WEST);

        // FOOTER : total + bouton confirmer

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        footer.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel total = new JLabel("Total : "
                + String.format("%.2f €", cartController.getPanier().calculerTotal()));
        total.setForeground(NavigationBarPanel.TEXT_COLOR);
        total.setFont(new Font("SansSerif", Font.BOLD, 16));
        footer.add(total, BorderLayout.WEST);

        JButton confirm = new JButton("Confirmer la commande");
        confirm.setFocusPainted(false);
        confirm.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        confirm.setForeground(NavigationBarPanel.MENU_HOVER_COLOR);
        confirm.setBorder(BorderFactory.createLineBorder(NavigationBarPanel.LINE_COLOR));
        confirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirm.addActionListener((ActionEvent e) -> {
            if (validateForm(fields)) {
                boolean ok = new CommandeDAOImpl().creerCommande(
                        cartController.getPanier(), fields[2].getText()
                );
                if (ok) {
                    JOptionPane.showMessageDialog(this,
                            "Commande validée avec succès !",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    cartController.viderPanier();
                    dispose();
                }
            }
        });

        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnWrap.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        btnWrap.add(confirm);
        footer.add(btnWrap, BorderLayout.EAST);

        add(footer, BorderLayout.SOUTH);
    }

    // message d'erreur
    private boolean validateForm(JTextField[] f) {
        for (JTextField t : f)
            if (t.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez remplir tous les champs",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
        return true;
    }
}
