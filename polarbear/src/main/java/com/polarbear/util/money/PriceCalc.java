package com.polarbear.util.money;

import java.util.List;

import com.polarbear.service.balance.to.BuyProduct;

public class PriceCalc {
    public static double calcProductTotalPrice(List<BuyProduct> buyProducts) {
        double totalProductPrice = 0d;
        for (BuyProduct p : buyProducts) {
            totalProductPrice = Arith.add(totalProductPrice, Arith.multiply(p.getProductRealPrice(), p.getBuyNum()));
        }
        return totalProductPrice;
    }

    public static double calcLogisticPrice(List<BuyProduct> buyProducts) {
        double totalProductPrice = calcProductTotalPrice(buyProducts);
        if (totalProductPrice >= 49d) {
            return 0d;
        }
        return 10d;
    }

    public static double calcTotalPrice(List<BuyProduct> buyProducts) {
        return Arith.add(calcProductTotalPrice(buyProducts), calcLogisticPrice(buyProducts));
    }
}
