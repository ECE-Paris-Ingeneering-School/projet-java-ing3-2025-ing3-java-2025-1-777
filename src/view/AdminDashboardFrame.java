package view;

import javax.swing.*;
import java.awt.*;

/** classe de la Page de l’administrateur*/
public class AdminDashboardFrame extends JFrame {

    public AdminDashboardFrame() {
        setTitle("Admin Dashboard – Application Shopping");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new NavigationBarPanel(), BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(NavigationBarPanel.BACKGROUND_COLOR);
        tabbedPane.addTab("Articles", new ArticleManagementPanel());
        tabbedPane.addTab("Rabais",   new DiscountManagementPanel());
        tabbedPane.addTab("Clients",  new ClientManagementPanel());
        tabbedPane.addTab("Stats",    new ReportingPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}


