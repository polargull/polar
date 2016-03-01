package polarbear.integration.service.order.factory;

import static com.polarbear.util.Constants.BUY_MODE.*;
import static com.polarbear.util.Constants.PAY_CODE.*;
import static polarbear.testdata.acceptance.testdata.AddressAcceptanceTestDataFactory.*;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.*;
import static polarbear.testdata.builder.order.OrderParamBuilder.anOrderParam;

import com.polarbear.service.balance.to.BuyProduct;

import polarbear.testdata.builder.order.OrderParamBuilder;

public class OrderParamFactory {
    public final static BuyProduct[] BUY_SINGAL_PRODUCT = new BuyProduct[] { 
        new BuyProduct(createProduct1(), 1)
    };
    public final static BuyProduct[] BUY_SHOPCART_PRODUCT = new BuyProduct[] { 
        new BuyProduct(createProduct1(), 1),
        new BuyProduct(createSalePrice6Product3(), 2) 
    };
    
    public static OrderParamBuilder createUser1ImmedidateBuyProduct1OrderParam() {
        return anOrderParam().withBuyProducts(BUY_SINGAL_PRODUCT).withAddress(createUser1Address1()).withBuyMode(IMMEDIDATE).withPayCode(HOU_DAO_FU_KUAN);
    }
    
    public static OrderParamBuilder createUser2ShopcartProduct1And3OrderParam() {
        return anOrderParam().withBuyProducts(BUY_SHOPCART_PRODUCT).withAddress(createUser2Address2()).withBuyMode(SHOPCART).withPayCode(ZHI_FU_BAO);
    }
}