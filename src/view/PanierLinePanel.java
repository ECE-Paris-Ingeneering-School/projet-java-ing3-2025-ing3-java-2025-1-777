package view;

import Controlers.CartController;
import model.Article;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


/**  classe qui permet d'affichager la logique du calcul du total par article du panier */
public class PanierLinePanel extends JPanel {
    private final CartController cartController;
    private final Article art;
    private final Runnable onChange;
    private final JSpinner spinner;
    private final JLabel lineDetail;
    private final JLabel bulkLineRed;
    private double currentLineTotal;

    public PanierLinePanel(CartController cartController, Article article, int initQty, Runnable onChange) {
        this.cartController = cartController;
        this.art = article;
        this.onChange = onChange;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, NavigationBarPanel.LINE_COLOR));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        row.setBorder(new EmptyBorder(5, 5, 5, 5));


        JLabel name = new JLabel(art.getNom());
        name.setForeground(NavigationBarPanel.TEXT_COLOR);
        row.add(name);

        spinner = new JSpinner(new SpinnerNumberModel(initQty, 0, art.getStock(), 1));
        spinner.addChangeListener(new ChangeListener() {
            @Override public void stateChanged(ChangeEvent e) {
                int q = (Integer) spinner.getValue();
                cartController.supprimerArticle(art);
                if (q > 0) {
                    cartController.ajouterAuPanier(art, q);
                }
                updateLine();
                onChange.run();
            }
        });
        row.add(spinner);

        JLabel unit = new JLabel(String.format("%.2f € ", art.getPrixUnitaire()));
        unit.setForeground(NavigationBarPanel.TEXT_COLOR);
        row.add(unit);

        JButton del = new JButton("Supprimer");
        del.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        del.setForeground(NavigationBarPanel.TEXT_COLOR);
        del.setBorder(BorderFactory.createLineBorder(NavigationBarPanel.LINE_COLOR));
        del.addActionListener(e -> {
            cartController.supprimerArticle(art);
            onChange.run();
        });
        row.add(del);

        add(row);

        bulkLineRed = new JLabel();
        bulkLineRed.setForeground(Color.RED);
        bulkLineRed.setFont(bulkLineRed.getFont().deriveFont(Font.BOLD));
        add(bulkLineRed);

        lineDetail = new JLabel();
        lineDetail.setForeground(NavigationBarPanel.TEXT_COLOR);
        add(lineDetail);

        updateLine();
    }

    /** Mise à jour du prix en fonction de la quantité choisie */
    private void updateLine() {
        int q = (Integer) spinner.getValue();
        int bulkQty = art.getQuantiteBulk();
        double unitPrice = art.getPrixUnitaire();
        double bulkPrice = art.getPrixBulk();

        if (bulkQty > 0 && q >= bulkQty) {
            int packs = q / bulkQty;
            int rest = q % bulkQty;
            currentLineTotal = packs * bulkPrice + rest * unitPrice;
            bulkLineRed.setText(String.format("Prix lot (%d pcs) : %.2f €", bulkQty, bulkPrice));
            lineDetail.setText(String.format("→ %d×%.2f€ + %d×%.2f€ = %.2f€", packs, bulkPrice, rest, unitPrice, currentLineTotal));
        } else {
            currentLineTotal = q * unitPrice;
            bulkLineRed.setText(" ");
            lineDetail.setText(String.format("→ %d×%.2f€ = %.2f€", q, unitPrice, currentLineTotal));
        }
    }

    /** permet à PanierView de récupérer le total de chaque ligne */
    public double getCurrentLineTotal() {
        return currentLineTotal;
    }

}

