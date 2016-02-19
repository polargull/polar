package polarbear.unit.service.balance;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import polarbear.testdata.acceptance.testdata.product.ProductTestDataFactory;

import com.polarbear.service.balance.to.BalanceDataTO;
import com.polarbear.service.balance.to.BuyProduct;

public class BalanceDataTOTest {

    @Test
    public void shouldReturnLogsticsIs10BalanceDataWhenPriceTotalPriceGreaterThan49() {
        List<BuyProduct> buyProductList = new ArrayList<BuyProduct>();
        buyProductList.add(new BuyProduct(ProductTestDataFactory.createProduct1(), 1));
        buyProductList.add(new BuyProduct(ProductTestDataFactory.createProduct2(), 1));
        BalanceDataTO to = new BalanceDataTO(null, buyProductList);
        
        assertThat(to.getAddress(), nullValue());
        assertThat(to.getLogisticPrice(), equalTo(0d));
        assertThat(to.getProductDistrict().getTotalNum(), equalTo(buyProductList.size()));
        double totalPrice = totalProductPrice(buyProductList);
        assertThat(to.getTotalPrice(), equalTo(totalPrice + 0));
    }

    @Test
    public void shouldReturnLogsticsIs0BalanceDataWhenPriceTotalPriceLessThan49() {
        List<BuyProduct> buyProductList = new ArrayList<BuyProduct>();
        buyProductList.add(new BuyProduct(ProductTestDataFactory.createSalePrice6Product(), 1));
        BalanceDataTO to = new BalanceDataTO(null, buyProductList);
        
        assertThat(to.getAddress(), nullValue());
        assertThat(to.getLogisticPrice(), equalTo(10d));
        assertThat(to.getProductDistrict().getTotalNum(), equalTo(buyProductList.size()));
        double totalPrice = totalProductPrice(buyProductList);
        assertThat(to.getTotalPrice(), equalTo(totalPrice + 10));
    }

    private double totalProductPrice(List<BuyProduct> buyProductList) {
        double totalPrice = 0;
        for (BuyProduct buyProduct : buyProductList) {
            totalPrice += buyProduct.getProductRealPrice() * buyProduct.getBuyNum();
        }
        return totalPrice;
    }
}
