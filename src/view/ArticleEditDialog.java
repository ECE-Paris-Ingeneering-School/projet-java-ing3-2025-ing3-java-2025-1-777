package view;

import Controlers.ProductController;
import DAO.MarqueDAO;
import DAO.MarqueDAOImpl;
import model.Article;
import model.Marque;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ArticleEditDialog extends JDialog {
    private final ProductController prodCtrl;
    private final Article article;   // null = création

    private JTextField nameF, descF, puF, pbF, qbF, stF;
    private JComboBox<Marque> brandC;

    public ArticleEditDialog(Frame owner, ProductController prodCtrl, Article article) {
        super(owner, article==null?"Ajouter un article":"Modifier un article", true);
        this.prodCtrl = prodCtrl;
        this.article  = article;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(0,2,10,10));
        form.setBorder(new EmptyBorder(15,15,15,15));
        form.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        form.add(new JLabel("Nom :"));      nameF = new JTextField(); form.add(nameF);
        form.add(new JLabel("Marque :"));   brandC = new JComboBox<>(); form.add(brandC);
        form.add(new JLabel("Description :")); descF=new JTextField(); form.add(descF);
        form.add(new JLabel("Prix Unitaire :")); puF=new JTextField(); form.add(puF);
        form.add(new JLabel("Prix Bulk :"));    pbF=new JTextField(); form.add(pbF);
        form.add(new JLabel("Quantité Bulk :")); qbF=new JTextField(); form.add(qbF);
        form.add(new JLabel("Stock :"));        stF=new JTextField(); form.add(stF);

        add(form, BorderLayout.CENTER);

        // charge marques
        MarqueDAO mdao = new MarqueDAOImpl();
        List<Marque> marques = mdao.findAll();
        DefaultComboBoxModel<Marque> cbm = new DefaultComboBoxModel<>();
        for (Marque m : marques) cbm.addElement(m);
        brandC.setModel(cbm);

        // si édition, pré-remplir
        if (article != null) {
            nameF.setText(article.getNom());
            descF.setText(article.getDescription());
            puF.setText(Double.toString(article.getPrixUnitaire()));
            pbF.setText(Double.toString(article.getPrixBulk()));
            qbF.setText(Integer.toString(article.getQuantiteBulk()));
            stF.setText(Integer.toString(article.getStock()));
            // sélectionner la bonne marque
            for (int i=0;i<brandC.getItemCount();i++){
                if (brandC.getItemAt(i).getIdMarque()==article.getIdMarque()){
                    brandC.setSelectedIndex(i);
                    break;
                }
            }
        }

        JButton save = new JButton("Enregistrer");
        save.addActionListener(e -> onSave());
        add(save, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getOwner());
    }

    private void onSave() {
        try {
            String nom   = nameF.getText().trim();
            String desc  = descF.getText().trim();
            double pu    = Double.parseDouble(puF.getText().trim());
            double pb    = Double.parseDouble(pbF.getText().trim());
            int qb       = Integer.parseInt(qbF.getText().trim());
            int st       = Integer.parseInt(stF.getText().trim());
            Marque m     = (Marque)brandC.getSelectedItem();

            if (nom.isEmpty()||m==null) {
                JOptionPane.showMessageDialog(this,"Nom et marque obligatoires");
                return;
            }

            Article a = (article!=null ? article : new Article());
            a.setNom(nom);
            a.setDescription(desc);
            a.setPrixUnitaire(pu);
            a.setPrixBulk(pb);
            a.setQuantiteBulk(qb);
            a.setStock(st);
            a.setIdMarque(m.getIdMarque());

            if (article==null) {
                prodCtrl.createArticle(a);
            } else{
                prodCtrl.updateArticle(a);
            }

            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"Saisies non valides");
        }
    }
}