package view;

import javax.swing.*;
import java.awt.*;

public class OrderConfirmationFrame extends JFrame {
    public OrderConfirmationFrame(double total) {
        setTitle("Confirmation");
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel message = new JLabel(
                "<html><center>Merci pour votre commande !<br>"
                        + "Montant total: " + String.format("%.2f €", total) + "</center></html>",
                SwingConstants.CENTER
        );
        message.setFont(new Font("SansSerif", Font.PLAIN, 16));

        panel.add(message, BorderLayout.CENTER);
        add(panel);
    }
}