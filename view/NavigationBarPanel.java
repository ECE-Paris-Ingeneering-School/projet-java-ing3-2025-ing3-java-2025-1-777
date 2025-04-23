
package view;

import Controlers.CartController;
import Controlers.ShoppingController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavigationBarPanel extends JPanel {
    public static final Color BACKGROUND_COLOR = new Color(253, 247, 240);
    static final Color LINE_COLOR = new Color(111, 57, 46);
    static final Color TEXT_COLOR = new Color(111, 57, 46);
    static final Color MENU_HOVER_COLOR = new Color(150, 100, 80);

    private final CartController cartController;


    public NavigationBarPanel() {
        this(null);
    }

    public NavigationBarPanel(CartController cartController) {
        this.cartController = cartController;
        initUI();
    }

    private void initUI() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel topMsg = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        topMsg.setBackground(BACKGROUND_COLOR);
        JLabel msg = new JLabel("Découvrez la pièce iconique");
        msg.setFont(new Font("SansSerif", Font.ITALIC, 14));
        msg.setForeground(TEXT_COLOR);
        topMsg.add(msg);
        add(topMsg);

        JPanel sep = new JPanel() {
            @Override 
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(LINE_COLOR);
                g.fillRect(0, getHeight()/2, getWidth(), 2);
            }
        };
        sep.setPreferredSize(new Dimension(10, 2));
        sep.setBackground(BACKGROUND_COLOR);
        add(sep);

        // menu principal
        JPanel menu = new JPanel(new BorderLayout());
        menu.setBackground(BACKGROUND_COLOR);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(BACKGROUND_COLOR);
        left.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel brand = new JLabel("Loro Piana");
        brand.setFont(new Font("Snell Roundhand", Font.PLAIN, 30));
        brand.setForeground(TEXT_COLOR);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        brand.addMouseListener(createHomeListener());
        left.add(brand);
        menu.add(left, BorderLayout.WEST);

        //  Onglets 
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        center.setBackground(BACKGROUND_COLOR);
        for (String text : new String[]{"Articles", "Marques", "Soldes"}) {
            center.add(createMenuLabel(text));
        }
        menu.add(center, BorderLayout.CENTER);

      
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        right.setBackground(BACKGROUND_COLOR);
        for (String text : new String[]{"Recherche","Mon compte", "Panier"}) {
            right.add(createMenuLabel(text));
        }
        menu.add(right, BorderLayout.EAST);

        add(menu);
    }

    private JLabel createMenuLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setForeground(TEXT_COLOR);
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lbl.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseEntered(MouseEvent e) {
                lbl.setForeground(MENU_HOVER_COLOR);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lbl.setForeground(TEXT_COLOR);
            }
            @Override 
            public void mouseClicked(MouseEvent e) {
                switch (text) {
                    case "Articles"       -> SwingUtilities.invokeLater(() -> new CatalogFrame().setVisible(true));
                    case "Recherche"      -> SwingUtilities.invokeLater(() -> new ArticleSearchFrame().setVisible(true));
                    case "Marques"        -> SwingUtilities.invokeLater(() -> new BrandsFrame().setVisible(true));
                    case "Soldes"         -> SwingUtilities.invokeLater(() -> new SaleFrame().setVisible(true));
                    case "Mon compte"     -> SwingUtilities.invokeLater(() -> new AccountFrame().setVisible(true));
                    case "Panier"         -> {
                        CartController cc = cartController != null
                                ? cartController
                                : ShoppingController.getInstance().getCartController();
                        SwingUtilities.invokeLater(() -> new PanierView(cc).setVisible(true));
                    }
                }
            }
        });
        return lbl;
    }

    private MouseAdapter createHomeListener() {
        return new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
                Window w = SwingUtilities.getWindowAncestor(NavigationBarPanel.this);
                if (w != null) {
                    w.dispose();
                }
                SwingUtilities.invokeLater(() -> new HomeFrame().setVisible(true));
            }
        };
    }
}
