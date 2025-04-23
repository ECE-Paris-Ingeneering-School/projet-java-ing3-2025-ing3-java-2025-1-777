package view;

import model.Commande;
import model.LigneCommande;
import model.Article;
import Controlers.ShoppingController;
import DAO.LigneCommandeDAO;
import DAO.LigneCommandeDAOImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class InvoiceFrame extends JFrame {
    private static final Color CREAM = new Color(253, 247, 240);

    public InvoiceFrame(Commande cmd) {
        setTitle("Facture n°" + cmd.getIdCommande());
        setSize(800, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(CREAM);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CREAM);
        header.setBorder(new EmptyBorder(20, 20, 20, 20));

    
        JLabel title = new JLabel("FACTURE", SwingConstants.RIGHT);
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        header.add(title, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Facture
        JPanel info = new JPanel(new GridLayout(1,2));
        info.setBackground(CREAM);
        info.setBorder(new EmptyBorder(0, 20, 20, 20));

        // coordonnées Loro Piana
        JTextArea supplier = new JTextArea( "Loro Piana\n" + "01 23 45 67 89\n" + "12 rue de la Paix\n" + "75000 Paris");
        supplier.setOpaque(false);
        supplier.setEditable(false);
        supplier.setFont(new Font("SansSerif", Font.PLAIN, 14));
        info.add(supplier);

        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        JTextArea inv = new JTextArea("Facture n°" + cmd.getIdCommande() + "\n" +df.format(cmd.getDateCommande()));
        inv.setOpaque(false);
        inv.setEditable(false);
        inv.setFont(new Font("SansSerif", Font.PLAIN, 14));
        info.add(inv);

        add(info, BorderLayout.CENTER);

        //lignes de commande
        String[] cols = {"Article", "Quantité", "Prix unitaire", "Total ligne"};
        DefaultTableModel tm = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tm);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        LigneCommandeDAO ligneDAO = new LigneCommandeDAOImpl();
        List<LigneCommande> lignes = ligneDAO.findByCommandeId(cmd.getIdCommande());
        double sousTotal = 0;
        for (LigneCommande l : lignes) {
            Article a = ShoppingController.getInstance()
                    .getCatalogue()
                    .stream()
                    .filter(x -> x.getIdArticle() == l.getIdArticle())
                    .findFirst()
                    .orElse(null);
            if (a == null) continue;
            double totalL = l.getPrixTotal();
            sousTotal += totalL;
            tm.addRow(new Object[]{
                    a.getNom(),
                    l.getQuantite(),
                    String.format("%.2f €", a.getPrixUnitaire()),
                    String.format("%.2f €", totalL)
            });
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        add(scroll, BorderLayout.SOUTH);

       
        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setBackground(CREAM);
        footer.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Date limite de paiement
        Date dateLimite = Date.from(
                cmd.getDateCommande().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().plusDays(30)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );
        JTextArea bank = new JTextArea( "Coordonnées bancaires :\n" +"Banque de Condorcet\n" + "IBAN : FR00 1001 1001 0101 0001 1001 101\n" +"BIC  : CONDFRPP\n" +"À payer avant le " + df.format(dateLimite));
        bank.setOpaque(false);
        bank.setEditable(false);
        bank.setFont(new Font("SansSerif", Font.PLAIN, 14));
        footer.add(bank);
        footer.add(Box.createVerticalStrut(20));

        // Calcul TVA 
        double tvaRate = 0.20;
        double tva = sousTotal * tvaRate;
        double total = sousTotal + tva;

        JPanel totals = new JPanel(new GridLayout(3, 2, 5, 5));
        totals.setBackground(CREAM);
        totals.add(new JLabel("Sous‑total :", SwingConstants.RIGHT));
        totals.add(new JLabel(String.format("%.2f €", sousTotal)));
        totals.add(new JLabel("TVA (20 %) :", SwingConstants.RIGHT));
        totals.add(new JLabel(String.format("%.2f €", tva)));
        totals.add(new JLabel("TOTAL :", SwingConstants.RIGHT));
        JLabel totLabel = new JLabel(String.format("%.2f €", total));
        totLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totals.add(totLabel);
        footer.add(totals);

        add(footer, BorderLayout.PAGE_END);
    }
}
