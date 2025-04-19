// util/DiscountApplier.java
package util;

import model.Discount;

public class DiscountApplier {
    public static double applyDiscount(double price, Discount discount) {
        if (discount == null) {
            return price;
        }

        if ("pourcentage".equals(discount.getType())) {
            return price * (1 - discount.getTaux() / 100);
        } else { // remise fixe
            return Math.max(0, price - discount.getTaux());
        }
    }
}