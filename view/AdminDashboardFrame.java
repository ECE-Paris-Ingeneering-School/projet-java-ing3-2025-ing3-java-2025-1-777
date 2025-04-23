package view;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale de l’administration
 */
public class AdminDashboardFrame extends JFrame {
    public AdminDashboardFrame() {
        setTitle("Admin Dashboard – Application Shopping");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Barre de navigation
        add(new NavigationBarPanel(), BorderLayout.NORTH);

        // Onglets d’administration
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Articles", new ArticleManagementPanel());
        tabbedPane.addTab("Rabais",   new DiscountManagementPanel());
        tabbedPane.addTab("Clients",  new ClientManagementPanel());
        tabbedPane.addTab("Stats",    new ReportingPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboardFrame().setVisible(true));
    }
}


