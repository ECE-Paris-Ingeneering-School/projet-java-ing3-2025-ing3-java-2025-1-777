package model;
/**
 * Classe qui contient les informations essentielles des commandes pour le reporting
 */
public class OrderRow {
    public final int    id;
    public final String dateCmd, due, status;
    public final double total;
    /**
     * Constructeur complet d'une commande pour le reporting.
     */
    public OrderRow(int id, String dateCmd, String due, double total, String status) {
        this.id = id;
        this.dateCmd = dateCmd;
        this.due = due;
        this.total = total;
        this.status = status;
    }
}