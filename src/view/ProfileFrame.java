package view;

import model.Utilisateur;
import javax.swing.*;
import java.awt.*;
import static view.NavigationBarPanel.BACKGROUND_COLOR;
import static view.NavigationBarPanel.TEXT_COLOR;

/**
 * Fenêtre affichant les informations du compte utilisateur.
 */
public class ProfileFrame extends JFrame {
    public ProfileFrame(Utilisateur user) {
        setTitle("Mon Compte");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT,20,10));
        header.setBackground(BACKGROUND_COLOR);
        JLabel title = new JLabel("Mon Compte");
        title.setFont(new Font("SansSerif", Font.BOLD,22));
        title.setForeground(TEXT_COLOR);
        header.add(title);
        add(header, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(3,2,10,10));
        infoPanel.setBackground(BACKGROUND_COLOR);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));
        infoPanel.add(new JLabel("Nom:"));
        infoPanel.add(new JLabel(user.getNom()));
        infoPanel.add(new JLabel("Prénom:"));
        infoPanel.add(new JLabel(user.getPrenom()));
        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(new JLabel(user.getEmail()));
        add(infoPanel, BorderLayout.CENTER);
    }
}


