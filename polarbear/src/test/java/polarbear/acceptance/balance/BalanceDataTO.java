package polarbear.acceptance.balance;

import java.util.List;

import com.polarbear.domain.Address;

public class BalanceDataTO {
    private Address address;
    private List<PayDistrict> payDistrict;
    private ProductDistrict productDistrict;
    private double logisticPrice;
    private double totalPrice;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ProductDistrict getProductDistrict() {
        return productDistrict;
    }

    public void setProductDistrict(ProductDistrict productDistrict) {
        this.productDistrict = productDistrict;
    }

    public List<PayDistrict> getPayDistrict() {
        return payDistrict;
    }

    public void setPayDistrict(List<PayDistrict> payDistrict) {
        this.payDistrict = payDistrict;
    }

    public double getLogisticPrice() {
        return logisticPrice;
    }

    public void setLogisticPrice(double logisticPrice) {
        this.logisticPrice = logisticPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}