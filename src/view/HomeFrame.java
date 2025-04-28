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

    private static final String HERO_IMG = "/images/banniere.jpg";
    private static final String SEC1_IMG = "/images/img1.jpg";
    private static final String SEC2_IMG = "/images/img2.jpg";
    private static final String SEC3_IMG = "/images/img3.jpg";

    /** Largeur max pour tout le contenu (en px) */
    private static final int MAX_CONTENT_WIDTH = 1000;
    /** Dimension des images de section (agrandies) */
    private static final int SECTION_IMG_SIZE = 550;

    private BufferedImage heroImage;
    private final JLabel bannerLabel = new JLabel();

    private final ShoppingController shop = ShoppingController.getInstance();
    private final CartController cart = shop.getCartController();

    public HomeFrame() {
        loadHeroImage();
        buildUI();
        // redimensionne la bannière une première fois après affichage
        SwingUtilities.invokeLater(this::resizeBanner);
    }

    private void loadHeroImage() {
        try (InputStream is = getClass().getResourceAsStream(HERO_IMG)) {
            if (is != null) {
                heroImage = ImageIO.read(is);
            } else {
                heroImage = ImageIO.read(new File("banniere.jpg"));
            }
        } catch (Exception e) {
            heroImage = null;
        }
    }

    private void buildUI() {
        setTitle("Loro Piana – Accueil");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        // Barre de navigation
        add(new NavigationBarPanel(), BorderLayout.NORTH);

        // Wrapper pour centrer main
        JPanel wrapper = new JPanel();
        wrapper.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
        wrapper.add(Box.createHorizontalGlue());

        // Panel vertical principal
        JPanel main = new JPanel();
        main.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setAlignmentY(Component.TOP_ALIGNMENT);
        main.setMaximumSize(new Dimension(MAX_CONTENT_WIDTH, Integer.MAX_VALUE));
        // Bordure droite pour délimiter
        main.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.LIGHT_GRAY));

        // 1) Bannière
        JComponent banner = createBannerComponent();
        banner.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(banner);

        // 2) Intro Baby Cashmere
        JComponent intro = createBabyCashmereIntro();
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(intro);

        // 3) Sections alternées
        String txt1 = "Le cachemire provient du duvet des chèvres Capra Hircus adultes, et le baby cachemire peut uniquement être prélevé sur des chevreaux de moins d’un an. Cette sélection permet de recueillir seulement 30 g de fibres exceptionnellement fines et douces, qui mesurent chacune à peine 13,5 microns de diamètre.\n" +
                " \n" +
                "La chèvre cachemire est une espèce native de régions montagneuses d’Asie, plus particulièrement de Mongolie et de Mongolie intérieure, des espaces désertiques et inhospitaliers où la nourriture et l’eau sont rares, les étés brûlants et les hivers rigoureux. Pour survivre dans cet environnement hostile, ces créatures intrépides développent une couche supplémentaire de poil sous leur pelage extérieur plus grossier, une toison à même la peau et composée de fibres très fines. Ces fibres retiennent l'air et créent une protection très efficace contre le froid, pour maintenir en permanence la température du corps. C’est ce manteau doux qui forme le cachemire.\n" +
                " \n" +
                "Porter un vêtement en baby cachemire, qu'il s'agisse d'une écharpe duveteuse ou d'un pull, c'est s'envelopper de douceur.";
        JComponent sec1 = createAltSection(true, SEC1_IMG, "30 g de pureté", txt1);
        sec1.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(sec1);

        String txt2 = "Depuis 2019, Loro Piana travaille à l’élaboration d’un protocole de certification "
                + "pour une chaîne d’approvisionnement responsable du cachemire. En partenariat avec "
                + "l’ICCAW et la SFA, la Maison fixe des standards exigeants aboutissant à la "
                + "certification des premiers lots en 2021.";
        JComponent sec2 = createAltSection(false, SEC2_IMG,
                "Des normes rigoureuses pour un cachemire d’exception", txt2);
        sec2.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(sec2);

        String txt3 = "Pier Luigi Loro Piana eut en premier l’idée du baby cachemire. Alors qu'il visitait des éleveurs de chèvres chinois, il découvrit la fibre des chevreaux Capra Hircus et, en tant que connaisseur, il s'aperçut immédiatement de sa finesse et de sa douceur, « c’est comme toucher des cheveux de bébé » dira-t-il. Il lui fallut dix ans pour réussir à convaincre un petit nombre d'éleveurs de séparer la fibre des chevreaux de celle des adultes, une tâche ardue compte tenu des quantités infimes que cela représentait. Dans le même temps, la maison avait commencé à faire des essais avec le baby cachemire en Italie, afin de trouver la meilleure manière de filer et tisser cette fibre, tout en respectant sa consistance extrêmement délicate et en mettant en valeur ses propriétés naturelles. Dix ans plus tard, les techniques étaient perfectionnées et le baby cachemire pouvait être utilisé pour fabriquer des vêtements d'une douceur stupéfiante, d'abord des mailles, puis des tissus et enfin des vêtements d'extérieur. Le succès a été immédiat, et Loro Piana comptait dorénavant une nouvelle fibre d’excellence à son portfolio.";
        JComponent sec3 = createAltSection(true, SEC3_IMG,
                "L’idée du baby cashmere", txt3);
        sec3.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(sec3);

        wrapper.add(main);
        wrapper.add(Box.createHorizontalGlue());

        JScrollPane scroll = new JScrollPane(
                wrapper,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setBorder(null);
        scroll.getViewport().setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        add(scroll, BorderLayout.CENTER);
    }

    private JComponent createBannerComponent() {
        // FlowLayout centré pour la bannière
        JPanel bannerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        bannerPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        bannerLabel.setOpaque(true);
        bannerLabel.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        bannerPanel.add(bannerLabel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeBanner();
            }
        });
        return bannerPanel;
    }

    private void resizeBanner() {
        if (heroImage == null) {
            bannerLabel.setText("Image bannière introuvable");
            bannerLabel.setForeground(Color.GRAY);
            return;
        }

        int fullW = getContentPane().getWidth();
        int w = Math.min(fullW, MAX_CONTENT_WIDTH);
        int h = 350;
        if (w <= 0) return;

        Image scaled = heroImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        bannerLabel.setIcon(new ImageIcon(scaled));
        bannerLabel.setPreferredSize(new Dimension(w, h));
        bannerLabel.setMaximumSize(new Dimension(w, h));
        bannerLabel.revalidate();
    }

    private JComponent createBabyCashmereIntro() {
        JPanel p = new JPanel();
        p.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(40, 60, 40, 60));

        JLabel title = centeredLabel("BABY CASHMERE", new Font("Serif", Font.BOLD, 26));
        // on force un maxSize immense pour que BoxLayout l'étire à toute la largeur dispo
        title.setMaximumSize(new Dimension(Integer.MAX_VALUE, title.getPreferredSize().height));

        JLabel subtitle = centeredLabel("Mongolie intérieure", new Font("SansSerif", Font.ITALIC, 18));
        subtitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, subtitle.getPreferredSize().height));

        JTextArea desc = createTextArea(
                "Cette fibre extrêmement fine et d’une douceur exquise est obtenue uniquement "
                        + "à partir du duvet de chevreaux de cachemire. Loro Piana travaille une infime proportion "
                        + "de fibres vierges, avec le soin requis pour les matières les plus précieuses."
        );
        // si vous voulez centrer aussi la zone de texte (optionnel) :
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        // image agrandie
        JLabel img = loadImage(imgPath, SECTION_IMG_SIZE, SECTION_IMG_SIZE);

        JLabel title = new JLabel(titleTxt);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setForeground(NavigationBarPanel.TEXT_COLOR);

        JTextArea body = createTextArea(bodyTxt);

        JPanel textBox = new JPanel();
        textBox.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        textBox.setLayout(new BoxLayout(textBox, BoxLayout.Y_AXIS));
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
        // vous pouvez ajuster cette largeur max si vous souhaitez des paragraphes plus larges
        ta.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        return ta;
    }

    private JLabel loadImage(String path, int w, int h) {
        JLabel l = new JLabel();
        try (InputStream is = getClass().getResourceAsStream(path)) {
            BufferedImage img = (is != null)
                    ? ImageIO.read(is)
                    : ImageIO.read(new File(path.replace("/images/", "")));
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