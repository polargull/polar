package polarbear.acceptance.balance;

public class ProductDistrict {
    private int totalNum;
    private double totalProductPrice;
    private BuyProduct onlyOneProductInfo;
    private String[] productsImg;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public double getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(double totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public BuyProduct getOnlyOneProductInfo() {
        return onlyOneProductInfo;
    }

    public void setOnlyOneProductInfo(BuyProduct onlyOneProductInfo) {
        this.onlyOneProductInfo = onlyOneProductInfo;
    }

    public String[] getProductsImg() {
        return productsImg;
    }

    public void setProductsImg(String[] productsImg) {
        this.productsImg = productsImg;
    }

}