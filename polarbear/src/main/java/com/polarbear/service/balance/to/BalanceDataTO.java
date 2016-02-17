package com.polarbear.service.balance.to;

import java.util.List;

import com.polarbear.domain.Address;
import com.polarbear.util.money.Arith;

public class BalanceDataTO {
    private Address address;
    private ProductDistrict productDistrict;
    private PayDistrict payDistrict;
    private double logisticPrice;
    private double totalPrice;

    public BalanceDataTO(Address address, List<BuyProduct> productLst) {
        this.address = address;
        this.productDistrict = new ProductDistrict(productLst);
    }

    public Address getAddress() {
        return address;
    }

    public ProductDistrict getProductDistrict() {
        return productDistrict;
    }

    public PayDistrict getPayDistrict() {
        return payDistrict;
    }

    public double getLogisticPrice() {
        return logisticPrice;
    }

    public double getTotalPrice() {
        return Arith.add(totalPrice, logisticPrice);
    }

}