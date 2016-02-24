package com.polarbear.util;

import java.util.List;

import com.polarbear.service.balance.to.BuyProduct;

public class ProductBuyNumCalc {
    public static int calcProductTotalBuyNum(List<BuyProduct> productList) {
        int buyTotalNums = 0;
        for (BuyProduct buyProduct : productList) {
            buyTotalNums += buyProduct.getBuyNum();
        }
        return buyTotalNums;
    }
}