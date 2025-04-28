// src/view/ConfirmationFrame.java
package view;

import DAO.CommandeDAO;
import DAO.CommandeDAOImpl;
import DAO.LigneCommandeDAO;
import DAO.LigneCommandeDAOImpl;
import Controlers.ShoppingController;
import model.Commande;
import model.LigneCommande;
import model.Article;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConfirmationFrame extends JFrame {
    private static final Color CREAM = new Color(253, 247, 240);

    public ConfirmationFrame(int commandeId) {
        CommandeDAO cmdDao = new CommandeDAOImpl();
        Commande cmd       = cmdDao.findById(commandeId);
        LigneCommandeDAO ligneDao = new LigneCommandeDAOImpl();
        List<LigneCommande> lignes = ligneDao.findByCommandeId(commandeId);
        List<Article> catalogue = ShoppingController
                .getInstance()
                .getCatalogue();

        setTitle("Confirmation – Commande n°" + commandeId);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(CREAM);
        setLayout(new BorderLayout(10,10));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CREAM);
        header.setBorder(new EmptyBorder(20,20,0,20));
        JLabel lbl = new JLabel("Votre commande est confirmée !", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.add(lbl, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // INFOS COMMANDE
        JPanel info = new JPanel(new GridLayout(3,1,5,5));
        info.setBackground(CREAM);
        info.setBorder(new EmptyBorder(0,20,0,20));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        info.add(new JLabel("N° commande      : " + commandeId));
        info.add(new JLabel("Date de commande : " +
                cmd.getDateCommande().toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate().format(df)
        ));
        info.add(new JLabel("Livraison prévue  : " +
                cmd.getDateLivraison().toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate().format(df)
        ));
        add(info, BorderLayout.CENTER);

        // DÉTAILS LIGNES
        String[] cols = {"Article", "Qté", "Prix unitaire", "Total ligne"};
        DefaultTableModel tm = new DefaultTableModel(cols,0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable table = new JTable(tm);
        for (LigneCommande l : lignes) {
            Article a = catalogue.stream()
                    .filter(x->x.getIdArticle()==l.getIdArticle())
                    .findFirst().orElse(null);
            if (a!=null) {
                tm.addRow(new Object[]{
                        a.getNom(),
                        l.getQuantite(),
                        String.format("%.2f €", a.getPrixUnitaire()),
                        String.format("%.2f €", l.getPrixTotal())
                });
            }
        }
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(10,20,10,20));
        add(scroll, BorderLayout.SOUTH);

        // FOOTER : totaux & fermer
        double ht  = lignes.stream().mapToDouble(LigneCommande::getPrixTotal).sum();
        double tva = ht * 0.20;
        double ttc = ht + tva;

        JPanel footer = new JPanel();
        footer.setBackground(CREAM);
        footer.setBorder(new EmptyBorder(0,20,20,20));
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.add(new JLabel(String.format("Sous-total (HT): %.2f €", ht)));
        footer.add(new JLabel(String.format("TVA (20%%)     : %.2f €", tva)));
        footer.add(Box.createVerticalStrut(5));
        JLabel tot = new JLabel(String.format("Total (TTC)   : %.2f €", ttc));
        tot.setFont(new Font("SansSerif", Font.BOLD, 16));
        footer.add(tot);
        footer.add(Box.createVerticalStrut(15));

        JButton close = new JButton("Fermer");
        close.setFont(new Font("SansSerif", Font.BOLD, 14));
        close.addActionListener(e -> dispose());
        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnWrap.setBackground(CREAM);
        btnWrap.add(close);
        footer.add(btnWrap);

        add(footer, BorderLayout.PAGE_END);
    }
}