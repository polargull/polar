package polarbear.testdata.acceptance.testdata;

import static com.polarbear.util.Constants.ORDER_STATE.*;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct1;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct2;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;
import static polarbear.testdata.acceptance.testdata.AddressAcceptanceTestDataFactory.createUser1Address1;
import static polarbear.testdata.builder.order.OrderBuilder.anOrder;

import com.polarbear.domain.Order;
import com.polarbear.service.balance.to.BuyProduct;

public class OrderAcceptanceTestDataFactory {
    public static final Long OID = 1l;
    public final static BuyProduct[] BUY_PRODUCTS = new BuyProduct[] { new BuyProduct(createProduct1(), 1), new BuyProduct(createProduct2(), 2) };

    public static Order createUser1_2ProductUnpayOrder1() {
        return anOrder()
        .withId(OID)
        .withBuyer(createUser1())
        .withBuyProducts(BUY_PRODUCTS)
        .withContact(createUser1Address1())
        .withState(UNPAY)
        .build();
    }
    
    public static Order createUser1_2ProductDeliveryOrder2() {
        return anOrder()
        .withId(OID + 1)
        .withBuyer(createUser1())
        .withBuyProducts(BUY_PRODUCTS)
        .withContact(createUser1Address1())
        .withState(DELIVERY)
        .build();
    }
    
    public static Order createUser1_2ProductPayOrder3() {
        return anOrder()
        .withId(OID + 2)
        .withBuyer(createUser1())
        .withBuyProducts(BUY_PRODUCTS)
        .withContact(createUser1Address1())
        .withState(PAYED)
        .build();
    }
}
