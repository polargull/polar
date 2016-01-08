package com.polarbear.service.shopcart;

import com.alibaba.fastjson.annotation.JSONField;
import com.polarbear.domain.Product;

public class ShopcartProduct {
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    @JSONField(serialize = false)
    public Product getProduct() {
        return product;
    }

    public String getName() {
        return product.getName();
    }

    public String getImg() {
        return product.getImage();
    }

    public int getCount() {
        return product.getNum();
    }

    public double getPrice() {
        return product.getPrice();
    }

}