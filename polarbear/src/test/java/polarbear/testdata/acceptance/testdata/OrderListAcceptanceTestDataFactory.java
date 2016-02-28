package polarbear.testdata.acceptance.testdata;

import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.*;
import static polarbear.testdata.builder.order.OrderListBuilder.anOrderList;

import com.polarbear.domain.OrderList;
import static com.polarbear.util.Constants.ORDER_STATE.*;
public class OrderListAcceptanceTestDataFactory {
    public static final Long OID = 1l;    
    public static OrderList createOrder1List1() {
        return anOrderList()
                .withId(OID)
                .withState(UNPAY)
                .withOrder(createUser12ProductUnpayOrder1())
                .withBuyProducts(BUY_PRODUCTS[0])
                .build();
    }
    public static OrderList createOrder1List2() {
        return anOrderList()
                .withId(OID + 1)
                .withState(UNPAY)
                .withOrder(createUser12ProductUnpayOrder1())
                .withBuyProducts(BUY_PRODUCTS[1])
                .build();
    }
}