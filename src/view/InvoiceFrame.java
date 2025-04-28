package view;

import DAO.LigneCommandeDAO;
import DAO.LigneCommandeDAOImpl;
import DAO.MarqueDAO;
import DAO.MarqueDAOImpl;
import model.Commande;
import model.LigneCommande;
import model.Article;
import model.Marque;
import Controlers.ShoppingController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

/** classe pour afficher la facture */

public class InvoiceFrame extends JFrame {
    private static final Color CREAM = new Color(253, 247, 240);

    public InvoiceFrame(Commande cmd) {
        setTitle("Facture n°" + cmd.getIdCommande());
        setSize(800, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(CREAM);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CREAM);
        header.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("FACTURE", SwingConstants.RIGHT);
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        header.add(title, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JPanel info = new JPanel(new GridLayout(1,2,10,0));
        info.setBackground(CREAM);
        info.setBorder(new EmptyBorder(0,20,10,20));
        JTextArea supplier = new JTextArea("Loro Piana\n" + "01 23 45 67 89\n" + "12 rue de la Paix\n" + "75000 Paris");
        supplier.setOpaque(false);
        supplier.setEditable(false);
        supplier.setFont(new Font("SansSerif", Font.PLAIN, 14));
        info.add(supplier);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        JTextArea inv = new JTextArea("Facture n°" + cmd.getIdCommande() + "\n" + sdf.format(cmd.getDateCommande()));
        inv.setOpaque(false);
        inv.setEditable(false);
        inv.setFont(new Font("SansSerif", Font.PLAIN, 14));
        info.add(inv);

        LigneCommandeDAO ligneDao = new LigneCommandeDAOImpl();
        List<LigneCommande> lignes = ligneDao.findByCommandeId(cmd.getIdCommande());
        List<Article> catalogue = ShoppingController.getInstance().getCatalogue();
        MarqueDAO marqueDao = new MarqueDAOImpl();

        JPanel itemsContainer = new JPanel(new BorderLayout());
        itemsContainer.setBackground(CREAM);
        itemsContainer.setBorder(BorderFactory.createTitledBorder("Articles achetés"));

        JPanel list = new JPanel();
        list.setBackground(CREAM);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBorder(new EmptyBorder(5,5,5,5));

        for (LigneCommande l : lignes) {
            Article a = catalogue.stream().filter(x->x.getIdArticle()==l.getIdArticle()).findFirst().orElse(null);
            if (a == null) {
                continue;
            }

            Marque m = marqueDao.findById(a.getIdMarque());
            String brand = (m!=null? m.getNom() : "");

            int qty = l.getQuantite();
            double unitPrice = a.getPrixUnitaire();
            int bulkQty = a.getQuantiteBulk();
            double bulkPrice = a.getPrixBulk();
            double lineTotal;
            if (bulkQty>0 && qty>=bulkQty) {
                int groups = qty/bulkQty, rem = qty%bulkQty;
                lineTotal = groups*bulkPrice + rem*unitPrice;
            } else {
                lineTotal = qty*unitPrice;
            }

            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(CREAM);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            String leftText = String.format("%s (%s)  –  Qté : %d", a.getNom(), brand, qty);
            JLabel left = new JLabel(leftText);
            left.setFont(new Font("SansSerif", Font.PLAIN, 14));
            row.add(left, BorderLayout.WEST);

            JLabel right = new JLabel(String.format("%.2f €", lineTotal), SwingConstants.RIGHT);
            right.setFont(new Font("SansSerif", Font.PLAIN, 14));
            row.add(right, BorderLayout.EAST);

            list.add(row);
        }

        JScrollPane jsp = new JScrollPane(list);
        jsp.setBorder(null);
        jsp.getViewport().setBackground(CREAM);
        itemsContainer.add(jsp, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(CREAM);
        center.add(info, BorderLayout.NORTH);
        center.add(itemsContainer, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setBackground(CREAM);
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setBorder(new EmptyBorder(10,20,20,20));

        JTextArea bank = new JTextArea("Coordonnées bancaires :\n" + "Banque de Condorcet\n" + "IBAN : FR00 1000 1000 0011 0001 0001 001\n" + "BIC  : CONDFRPP");
        bank.setOpaque(false);
        bank.setEditable(false);
        bank.setFont(new Font("SansSerif", Font.PLAIN, 14));
        footer.add(bank);
        footer.add(Box.createVerticalStrut(20));

        double ht = lignes.stream().mapToDouble(LigneCommande::getPrixTotal).sum();
        double tva = ht * 0.20;
        double ttc = ht + tva;
        footer.add(new JLabel(String.format("Sous-total (HT) : %.2f €", ht)));
        footer.add(new JLabel(String.format("TVA (20%%) : %.2f €", tva)));
        footer.add(Box.createVerticalStrut(5));
        JLabel totLbl = new JLabel(String.format("TOTAL (TTC) : %.2f €", ttc));
        totLbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        footer.add(totLbl);

        footer.add(Box.createVerticalStrut(15));
        JButton close = new JButton("Fermer");
        close.setFont(new Font("SansSerif", Font.BOLD, 14));
        close.addActionListener(e -> dispose());
        JPanel wrap = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrap.setBackground(CREAM);
        wrap.add(close);
        footer.add(wrap);

        add(footer, BorderLayout.SOUTH);
    }
}