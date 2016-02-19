package com.polarbear.service.balance.to;

import static com.polarbear.util.Constants.PAY_CODE.WEI_XIN;
import static com.polarbear.util.Constants.PAY_CODE.ZHI_FU_BAO;

import java.util.ArrayList;
import java.util.List;

import com.polarbear.domain.Address;
import com.polarbear.util.money.Arith;

public class BalanceDataTO {
    private Address address;
    private ProductDistrict productDistrict;
    private static List<PayDistrict> payDistrict = new ArrayList<PayDistrict>();
    private double logisticPrice;
    private double totalPrice;

    static {
        payDistrict.add(new PayDistrict(ZHI_FU_BAO.value(), ZHI_FU_BAO.name()));
        payDistrict.add(new PayDistrict(WEI_XIN.value(), WEI_XIN.name()));
    }

    public BalanceDataTO() {
    }

    public BalanceDataTO(Address address, List<BuyProduct> productLst) {
        this.address = address;
        this.productDistrict = new ProductDistrict(productLst);
        calcLogisticPrice();
        calcTotalPrice();
    }

    private void calcLogisticPrice() {
        if (productDistrict.getTotalProductPrice() >= 49d) {
            logisticPrice = 0d;
            return;
        }
        logisticPrice = 10d;
    }

    private void calcTotalPrice() {
        totalPrice = Arith.add(productDistrict.getTotalProductPrice(), logisticPrice);
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