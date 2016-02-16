package com.polarbear.domain.product;

import javax.persistence.Embeddable;

@Embeddable
public class Sale {
    Double salePrice;
    Integer saleBeginTime;
    Integer saleEndTime;    
}