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
    static List<BuyProduct> buyProductList = new ArrayList<BuyProduct>();
    static {
        buyProductList.add(new BuyProduct(ProductTestDataFactory.createProduct1(), 1));
        buyProductList.add(new BuyProduct(ProductTestDataFactory.createProduct2(), 1));
    }
    BalanceDataTO to = new BalanceDataTO(null, buyProductList);

    @Test
    public void testBanlanceDataCalc() {
        assertThat(to.getAddress(), nullValue());
        assertThat(to.getLogisticPrice(), equalTo(0d));
        assertThat(to.getProductDistrict().getTotalNum(), equalTo(buyProductList.size()));
        double totalPrice = 0;
        for (BuyProduct buyProduct : buyProductList) {
            totalPrice += buyProduct.getProductRealPrice() * buyProduct.getBuyNum();
        }
        assertThat(to.getTotalPrice(), equalTo(totalPrice));
    }
}
