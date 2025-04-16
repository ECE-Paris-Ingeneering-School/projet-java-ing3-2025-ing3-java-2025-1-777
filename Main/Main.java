package Main;

import Controlers.ShoppingController;
import view.CatalogFrame;
import javax.swing.*;
import view.LoginFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShoppingController shoppingController = new ShoppingController();
            new LoginFrame(shoppingController).setVisible(true);
        });
    }
}