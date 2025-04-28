package model;

/**
 * classe représentant le nombre de commandes et le chiffre d'affaires total.
 */
public class Totals {
    private final int count;
    private final double sum;
    /**
     * Constructeur avec parametre: le nbr de commande et le chiffre d'affaires
     */
    public Totals(int count, double sum) {
        this.count = count;
        this.sum   = sum;
    }
    /**
     * getter qui retourne le nombre total de commandes.
     */
    public int getCount() {
        return count;
    }
    /**
     * setter qui retourne le nombre total de commandes.
     */
    public double getSum() {
        return sum;
    }
}
