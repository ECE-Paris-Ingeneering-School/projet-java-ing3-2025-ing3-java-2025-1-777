package view;

import DAO.DiscountDAO;
import Controlers.ProductController;
import model.Discount;
import model.Article;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;

/**
 * Dialogue pour ajouter ou modifier un rabais.
 * Affiche la description de l'article sélectionné automatiquement.
 */
public class DiscountEditDialog extends JDialog {
    private final DiscountDAO dao;
    private final ProductController prodCtrl;
    private final Discount discount;
    private Article art;               // ← on y stocke l'article sélectionné

    private JComboBox<Article> articleC;
    private JTextArea articleDescArea;
    private JTextField descF;
    private JTextField tauxF;

    /**
     * Constructeur.
     *
     * @param owner         fenêtre parente
     * @param discountDAO   DAO à utiliser pour insert/update
     * @param prodCtrl      contrôleur des produits
     * @param discount      objet existant pour édition, ou null pour création
     */
    public DiscountEditDialog(Window owner,
                              DiscountDAO discountDAO,
                              ProductController prodCtrl,
                              Discount discount) {
        super(owner,
                discount == null ? "Ajouter un rabais" : "Modifier un rabais",
                ModalityType.APPLICATION_MODAL);

        this.dao      = discountDAO;
        this.prodCtrl = prodCtrl;
        this.discount = discount;

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(15, 15, 15, 15));
        form.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.gridx  = 0;
        gbc.gridy  = 0;

        // --- Sélecteur d'article ---
        form.add(new JLabel("Article :"), gbc);
        gbc.gridx = 1;
        articleC = new JComboBox<>();
        List<Article> arts = prodCtrl.getCatalogue();
        for (Article a : arts) articleC.addItem(a);
        // affiche juste le nom
        articleC.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value,
                                                          int index,
                                                          boolean isSel,
                                                          boolean cellFocus) {
                super.getListCellRendererComponent(list, value, index, isSel, cellFocus);
                if (value instanceof Article art) setText(art.getNom());
                return this;
            }
        });
        gbc.gridwidth = 2;
        form.add(articleC, gbc);

        // --- Description de l'article ---
        gbc.gridy++;
        articleDescArea = new JTextArea(3, 30);
        articleDescArea.setLineWrap(true);
        articleDescArea.setWrapStyleWord(true);
        articleDescArea.setEditable(false);
        articleDescArea.setBackground(UIManager.getColor("TextField.background"));
        form.add(new JScrollPane(articleDescArea), gbc);

        // --- Champ description du rabais ---
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Description rabais :"), gbc);
        gbc.gridx = 1;
        descF = new JTextField(30);
        form.add(descF, gbc);

        // --- Champ taux ---
        gbc.gridy++;
        gbc.gridx = 0;
        form.add(new JLabel("Taux (%) :"), gbc);
        gbc.gridx = 1;
        tauxF = new JTextField(10);
        form.add(tauxF, gbc);

        add(form, BorderLayout.CENTER);

        // met à jour art & description à chaque sélection
        articleC.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateArticleDescription();
            }
        });
        // initialisation
        updateArticleDescription();

        // si on édite un rabais existant, on pré-remplit
        if (discount != null) {
            for (int i = 0; i < articleC.getItemCount(); i++) {
                if (articleC.getItemAt(i).getIdArticle() == discount.getIdArticle()) {
                    articleC.setSelectedIndex(i);
                    break;
                }
            }
            descF.setText(discount.getDescription());
            tauxF.setText(Double.toString(discount.getTaux()));
        }

        // bouton Enregistrer
        JButton saveB = new JButton("Enregistrer");
        saveB.addActionListener(e -> onSave());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        south.add(saveB);
        add(south, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getOwner());
    }

    /** Met à jour la zone de description et le champ art selon l'article sélectionné */
    private void updateArticleDescription() {
        Article sel = (Article) articleC.getSelectedItem();
        this.art = sel;
        articleDescArea.setText(sel != null ? sel.getDescription() : "");
    }

    /** Sauvegarde ou met à jour le rabais dans la base */
    private void onSave() {
        String rabDesc = descF.getText().trim();
        String tauxTxt = tauxF.getText().trim();
        if (rabDesc.isEmpty() || tauxTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires");
            return;
        }
        try {
            double taux = Double.parseDouble(tauxTxt);
            Discount d = (discount != null ? discount : new Discount());
            d.setIdArticle(art.getIdArticle());
            d.setDescription(rabDesc);
            d.setTaux(taux);

            if (discount == null) dao.insert(d);
            else              dao.update(d);

            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le taux doit être un nombre valide");
        }
    }
}