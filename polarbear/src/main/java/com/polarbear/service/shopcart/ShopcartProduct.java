package com.polarbear.service.shopcart;

import com.alibaba.fastjson.annotation.JSONField;
import com.polarbear.domain.product.Product;

public class ShopcartProduct {
    @JSONField(serialize = false)
    private Product product;
    private Integer num;

    public ShopcartProduct() {
    }

    public ShopcartProduct(Product product, int num) {
        this.product = product;
        this.num = num;
    }

    public Long getPid() {
        return product.getId();
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

    public double getPrice() {
        return product.getRealPrice();
    }

}