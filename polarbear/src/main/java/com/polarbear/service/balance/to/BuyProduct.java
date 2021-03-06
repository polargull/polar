package com.polarbear.service.balance.to;

import com.alibaba.fastjson.annotation.JSONField;
import com.polarbear.domain.product.Product;

public class BuyProduct {
    Product product;
    int buyNum;
    long pid;

    public BuyProduct(Product product, int buyNum) {
        super();
        this.product = product;
        this.buyNum = buyNum;
        this.pid = product.getId();
    }

    @JSONField(serialize = false)
    public Product getProduct() {
        return product;
    }


    public long getPid() {
        return pid;
    }

    public double getProductRealPrice() {
        return product.getRealPrice();
    }
    
    public String getProductImg() {
        return product.getImage().split(";")[0];
    }
    
    public String getProductName() {
        return product.getName();
    }

    public int getBuyNum() {
        return buyNum;
    }

}