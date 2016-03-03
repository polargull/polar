package polarbear.testdata.acceptance.testdata;

import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.*;
import static polarbear.testdata.builder.order.OrderListBuilder.anOrderList;

import com.polarbear.domain.OrderList;
import static com.polarbear.util.Constants.ORDER_STATE.*;
public class OrderListAcceptanceTestDataFactory {
    public static final Long OID = 0l;
    public static OrderList createOrder1List1() {
        return anOrderList()
                .withId(OID + 1)
                .withState(UNPAY)
                .withOrder(createUser1_2ProductUnpayOrder1())
                .withBuyProducts(BUY_PRODUCTS[0])
                .build();
    }
    public static OrderList createOrder1List2() {
        return anOrderList()
                .withId(OID + 2)
                .withState(UNPAY)
                .withOrder(createUser1_2ProductUnpayOrder1())
                .withBuyProducts(BUY_PRODUCTS[1])
                .build();
    }
    public static OrderList createOrder2List1() {
        return anOrderList()
                .withId(OID + 3)
                .withState(DELIVERY)
                .withOrder(createUser1_2ProductDeliveryOrder2())
                .withBuyProducts(BUY_PRODUCTS[0])
                .build();
    }
    public static OrderList createOrder2List2() {
        return anOrderList()
                .withId(OID + 4)
                .withState(DELIVERY)
                .withOrder(createUser1_2ProductDeliveryOrder2())
                .withBuyProducts(BUY_PRODUCTS[1])
                .build();
    }
    public static OrderList createOrder3List1() {
        return anOrderList()
                .withId(OID + 5)
                .withState(PAYED)
                .withOrder(createUser1_2ProductPayOrder3())
                .withBuyProducts(BUY_PRODUCTS[0])
                .build();
    }
    public static OrderList createOrder3List2() {
        return anOrderList()
                .withId(OID + 6)
                .withState(PAYED)
                .withOrder(createUser1_2ProductPayOrder3())
                .withBuyProducts(BUY_PRODUCTS[1])
                .build();
    }
}