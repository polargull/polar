package com.polarbear.service.shopcart;

import java.util.List;

import com.polarbear.domain.Shopcart;

public class MyShopcart {
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

    public void setProductList(List<ShopcartProduct> productList) {
        this.productList = productList;
    }

    public Shopcart getShopcart() {
        return shopcart;
    }

    public void setShopcart(Shopcart shopcart) {
        this.shopcart = shopcart;
    }

}