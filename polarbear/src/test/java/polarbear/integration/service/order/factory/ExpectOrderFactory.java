package polarbear.integration.service.order.factory;

import static polarbear.test.util.Constants.ORDER_ID_1;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.*;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;
import static polarbear.testdata.builder.order.OrderBuilder.anOrder;
import polarbear.testdata.builder.order.OrderParamBuilder;

import com.polarbear.domain.Order;
import com.polarbear.util.Constants.ORDER_STATE;

public class ExpectOrderFactory {
    public static Order expectCreateOrder(OrderParamBuilder paramBuilder, ORDER_STATE state) {
        return anOrder()
            .withId(ORDER_ID_1)
            .withBuyer(createUser1())
            .withBuyProducts(paramBuilder.getBuyProducts())
            .withContact(paramBuilder.getAddress())
            .withState(state)
            .build();
    }
    
    public static Order expectOrder1(ORDER_STATE state) {
        Order order = createUser1_2ProductUnpayOrder1();
        order.setState(state.value());
        return order;
    }
    
    public static Order expectOrder2(ORDER_STATE state) {
        Order order = createUser1_2ProductDeliveryOrder2();
        order.setState(state.value());
        return order;
    }
    
    public static Order expectOrder3(ORDER_STATE state) {
        Order order = createUser1_2ProductPayOrder3();
        order.setState(state.value());
        return order;
    }
}