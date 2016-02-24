package com.polarbear.service.balance.to;

import static com.polarbear.util.Constants.PAY_CODE.*;
import static com.polarbear.util.money.PriceCalc.*;
import java.util.ArrayList;
import java.util.List;
import com.polarbear.domain.Address;

public class BalanceDataTO {
    private Address address;
    private ProductDistrict productDistrict;
    private double logisticPrice;
    private double totalPrice;
    private static List<PayDistrict> payDistrict = new ArrayList<PayDistrict>();

    static {
        payDistrict.add(new PayDistrict(ZHI_FU_BAO.value(), ZHI_FU_BAO.name()));
        payDistrict.add(new PayDistrict(WEI_XIN.value(), WEI_XIN.name()));
    }

    public BalanceDataTO() {
    }

    public BalanceDataTO(Address address, List<BuyProduct> productLst) {
        this.address = address;
        this.productDistrict = new ProductDistrict(productLst);
        this.logisticPrice = calcLogisticPrice(productLst);
        this.totalPrice = calcTotalPrice(productLst);
    }

    public Address getAddress() {
        return address;
    }

    public ProductDistrict getProductDistrict() {
        return productDistrict;
    }

    public List<PayDistrict> getPayDistrict() {
        return payDistrict;
    }

    public double getLogisticPrice() {
        return logisticPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}