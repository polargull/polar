package com.polarbear.service.shopcart;

import java.util.ArrayList;
import java.util.List;

import com.polarbear.domain.Shopcart;

public class MyShopcart {
    private List<ShopcartProduct> productList = new ArrayList<ShopcartProduct>();
    private Shopcart shopcart;

    public MyShopcart() {
    }

    public MyShopcart(List<ShopcartProduct> productList, Shopcart shopcart) {
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