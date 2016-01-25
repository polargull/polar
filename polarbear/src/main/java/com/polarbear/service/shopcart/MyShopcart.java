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

    private synchronized void calcTotalPrice() {
        for (ShopcartProduct sp : productList) {
            totalPrice = Arith.add(totalPrice, Arith.multiply(sp.getPrice(), sp.getNum()));
        }
    }
    
    public List<ShopcartProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<ShopcartProduct> productList) {
        this.productList = productList;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

}