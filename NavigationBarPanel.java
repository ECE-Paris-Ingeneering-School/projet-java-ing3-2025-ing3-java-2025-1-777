package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Barre de navigation inspirée du site Loro Piana
 
 */
public class NavigationBarPanel extends JPanel {

    //  couleurs 
    public static final Color BACKGROUND_COLOR = new Color(253, 247, 240); 
    private static final Color LINE_COLOR = new Color(111, 57, 46);         
    private static final Color TEXT_COLOR = new Color(111, 57, 46);         
    private static final Color MENU_HOVER_COLOR = new Color(150, 100, 80);    

    public NavigationBarPanel() {
        initUI();
    }

    private void initUI() {
        // Barre de navigation 
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(0, 0, 0, 0));

       
        JPanel topMessagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        topMessagePanel.setBackground(BACKGROUND_COLOR);
        JLabel topMessage = new JLabel("Découvrez la pièce iconique");
        topMessage.setFont(new Font("SansSerif", Font.ITALIC, 14));
        topMessage.setForeground(TEXT_COLOR);
        topMessagePanel.add(topMessage);
        add(topMessagePanel); // 1ère ligne

        //  Ligne de séparation fine
        JPanel separatorPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(LINE_COLOR);
                int y = getHeight() / 2;
                g.fillRect(0, y, getWidth(), 2);
            }
        };
        separatorPanel.setBackground(BACKGROUND_COLOR);
        separatorPanel.setPreferredSize(new Dimension(getWidth(), 2));
        add(separatorPanel); // 2ème ligne

        // Barre de menu principale 
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(BACKGROUND_COLOR);

        // Logo 
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(BACKGROUND_COLOR);
        leftPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        ImageIcon logoIcon = new ImageIcon("logo_loropiana.png");
     
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(logoLabel);
        
        JLabel collectionLabel = new JLabel("Loro Piana");
        collectionLabel.setFont(new Font("Snell Roundhand", Font.PLAIN, 30));
        collectionLabel.setForeground(TEXT_COLOR);
        collectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(collectionLabel);
        menuPanel.add(leftPanel, BorderLayout.WEST);

        // onglets de navigation
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        centerPanel.setBackground(BACKGROUND_COLOR);
        String[] menuItems = {"Homme", "Femme", "Enfant", "Cadeaux", "Art de Vivre"};
        for (String item : menuItems) {
            JLabel menuLabel = createMenuLabel(item);
            centerPanel.add(menuLabel);
        }
        menuPanel.add(centerPanel, BorderLayout.CENTER);

        // Recherche, Compte, Panier
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setBackground(BACKGROUND_COLOR);
        String[] actions = {"Recherche", "Compte", "Panier"};
        for (String action : actions) {
            JLabel actionLabel = createMenuLabel(action);
            rightPanel.add(actionLabel);
        }
        menuPanel.add(rightPanel, BorderLayout.EAST);

        add(menuPanel); 
    }

    /**
     * Crée un JLabel servant de menu cliquable.
     
     */
    private JLabel createMenuLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(MENU_HOVER_COLOR);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(TEXT_COLOR);
                setCursor(Cursor.getDefaultCursor());
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                // Redirect to a new window 
                SwingUtilities.invokeLater(() -> {
                    new CustomWindow(text).setVisible(true);
                });
            }
        });
        return label;
    }

   
    public static class CustomWindow extends JFrame {
        public CustomWindow(String menuItem) {
            setTitle("Loro Piana - " + menuItem);
            setSize(1200, 800);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

         
            getContentPane().setBackground(BACKGROUND_COLOR);

            NavigationBarPanel navBar = new NavigationBarPanel();
            add(navBar, BorderLayout.NORTH);

            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBackground(BACKGROUND_COLOR);
            contentPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
            JLabel contentLabel = new JLabel("Bienvenue sur la page " + menuItem, SwingConstants.CENTER);
            contentLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
            contentLabel.setForeground(TEXT_COLOR);
            contentPanel.add(contentLabel, BorderLayout.CENTER);

            add(contentPanel, BorderLayout.CENTER);
        }
    }

  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Barre de Navigation Loro Piana");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 200);
            frame.add(new NavigationBarPanel(), BorderLayout.NORTH);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
