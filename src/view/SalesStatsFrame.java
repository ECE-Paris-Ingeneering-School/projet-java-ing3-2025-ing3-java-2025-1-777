package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SalesStatsFrame extends JFrame {

    public SalesStatsFrame() {
        setTitle("Reporting des ventes");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //filtres date
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        north.setBorder(new EmptyBorder(5,5,5,5));
        SpinnerDateModel m1 = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        SpinnerDateModel m2 = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spFrom = new JSpinner(m1);
        JSpinner spTo   = new JSpinner(m2);
        JSpinner.DateEditor de1 = new JSpinner.DateEditor(spFrom,"dd/MM/yyyy");
        JSpinner.DateEditor de2 = new JSpinner.DateEditor(spTo,  "dd/MM/yyyy");
        spFrom.setEditor(de1); spTo.setEditor(de2);

        north.add(new JLabel("Du")); north.add(spFrom);
        north.add(new JLabel("au")); north.add(spTo);
        JButton btGo = new JButton("Actualiser");
        north.add(btGo);
        add(north, BorderLayout.NORTH);


        JPanel center = new JPanel(new GridLayout(1,2,10,10));
        center.setBorder(new EmptyBorder(10,10,10,10));

        // Placeholder graphique
        JPanel chart = new JPanel(new BorderLayout());
        chart.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        chart.add(new JLabel("Graphique (JFreeChart) ici", SwingConstants.CENTER), BorderLayout.CENTER);

        // Table ventes
        String[] cols = {"Date", "Article", "Qté", "Total €"};
        JTable table = new JTable(new Object[0][cols.length], cols);
        JScrollPane scroll = new JScrollPane(table);

        center.add(chart);
        center.add(scroll);
        add(center, BorderLayout.CENTER);

        // total
        JLabel lblTotal = new JLabel("Total CA : 0 €");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTotal.setBorder(new EmptyBorder(5,10,5,10));
        add(lblTotal, BorderLayout.SOUTH);

        // bouton Actualiser
        btGo.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String d1 = sdf.format(m1.getDate());
            String d2 = sdf.format(m2.getDate());
            //  requête DAO pour remplir le tableau + le graphique + total CA
            JOptionPane.showMessageDialog(this,"(exemple) requête entre "+d1+" et "+d2);
        });
    }
}