package com.polarbear.service.shopcart;

import com.alibaba.fastjson.annotation.JSONField;
import com.polarbear.domain.Product;

public class ShopcartProduct {
    private Product product;
    private Integer num;

    public ShopcartProduct() {
    }

    public ShopcartProduct(Product product, int num) {
        this.product = product;
        this.num = num;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public double getPrice() {
        return product.getRealPrice();
    }

}