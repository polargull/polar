package com.polarbear.service.balance.to;

import com.alibaba.fastjson.annotation.JSONField;
import com.polarbear.domain.product.Product;

public class BuyProduct {
    @JSONField(serialize = false)
    Product product;
    int buyNum;

    public BuyProduct(Product product, int buyNum) {
        super();
        this.product = product;
        this.buyNum = buyNum;
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