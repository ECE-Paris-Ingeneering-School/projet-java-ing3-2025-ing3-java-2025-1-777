// util/BulkDiscountCalculator.java
package util;

public class BulkDiscountCalculator {
    public static double calculateBulkPrice(int quantity, double unitPrice, double bulkPrice, int bulkQuantity) {
        if (bulkQuantity <= 0) {
            return quantity * unitPrice;
        }

        int bulkGroups = quantity / bulkQuantity;
        int remainingItems = quantity % bulkQuantity;
        return (bulkGroups * bulkPrice) + (remainingItems * unitPrice);
    }
}