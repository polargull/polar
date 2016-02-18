package com.polarbear.service.balance.to;

import java.util.ArrayList;
import java.util.List;

import com.polarbear.util.money.Arith;

public class ProductDistrict {
    private List<BuyProduct> productList = new ArrayList<BuyProduct>();
    private int totalNum;
    private double totalProductPrice;

    public ProductDistrict(List<BuyProduct> productList) {
        this.productList = productList;
        calcProductTotalPrice(productList);
    }

    private void calcProductTotalPrice(List<BuyProduct> productList) {
        for (BuyProduct p : productList) {
            totalProductPrice = Arith.add(totalProductPrice, Arith.multiply(p.getProductRealPrice(), p.getBuyNum()));
        }
    }

    public BuyProduct getOnlyOneProductInfo() {
        return productList.get(0);
    }

    public String[] getProductsImg() {
        String[] imgs = new String[productList.size()];
        for (int i = 0; i < productList.size(); i++) {
            imgs[i] = productList.get(i).getProductImg();
        }
        return imgs;
    }

    public int getTotalNum() {
        this.totalNum = productList.size();
        return totalNum;
    }

    public double getTotalProductPrice() {
        return totalProductPrice;
    }
}