package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavigationBarPanel extends JPanel {

    public NavigationBarPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        //  Logo et Nom de marque
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftPanel.setBackground(Color.WHITE);
        JLabel brandLabel = new JLabel("Loro Piana");
        brandLabel.setFont(new Font("Serif", Font.BOLD, 28));
        brandLabel.setForeground(Color.BLACK);
        leftPanel.add(brandLabel);
        add(leftPanel, BorderLayout.WEST);

        // Onglets de navigation
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        centerPanel.setBackground(Color.WHITE);
        String[] menuItems = {"Collections", "Men", "Women", "Kids", "About"};
        for (String item : menuItems) {
            JLabel menuLabel = new JLabel(item);
            menuLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            menuLabel.setForeground(Color.DARK_GRAY);
            menuLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                
                    JOptionPane.showMessageDialog(null, "Onglet cliqué : " + item);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    menuLabel.setForeground(Color.GRAY);
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    menuLabel.setForeground(Color.DARK_GRAY);
                    setCursor(Cursor.getDefaultCursor());
                }
            });
            centerPanel.add(menuLabel);
        }
        add(centerPanel, BorderLayout.CENTER);

        // Search bar and Cart
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setBackground(Color.WHITE);
        String[] actions = {"Search", "Cart(0)"};
        for (String action : actions) {
            JLabel actionLabel = new JLabel(action);
            actionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            actionLabel.setForeground(Color.DARK_GRAY);
            actionLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(null, "Action cliquée : " + action);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    actionLabel.setForeground(Color.GRAY);
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    actionLabel.setForeground(Color.DARK_GRAY);
                    setCursor(Cursor.getDefaultCursor());
                }
            });
            rightPanel.add(actionLabel);
        }
        add(rightPanel, BorderLayout.EAST);
    }
}
