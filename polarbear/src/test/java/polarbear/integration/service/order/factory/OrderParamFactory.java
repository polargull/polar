package polarbear.integration.service.order.factory;

import static com.polarbear.util.Constants.BUY_MODE.IMMEDIDATE;
import static com.polarbear.util.Constants.PAY_CODE.HOU_DAO_FU_KUAN;
import static polarbear.testdata.acceptance.testdata.AddressAcceptanceTestDataFactory.createUser1Address1;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct1;
import static polarbear.testdata.builder.order.OrderParamBuilder.anOrderParam;

import com.polarbear.service.balance.to.BuyProduct;

import polarbear.testdata.builder.order.OrderParamBuilder;

public class OrderParamFactory {
    final static BuyProduct[] BUY_SINGAL_PRODUCT = new BuyProduct[] { new BuyProduct(createProduct1(), 1) };
    
    public static OrderParamBuilder createUser1ImmedidateBuyProduct1OrderParam() {
        return anOrderParam().withBuyProducts(BUY_SINGAL_PRODUCT).withAddress(createUser1Address1()).withBuyMode(IMMEDIDATE).withPayCode(HOU_DAO_FU_KUAN);
    }
}
