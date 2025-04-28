package view;

import Controlers.CartController;
import Controlers.ShoppingController;
import DAO.CommandeDAO;
import DAO.CommandeDAOImpl;
import DAO.LigneCommandeDAO;
import DAO.LigneCommandeDAOImpl;
import model.Commande;
import model.LigneCommande;
import model.Article;
import model.Utilisateur;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.*;
/** classe du compte de l'utilisateur (client)
 * Permet d'afficher les informations personnelles du client et de consulter ses commandes
 */

public class AccountFrame extends JFrame {
    private final ShoppingController shop = ShoppingController.getInstance();
    private final CartController cartController = shop.getCartController();
    private final Utilisateur currentUser = shop.getCurrentUser();
    private final CommandeDAO commandeDAO = new CommandeDAOImpl();
    private final LigneCommandeDAO ligneDAO = new LigneCommandeDAOImpl();

    public AccountFrame() {
        setTitle("Mon Compte – Loro Piana");
        setSize(860, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());


        add(new NavigationBarPanel(cartController), BorderLayout.NORTH);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        main.add(new JLabel("Nom : " + currentUser.getNom()));
        main.add(new JLabel("Prénom : " + currentUser.getPrenom()));
        main.add(new JLabel("Email : " + currentUser.getEmail()));
        main.add(Box.createVerticalStrut(15));

        // historique des commandes
        String[] cols = {"N° commande", "Articles", "Date cmd", "Date livraison", "Total (€)", "Statut"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable table = new JTable(model);
        List<Commande> liste = commandeDAO.findByUser(currentUser.getIdUtilisateur());
        for (Commande c : liste) {
            List<LigneCommande> lignes = ligneDAO.findByCommandeId(c.getIdCommande());
            String artList = lignes.stream()
                    .map(l -> {
                        Article a = shop.getCatalogue().stream().filter(x->x.getIdArticle()==l.getIdArticle()).findFirst().orElse(null);
                        return a!=null
                                ? a.getNom() + " ×" + l.getQuantite()
                                : "";
                    })
                    .filter(s->!s.isBlank())
                    .collect(Collectors.joining(", "));
            model.addRow(new Object[]{
                    c.getIdCommande(),
                    artList,
                    c.getDateCommande(),
                    c.getDateLivraison(),
                    String.format("%.2f", c.getTotalCommande()),
                    c.getStatus()
            });
        }
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(840, 300));
        main.add(scroll);
        main.add(Box.createVerticalStrut(10));


        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btns.setBackground(NavigationBarPanel.BACKGROUND_COLOR);

        JButton cancel = new JButton("Annuler la commande sélectionnée");
        cancel.addActionListener((ActionEvent e) -> {
            int r = table.getSelectedRow();
            if (r<0) { JOptionPane.showMessageDialog(this,"Sélectionnez une commande"); return; }
            int id = (int)model.getValueAt(r,0);
            if (commandeDAO.annulerCommande(id)) {
                JOptionPane.showMessageDialog(this,"Commande annulée");
                model.removeRow(r);
            } else {
                JOptionPane.showMessageDialog(this,"Erreur lors de l'annulation","Erreur",JOptionPane.ERROR_MESSAGE);
            }
        });
        btns.add(cancel);


        JButton view = new JButton("Voir facture");
        view.addActionListener(ev -> {
            int r = table.getSelectedRow();
            if (r<0) {
                JOptionPane.showMessageDialog(this, "Sélectionnez une commande");
                return;
            }
            int id = (int) model.getValueAt(r, 0);
            Commande c = commandeDAO.findById(id);
            if (c!=null) {
                new InvoiceFrame(c).setVisible(true);
            }
        });
        btns.add(view);

        main.add(btns);

        add(new JScrollPane(main), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccountFrame().setVisible(true));
    }
}