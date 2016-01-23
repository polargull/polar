package com.polarbear.service.shopcart;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.polarbear.domain.Shopcart;
import com.polarbear.util.money.Arith;

public class MyShopcart {
    @JSONField(serialize = false)
    private Shopcart shopcart;
    private List<ShopcartProduct> productList;

    public MyShopcart() {
    }

    public MyShopcart(Shopcart shopcart, List<ShopcartProduct> productList) {
        super();
        this.productList = productList;
        this.shopcart = shopcart;
    }

    public List<ShopcartProduct> getProductList() {
        return productList;
    }

    public double getTotalPrice() {
        double totalPrice = 0d;
        for (ShopcartProduct sp : productList) {
            totalPrice = Arith.add(totalPrice, sp.getPrice());
        }
        return totalPrice;
    }

    public int getProductNum() {
        return shopcart.getProductNum();
    }

}