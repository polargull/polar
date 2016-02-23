package com.polarbear.service.shopcart;

import java.util.List;

import com.polarbear.domain.Shopcart;
import com.polarbear.util.money.Arith;

public class MyShopcart {
    private double totalPrice;
    private int productNum;
    private List<ShopcartProduct> productList;

    public MyShopcart() {
    }

    public MyShopcart(Shopcart shopcart, List<ShopcartProduct> productList) {
        super();
        this.productList = productList;
        this.productNum = shopcart.getProductNum();
        calcTotalPrice();
    }

    public MyShopcart(List<ShopcartProduct> productList) {
        super();
        this.productList = productList;
        calcTotalPrice();
        calcTotalProductNum();
    }

    public void calcTotalProductNum() {
        for (ShopcartProduct sp : productList) {
            productNum += sp.getNum();
        }
    }

    private void calcTotalPrice() {
        for (ShopcartProduct sp : productList) {
            totalPrice = Arith.add(totalPrice, Arith.multiply(sp.getPrice(), sp.getNum()));
        }
    }

    public List<ShopcartProduct> getProductList() {
        return productList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getProductNum() {
        return productNum;
    }

}