package view;

import Controlers.ShoppingController; // Importation du contrôleur pour gérer la logique du catalogue
import model.Article; // Importation de la classe Article représentant un produit
import model.Utilisateur; // Importation de la classe Utilisateur pour obtenir les informations de l'utilisateur

import javax.swing.*; // Importation des composants Swing pour l'interface graphique
import java.awt.*; // Importation des classes pour les composants graphiques comme GridLayout, BorderLayout, etc.
import java.util.List; // Importation de la classe List pour gérer la liste des produits

/**
 * Affichage du catalogue avec les produits
 * Cette classe représente la fenêtre qui affiche le catalogue des produits.
 * Elle présente une grille de cartes de produits avec des informations sur chaque article.
 */
public class CatalogFrame extends JFrame {
    private final ShoppingController shoppingController; // Contrôleur pour gérer la logique du catalogue

    /**
     * Constructeur de la classe CatalogFrame.
     * Initialise l'interface utilisateur en passant l'utilisateur et le contrôleur comme paramètres.
     * @param utilisateur L'utilisateur connecté.
     * @param controller Le contrôleur pour interagir avec les données du catalogue.
     */
    public CatalogFrame(Utilisateur utilisateur, ShoppingController controller) {
        this.shoppingController = controller; // Initialisation du contrôleur
        initUI(utilisateur); // Initialisation de l'interface utilisateur
    }

    /**
     * Méthode pour initialiser l'interface utilisateur de la fenêtre de catalogue.
     * Configure les éléments de la fenêtre, y compris la barre de navigation, la grille de produits et le pied de page.
     * @param utilisateur L'utilisateur connecté (pas utilisé ici mais passé pour être potentiellement utilisé plus tard).
     */
    private void initUI(Utilisateur utilisateur) {
        // Configuration de la fenêtre principale
        setTitle("Loro Piana - Catalogue"); // Définir le titre de la fenêtre
        setSize(1200, 800); // Définir la taille de la fenêtre
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Définir l'action de fermeture de la fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre à l'écran
        getContentPane().setBackground(NavigationBarPanel.BACKGROUND_COLOR); // Définir la couleur de fond de la fenêtre
        setLayout(new BorderLayout()); // Définir le layout global de la fenêtre

        // Ajouter la barre de navigation en haut de la fenêtre
        add(new NavigationBarPanel(shoppingController), BorderLayout.NORTH);

        // === GRILLE DE PRODUITS ===
        JPanel catalogPanel = new JPanel(); // Créer un panneau pour afficher les produits
        catalogPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR); // Définir la couleur de fond du panneau
        catalogPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Ajouter des marges autour du panneau
        catalogPanel.setLayout(new GridLayout(0, 3, 20, 20)); // Créer une grille pour afficher les produits avec 3 colonnes

        // Récupérer la liste des produits depuis le contrôleur
        List<Article> products = shoppingController.getCatalogue();
        // Parcourir la liste des produits et ajouter une carte pour chaque produit
        for (Article product : products) {
            ProductCardPanel card = new ProductCardPanel(product, shoppingController); // Créer une carte pour le produit
            catalogPanel.add(card); // Ajouter la carte au panneau de catalogue
        }

        // Ajouter un JScrollPane pour permettre le défilement horizontal/vertical
        JScrollPane scrollPane = new JScrollPane(
                catalogPanel, // Contenu à afficher dans le JScrollPane
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, // Ajouter la barre de défilement verticale si nécessaire
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER // Ne pas ajouter de barre de défilement horizontale
        );
        scrollPane.setBorder(null); // Retirer la bordure du JScrollPane
        scrollPane.getViewport().setBackground(NavigationBarPanel.BACKGROUND_COLOR); // Définir la couleur de fond du viewport
        add(scrollPane, BorderLayout.CENTER); // Ajouter le JScrollPane au centre de la fenêtre

        // === FOOTER (Pied de page) ===
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Créer un panneau pour le pied de page
        footerPanel.setBackground(NavigationBarPanel.BACKGROUND_COLOR); // Définir la couleur de fond du pied de page
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Ajouter des marges autour du panneau
        JLabel footerLabel = new JLabel("© 2025 Loro Piana - All Rights Reserved"); // Texte du pied de page
        footerLabel.setForeground(Color.GRAY); // Définir la couleur du texte en gris
        footerPanel.add(footerLabel); // Ajouter le texte au panneau du pied de page
        add(footerPanel, BorderLayout.SOUTH); // Ajouter le pied de page en bas de la fenêtre
    }
}
