// util/DiscountApplier.java
package util;

import model.Discount; // Importation de la classe Discount, qui représente une réduction

/**
 * Classe utilitaire pour appliquer des remises sur un prix.
 * Cette classe contient une méthode qui applique une remise (en pourcentage ou en montant fixe) sur un prix donné.
 */
public class DiscountApplier {

    /**
     * Applique une remise sur un prix donné.
     * Si la remise est de type "pourcentage", elle est appliquée en réduisant le prix en fonction du taux de remise.
     * Si la remise est de type "montant", elle est appliquée en soustrayant la valeur fixe du prix.
     *
     * @param price Le prix initial de l'article avant remise.
     * @param discount L'objet Discount contenant les informations de la remise à appliquer.
     * @return Le prix après application de la remise.
     */
    public static double applyDiscount(double price, Discount discount) {
        // Si aucune remise n'est spécifiée (discount est null), le prix reste inchangé
        if (discount == null) {
            return price; // Retourne simplement le prix d'origine
        }

        // Si le type de remise est "pourcentage", on applique la remise en pourcentage
        if ("pourcentage".equals(discount.getType())) {
            return price * (1 - discount.getTaux() / 100); // Calcule le prix après réduction en pourcentage
        } else { // Si la remise est de type "montant", on applique la réduction en valeur fixe
            return Math.max(0, price - discount.getTaux()); // On soustrait la remise, en s'assurant que le prix ne devient pas négatif
        }
    }
}
