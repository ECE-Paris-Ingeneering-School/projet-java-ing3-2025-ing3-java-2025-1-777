package view;

import Controlers.ProductController;
import DAO.MarqueDAO;
import DAO.MarqueDAOImpl;
import model.Article;
import model.Marque;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;
/** classe  de dialogue
 * permet la modification des propriétés des articles par l'admin
 */

public class ArticleEditDialog extends JDialog {
    private final ProductController prodCtrl;
    private final Article article;

    private JTextField name, desc, pu, pb, qb, stock;
    private JComboBox<Marque> brandC;

    public ArticleEditDialog(Frame owner, ProductController prodCtrl, Article article) {
        super(owner, article==null?"Ajouter un article":"Modifier un article", true);
        this.prodCtrl = prodCtrl;
        this.article  = article;
        initUI();
    }
    /**
     * Initialise l'interface utilisateur
     * permet d'afficher les champs du formulaire
     */
    private void initUI() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(0,2,10,10));
        form.setBorder(new EmptyBorder(15,15,15,15));
        form.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        form.add(new JLabel("Nom :"));
        name = new JTextField();
        form.add(name);
        form.add(new JLabel("Marque :"));
        brandC = new JComboBox<>();
        form.add(brandC);
        form.add(new JLabel("Description :"));
        desc=new JTextField();
        form.add(desc);
        form.add(new JLabel("Prix Unitaire :"));
        pu=new JTextField();
        form.add(pu);
        form.add(new JLabel("Prix Bulk :"));
        pb=new JTextField();
        form.add(pb);
        form.add(new JLabel("Quantité Bulk :"));
        qb=new JTextField();
        form.add(qb);
        form.add(new JLabel("Stock :"));
        stock=new JTextField();
        form.add(stock);

        add(form, BorderLayout.CENTER);


        MarqueDAO mdao = new MarqueDAOImpl();
        List<Marque> marques = mdao.findAll();
        DefaultComboBoxModel<Marque> cbm = new DefaultComboBoxModel<>();
        for (Marque m : marques) cbm.addElement(m);
        brandC.setModel(cbm);

        if (article != null) {
            name.setText(article.getNom());
            desc.setText(article.getDescription());
            pu.setText(Double.toString(article.getPrixUnitaire()));
            pb.setText(Double.toString(article.getPrixBulk()));
            qb.setText(Integer.toString(article.getQuantiteBulk()));
            stock.setText(Integer.toString(article.getStock()));

            for (int i=0;i<brandC.getItemCount();i++){
                if (brandC.getItemAt(i).getIdMarque()==article.getIdMarque()){
                    brandC.setSelectedIndex(i);
                    break;
                }
            }
        }

        JButton save = new JButton("Enregistrer");
        save.addActionListener(e -> Save());
        add(save, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getOwner());
    }
    /**
     * Récupère les valeurs saisies, crée ou met à jour l'article via le contrôleur
     */
    private void Save() {
        try {
            String nom = name.getText().trim();
            String descr = desc.getText().trim();
            double pru = Double.parseDouble(pu.getText().trim());
            double prb = Double.parseDouble(pb.getText().trim());
            int qtb = Integer.parseInt(qb.getText().trim());
            int st = Integer.parseInt(stock.getText().trim());
            Marque m = (Marque)brandC.getSelectedItem();

            if (nom.isEmpty()||m==null) {
                JOptionPane.showMessageDialog(this,"Le nom et la marque sont obligatoires");
                return;
            }

            Article a = (article!=null ? article : new Article());
            a.setNom(nom);
            a.setDescription(descr);
            a.setPrixUnitaire(pru);
            a.setPrixBulk(prb);
            a.setQuantiteBulk(qtb);
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