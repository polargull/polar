package polarbear.acceptance.shopcart.bean;

import java.util.List;

public class MyShopcart {
    private double totalPrice;
    private int productNum;
    private List<ShopcartProduct> productList;

    public MyShopcart() {
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public List<ShopcartProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<ShopcartProduct> productList) {
        this.productList = productList;
    }

}