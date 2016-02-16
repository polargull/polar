package com.polarbear.domain.product;

import javax.persistence.Embeddable;

@Embeddable
public class Sale {
    Double salePrice;
    Integer saleBeginTime;
    Integer saleEndTime;

    public Sale() {
    }

    public Sale(Integer saleBeginTime, Integer saleEndTime) {
        super();
        this.saleBeginTime = saleBeginTime;
        this.saleEndTime = saleEndTime;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }
        
}
