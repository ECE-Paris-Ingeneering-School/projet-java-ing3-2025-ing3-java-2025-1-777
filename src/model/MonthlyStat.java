package model;
/**
 * Classe qui permet de représenter les statistiques mensuelles de commandes pour le reporting
 */
public class MonthlyStat {
    public final int year, month, count;
    public final double sum;
    public MonthlyStat(int year, int month, int count, double sum) {
        this.year = year;
        this.month = month;
        this.count = count;
        this.sum = sum;
    }
}