package polarbear.acceptance.balance;


public class BuyProduct {
    long productId;
    double ProductRealPrice;
    String productImg;
    String productName;
    String productRealPrice;
    int buyNum;

    public BuyProduct() {
    }

    public BuyProduct(long productId, int buyNum) {
        super();
        this.productId = productId;
        this.buyNum = buyNum;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setProductRealPrice(double productRealPrice) {
        ProductRealPrice = productRealPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductRealPrice() {
        return productRealPrice;
    }

    public void setProductRealPrice(String productRealPrice) {
        this.productRealPrice = productRealPrice;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

}