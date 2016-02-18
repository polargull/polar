package com.polarbear.service.balance.to;

import java.util.List;

import com.polarbear.util.money.Arith;

public class ProductDistrict {
    private int totalNum;
    private double totalProductPrice;
    private BuyProduct onlyOneProductInfo;
    private String[] productsImg;

    public ProductDistrict(List<BuyProduct> productList) {
        calcProductTotalPrice(productList);
        this.totalNum = productList.size();
        setMultiplyProductImgs(productList);
        onlyOneProductInfo = productList.get(0);
    }

    private void setMultiplyProductImgs(List<BuyProduct> productList) {
        productsImg = new String[productList.size()];
        for (int i = 0; i < productList.size(); i++) {
            productsImg[i] = productList.get(i).getProductImg();
        }
    }

    private void calcProductTotalPrice(List<BuyProduct> productList) {
        for (BuyProduct p : productList) {
            totalProductPrice = Arith.add(totalProductPrice, Arith.multiply(p.getProductRealPrice(), p.getBuyNum()));
        }
    }

    public BuyProduct getOnlyOneProductInfo() {
        return onlyOneProductInfo;
    }

    public String[] getProductsImg() {
        return productsImg;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public double getTotalProductPrice() {
        return totalProductPrice;
    }
}