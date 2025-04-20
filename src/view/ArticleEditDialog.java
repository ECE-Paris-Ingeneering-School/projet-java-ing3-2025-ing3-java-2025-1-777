package view;

import Controlers.ProductController;
import DAO.MarqueDAO;
import DAO.MarqueDAOImpl;
import model.Article;
import model.Marque;

import javax.swing.*;
import java.awt.*;

public class ArticleEditDialog extends JDialog {

    private final JTextField tfNom   = new JTextField(20);
    private final JTextField tfPrix  = new JTextField(8);
    private final JTextField tfStock = new JTextField(5);
    private final JComboBox<Marque> cbMarque = new JComboBox<>();

    private final ProductController prodCtrl;
    private Article article;

    public ArticleEditDialog(Window parent, ProductController ctrl, Article a) {
        super(parent, (a == null ? "Nouvel article" : "Modifier article"), ModalityType.APPLICATION_MODAL);
        this.prodCtrl = ctrl;
        this.article  = a;
        buildUI();
        populate();
        pack();
        setLocationRelativeTo(parent);
    }

    private void buildUI() {
        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.add(new JLabel("Nom"));   form.add(tfNom);
        form.add(new JLabel("Prix (€)")); form.add(tfPrix);
        form.add(new JLabel("Stock")); form.add(tfStock);
        form.add(new JLabel("Marque"));form.add(cbMarque);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Annuler");
        buttons.add(ok); buttons.add(cancel);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        cancel.addActionListener(e -> dispose());
        ok.addActionListener(e -> saveAndClose());
    }

    private void populate() {

        MarqueDAO mdao = new MarqueDAOImpl();
        mdao.findAll().forEach(cbMarque::addItem);

        if (article != null) {
            tfNom.setText(article.getNom());
            tfPrix.setText(String.valueOf(article.getPrixUnitaire()));
            tfStock.setText(String.valueOf(article.getStock()));
            for (int i=0;i<cbMarque.getItemCount();i++)
                if (cbMarque.getItemAt(i).getIdMarque()==article.getIdMarque())
                    cbMarque.setSelectedIndex(i);
        }
    }

    private void saveAndClose() {
        try {
            String nom   = tfNom.getText().trim();
            double prix  = Double.parseDouble(tfPrix.getText().trim().replace(',','.'));
            int stock    = Integer.parseInt(tfStock.getText().trim());
            int idMarque = ((Marque)cbMarque.getSelectedItem()).getIdMarque();

            if (nom.isEmpty()) throw new Exception();
            if (article == null) article = new Article();
            article.setNom(nom);
            article.setPrixUnitaire(prix);
            article.setStock(stock);
            article.setIdMarque(idMarque);

            boolean ok = (article.getIdArticle()==0)
                    ? prodCtrl.addProduct(article)
                    : prodCtrl.updateProduct(article);

            if (!ok) throw new Exception("échec DAO");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,"Données invalides","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }
}