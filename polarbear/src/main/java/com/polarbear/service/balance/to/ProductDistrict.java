package com.polarbear.service.balance.to;

import java.util.List;
import static com.polarbear.util.money.PriceCalc.*;
import static com.polarbear.util.ProductBuyNumCalc.*;

public class ProductDistrict {
    private int totalNum;
    private double totalProductPrice;
    private BuyProduct onlyOneProductInfo;
    private String[] productsImg;
    private Long[] productIds;
    private Integer[] productNums;

    public ProductDistrict(List<BuyProduct> productList) {
        this.totalProductPrice = calcProductTotalPrice(productList);
        this.totalNum = calcProductTotalBuyNum(productList);
        this.onlyOneProductInfo = productList.get(0);
        setProductData(productList);
    }

    private void setProductData(List<BuyProduct> productList) {
        this.productsImg = new String[productList.size()];
        this.productIds = new Long[productList.size()];
        this.productNums = new Integer[productList.size()];
        for (int i = 0; i < productList.size(); i++) {
            this.productsImg[i] = productList.get(i).getProductImg();
            this.productIds[i] = productList.get(i).getPid();
            this.productNums[i] = productList.get(i).getBuyNum();
        }
    }

    public BuyProduct getOnlyOneProductInfo() {
        return onlyOneProductInfo;
    }

    public String[] getProductsImg() {
        return productsImg;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public double getTotalProductPrice() {
        return totalProductPrice;
    }
}