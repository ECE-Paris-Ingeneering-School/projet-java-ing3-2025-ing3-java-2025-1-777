package view;

import Controlers.CartController;
import Controlers.ShoppingController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

/**
 * Page d’accueil
 */
public class HomeFrame extends JFrame {


    private static final String HERO_IMG = "/images/hero.jpg";
    private static final String SEC1_IMG = "/images/sec1.jpg";
    private static final String SEC2_IMG = "/images/sec2.jpg";
    private static final String SEC3_IMG = "/images/sec3.jpg";

    private BufferedImage heroImage; 
    private final JLabel bannerLabel = new JLabel();
    ShoppingController shop = ShoppingController.getInstance();
    CartController cart = shop.getCartController();


    public HomeFrame() {
        loadHeroImage();
        buildUI();
        
    }

    //la bannière
    private void loadHeroImage() {
        try (InputStream is = getClass().getResourceAsStream(HERO_IMG)) {
            if (is != null) {
                heroImage = ImageIO.read(is);
            } else {
                heroImage = ImageIO.read(new File("hero.jpg")); 
            }
        } catch (Exception e) {
            heroImage = null;
        }
    }

    //interface
    private void buildUI() {
        setTitle("Loro Piana – Accueil");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);


        add(new NavigationBarPanel(), BorderLayout.NORTH);


        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(NavigationBarPanel.BACKGROUND_COLOR);


        main.add(createBannerComponent());


        main.add(createBabyCashmereIntro());


        main.add(createAltSection(true , SEC1_IMG,
                "30 g de pureté",
                "Le cachemire provient du duvet des chèvres Capra Hircus adultes, "
                        + "et le baby cashmere est exclusivement prélevé sur les chevreaux de moins d’un an. "
                        + "Cette sélection ne permet de recueillir que 30 g de fibres exceptionnellement fines "
                        + "et douces par animal, mesurant à peine 13,5 microns de diamètre."));

        main.add(createAltSection(false, SEC2_IMG,
                "Des normes rigoureuses pour un cachemire d’exception",
                "Depuis 2019, Loro Piana travaille à l’élaboration d’un protocole de certification "
                        + "pour une chaîne d’approvisionnement responsable du cachemire. En partenariat avec "
                        + "l’International Cooperation Committee of Animal Welfare (ICCAW) et la Sustainable "
                        + "Fiber Alliance (SFA), la Maison fixe des standards exigeants qui ont abouti à la "
                        + "certification des premiers lots de cachemire en 2021."));

        main.add(createAltSection(true , SEC3_IMG,
                "L’idée du baby cashmere",
                "Lors d’un voyage en Asie, Pier Luigi Loro Piana découvrit la finesse incomparable "
                        + "du duvet des jeunes chevreaux. Dix années de recherche et de collaboration avec "
                        + "les éleveurs ont permis de séparer cette fibre précieuse, puis de mettre au point, "
                        + "en Italie, les techniques de filature et de tissage respectant sa délicatesse. "
                        + "Ainsi naquit une nouvelle fibre d’excellence au sein de la Maison."));

        
        JScrollPane scroll = new JScrollPane(
                main,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setBorder(null);
        scroll.getViewport().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        add(scroll, BorderLayout.CENTER);
    }


   
    private JComponent createBannerComponent() {
        bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bannerLabel.setOpaque(true);
        bannerLabel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        resizeBanner();
        addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) { resizeBanner(); }
        });
        return bannerLabel;
    }
    private void resizeBanner() {
        if (heroImage == null) {
            bannerLabel.setText("Image bannière introuvable");
            bannerLabel.setForeground(Color.GRAY);
            return;
        }
        int w = getContentPane().getWidth();
        int h = 350;                                     
        Image scaled = heroImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        bannerLabel.setPreferredSize(new Dimension(w, h));
        bannerLabel.setIcon(new ImageIcon(scaled));
        bannerLabel.revalidate();
    }


    private JComponent createBabyCashmereIntro() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        p.setBorder(new EmptyBorder(40, 60, 40, 60));

        JLabel title = centeredLabel("BABY CASHMERE", new Font("Serif", Font.BOLD, 26));
        JLabel subtitle = centeredLabel("Mongolie intérieure", new Font("SansSerif", Font.ITALIC, 18));

        JTextArea desc = createTextArea(
                "Cette fibre extrêmement fine et d’une douceur exquise est obtenue uniquement "
                        + "à partir du duvet de chevreaux de cachemire. Loro Piana travaille une infime proportion "
                        + "de fibres vierges, avec le soin requis pour les matières les plus précieuses.");

        p.add(title);
        p.add(Box.createVerticalStrut(8));
        p.add(subtitle);
        p.add(Box.createVerticalStrut(20));
        p.add(desc);

        return p;
    }


    private JComponent createAltSection(boolean imageLeft,
                                        String imgPath,
                                        String titleTxt,
                                        String bodyTxt) {
        JPanel section = new JPanel(new GridBagLayout());
        section.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        section.setBorder(new EmptyBorder(60, 60, 60, 60));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.weightx = gc.weighty = 1.0;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(0, 0, 0, 40);  


        JLabel img = loadImage(imgPath, 450, 450);


        JLabel title = new JLabel(titleTxt);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setForeground(NavigationBarPanel.TEXT_COLOR);

        JTextArea body = createTextArea(bodyTxt);

        JPanel textBox = new JPanel();
        textBox.setLayout(new BoxLayout(textBox, BoxLayout.Y_AXIS));
        textBox.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        textBox.add(title);
        textBox.add(Box.createVerticalStrut(12));
        textBox.add(body);

        if (imageLeft) {
            gc.gridx = 0; section.add(img, gc);
            gc.gridx = 1; section.add(textBox, gc);
        } else {
            gc.gridx = 0; section.add(textBox, gc);
            gc.gridx = 1; section.add(img, gc);
        }
        return section;
    }


    private JLabel centeredLabel(String txt, Font f) {
        JLabel l = new JLabel(txt, SwingConstants.CENTER);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        l.setFont(f);
        l.setForeground(NavigationBarPanel.TEXT_COLOR);
        return l;
    }

    private JTextArea createTextArea(String txt) {
        JTextArea ta = new JTextArea(txt);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setEditable(false);
        ta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ta.setForeground(NavigationBarPanel.TEXT_COLOR);
        ta.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        ta.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        return ta;
    }

    private JLabel loadImage(String path, int w, int h) {
        JLabel l = new JLabel();
        try (InputStream is = getClass().getResourceAsStream(path)) {
            BufferedImage img;
            if (is != null) {
                img = ImageIO.read(is);
            } else {
                img = ImageIO.read(new File(path.replace("/images/", "")));
            }
            l.setIcon(new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            l.setText("Image");
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setPreferredSize(new Dimension(w, h));
            l.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }
        return l;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeFrame().setVisible(true));
    }
}
