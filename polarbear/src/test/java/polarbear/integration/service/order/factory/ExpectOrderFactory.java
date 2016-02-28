package polarbear.integration.service.order.factory;

import static com.polarbear.util.Constants.ORDER_STATE.*;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.*;
import static polarbear.testdata.acceptance.testdata.AddressAcceptanceTestDataFactory.createUser1Address1;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;
import static polarbear.testdata.builder.order.OrderBuilder.anOrder;
import polarbear.testdata.builder.order.OrderParamBuilder;
import static polarbear.test.util.Constants.*;
import com.polarbear.domain.Order;

public class ExpectOrderFactory {
    public static Order expectCreateOrder(OrderParamBuilder paramBuilder) {
        return anOrder()
            .withId(ORDER_ID_1)
            .withBuyer(createUser1())
            .withBuyProducts(paramBuilder.getBuyProducts())
            .withContact(paramBuilder.getAddress())
            .withState(UNPAY)
            .build();
    }
    
    public static Order expectCancleOrder() {
        Order order = createUser1_2ProductUnpayOrder1();
        order.setState(CANCLE.value());
        return order;
    }
}
