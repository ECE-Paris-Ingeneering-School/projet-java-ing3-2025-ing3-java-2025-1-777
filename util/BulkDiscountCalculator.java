// util/BulkDiscountCalculator.java
package util;

/**
 * Classe utilitaire pour le calcul des prix des articles en fonction des remises en gros.
 * Cette classe contient la méthode qui calcule le prix d'un article en tenant compte d'une remise en gros,
 * où un prix réduit s'applique si la quantité achetée dépasse un certain seuil (bulk quantity).
 */
public class BulkDiscountCalculator {

    /**
     * Calcule le prix total d'un article en fonction de la quantité achetée, du prix unitaire,
     * du prix en gros et de la quantité nécessaire pour bénéficier du prix en gros.
     * Si la quantité achetée dépasse le seuil (bulkQuantity), un prix réduit (bulkPrice) est appliqué pour les articles en gros.
     * Les articles qui ne remplissent pas le seuil bénéficient du prix unitaire classique.
     *
     * @param quantity La quantité d'articles achetés.
     * @param unitPrice Le prix unitaire d'un article.
     * @param bulkPrice Le prix appliqué pour les articles achetés en gros.
     * @param bulkQuantity Le seuil de quantité pour bénéficier du prix en gros.
     * @return Le prix total de l'article après application des remises en gros.
     */
    public static double calculateBulkPrice(int quantity, double unitPrice, double bulkPrice, int bulkQuantity) {
        // Si la quantité minimum pour bénéficier de la remise en gros est inférieure ou égale à 0,
        // on applique simplement le prix unitaire classique.
        if (bulkQuantity <= 0) {
            return quantity * unitPrice; // Pas de remise en gros, on applique le prix unitaire classique
        }

        // Calcul du nombre de groupes d'articles achetés en gros (divisé par le seuil de quantité)
        int bulkGroups = quantity / bulkQuantity; // Nombre d'articles pouvant bénéficier du prix en gros
        // Calcul du nombre d'articles restants qui ne remplissent pas le seuil
        int remainingItems = quantity % bulkQuantity; // Articles restant qui ne bénéficient pas du prix en gros

        // Le prix total est la somme des articles achetés en gros à leur prix réduit,
        // et des articles restants à leur prix unitaire normal.
        return (bulkGroups * bulkPrice) + (remainingItems * unitPrice);
    }
}
