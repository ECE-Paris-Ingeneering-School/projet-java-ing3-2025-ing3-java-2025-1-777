package view;

import javax.swing.*;
import java.awt.*;

/**
 * Page d’accueil 
 */
public class HomeFrame extends JFrame {

    public HomeFrame() {
        initUI();
    }

  
    private void initUI() {
        setTitle("Loro Piana – Accueil");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

      
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        // barre de navigation réutilisable 
        add(new NavigationBarPanel(), BorderLayout.NORTH);

        //) message de bienvenue 
        JLabel welcome = new JLabel("Bienvenue chez Loro Piana", SwingConstants.CENTER);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcome.setForeground(NavigationBarPanel.TEXT_COLOR);
        add(welcome, BorderLayout.CENTER);
    }
}
