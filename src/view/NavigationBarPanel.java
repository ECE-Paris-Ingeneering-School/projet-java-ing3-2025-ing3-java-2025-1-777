package view;

import Controlers.ProductController;
import Controlers.ShoppingController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Barre de navigation

 */
public class NavigationBarPanel extends JPanel {

    // palette
    public static final Color BACKGROUND_COLOR = new Color(253, 247, 240);
    static final Color LINE_COLOR      = new Color(111, 57, 46);
    static final Color TEXT_COLOR              = new Color(111, 57, 46);
    static final Color MENU_HOVER_COLOR= new Color(150, 100, 80);

    public NavigationBarPanel() { initUI(); }


    private void initUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BACKGROUND_COLOR);


        JPanel topMsg = new JPanel(new FlowLayout(FlowLayout.CENTER,10,5));
        topMsg.setBackground(BACKGROUND_COLOR);
        JLabel msg = new JLabel("Découvrez la pièce iconique");
        msg.setFont(new Font("SansSerif", Font.ITALIC, 14));
        msg.setForeground(TEXT_COLOR);
        topMsg.add(msg);
        add(topMsg);


        JPanel sep = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(LINE_COLOR);
                g.fillRect(0, getHeight()/2, getWidth(), 2);
            }
        };
        sep.setPreferredSize(new Dimension(10,2));
        sep.setBackground(BACKGROUND_COLOR);
        add(sep);


        JPanel menu = new JPanel(new BorderLayout());
        menu.setBackground(BACKGROUND_COLOR);

        // logo + nom
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(BACKGROUND_COLOR);
        left.setBorder(new EmptyBorder(5,5,5,5));

        ImageIcon logoIc = new ImageIcon("logo_loropiana.png");
        JLabel logo = new JLabel(logoIc);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        logo.addMouseListener(createHomeListener());
        left.add(logo);

        JLabel brand = new JLabel("Loro Piana");
        brand.setFont(new Font("Snell Roundhand", Font.PLAIN, 30));
        brand.setForeground(TEXT_COLOR);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        brand.addMouseListener(createHomeListener());
        left.add(brand);

        menu.add(left, BorderLayout.WEST);

       //onglets
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        center.setBackground(BACKGROUND_COLOR);
        String[] items = {"Articles", "Marques", "Cadeaux", "Art de Vivre"};
        for (String s : items) center.add(createMenuLabel(s));
        menu.add(center, BorderLayout.CENTER);


        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        right.setBackground(BACKGROUND_COLOR);
        String[] acts = {"Recherche", "Compte", "Panier"};
        for (String s : acts) right.add(createMenuLabel(s));
        menu.add(right, BorderLayout.EAST);

        add(menu);
    }

    private JLabel createMenuLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(TEXT_COLOR);
        l.setFont(new Font("SansSerif", Font.PLAIN, 14));

        l.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                l.setForeground(MENU_HOVER_COLOR);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override public void mouseExited(MouseEvent e) {
                l.setForeground(TEXT_COLOR);
                setCursor(Cursor.getDefaultCursor());
            }
            @Override public void mouseClicked(MouseEvent e) {
                switch (text) {
                    case "Panier" -> {
                        if (ShoppingController.getInstance() != null) {
                            new PanierView(ShoppingController.getInstance()
                                    .getCartController()).setVisible(true);
                        } else JOptionPane.showMessageDialog(null,
                                "Erreur : contrôleur non initialisé");
                    }

                    default -> SwingUtilities.invokeLater(() ->
                            new CustomWindow(text).setVisible(true));
                    case "Recherche" -> SwingUtilities.invokeLater(() ->
                            new ArticleSearchFrame().setVisible(true));
                    case "Marques" ->
                            SwingUtilities.invokeLater(() -> new BrandsFrame().setVisible(true));
                    case "Articles" ->
                            SwingUtilities.invokeLater(() -> new CatalogFrame(new ProductController()).setVisible(true));
                }
            }
        });
        return l;
    }

    /* listener logo / nom → HomeFrame */
    private MouseAdapter createHomeListener() {
        return new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                Window w = SwingUtilities.getWindowAncestor(NavigationBarPanel.this);
                if (w != null) w.dispose();                      // ferme fenêtre courante
                SwingUtilities.invokeLater(() -> new HomeFrame().setVisible(true));
            }
        };
    }

    /* ------------------------------------------------------------ */
    public static class CustomWindow extends JFrame {
        public CustomWindow(String item) {
            setTitle("Loro Piana – " + item);
            setSize(1200, 800);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            getContentPane().setBackground(BACKGROUND_COLOR);
            add(new NavigationBarPanel(), BorderLayout.NORTH);

            JPanel content = new JPanel(new BorderLayout());
            content.setBackground(BACKGROUND_COLOR);
            content.setBorder(new EmptyBorder(20,40,20,40));
            JLabel lbl = new JLabel("Bienvenue sur la page " + item, SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 24));
            lbl.setForeground(TEXT_COLOR);
            content.add(lbl, BorderLayout.CENTER);
            add(content, BorderLayout.CENTER);
        }
    }

    /* test isolé */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Test NavBar");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(1200, 200);
            f.add(new NavigationBarPanel(), BorderLayout.NORTH);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}