package polarbear.integration.service.order;

import static com.polarbear.util.Constants.ORDER_STATE.*;
import static com.polarbear.util.Constants.ResultState.*;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectOrder1;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.*;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser2;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.dao.DataAccessException;

import com.polarbear.domain.Order;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.util.factory.CurrentThreadUserFactory;

public class GetOrderDetailServiceTest extends AbstractOrderServiceTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void init() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldFailWhenGetOrderDetailByNotOrderOwner() throws DataAccessException, Exception {
        CurrentThreadUserFactory.setUser(createUser2());
        expectedEx.expect(OrderStateException.class);
        expectedEx.expectMessage(ORDER_USER_ERR.emsg());
        final Order cancleOrder = createUser1_2ProductUnpayOrder1();
        orderSvc.getMyOrderDetail(cancleOrder.getId());
//        Order actOrder = orderSvc.getMyOrderDetail(createUser1_2ProductUnpayOrder1().getId());
//        assertThatOrder(actOrder, expectOrder(CANCLE));
//        assertRelateOrder(cancleOrder.getId(), BUY_PRODUCTS, CANCLE);
//        assertThatProductNum(BUY_PRODUCTS, DECREASE);
    }

    @Test
    public void shouldSuccessWhenGetOrderDetailByInDeliveryState() throws DataAccessException, Exception {
        final Order unpayOrder = createUser1_2ProductUnpayOrder1();
        Order actOrder = orderSvc.getMyOrderDetail(unpayOrder.getId());
        assertThatOrder(actOrder, expectOrder1(UNPAY));
//        assertRelateOrder(unpayOrder.getId(), BUY_PRODUCTS, UNPAY);
    }

    @After
    public void cleanUp() throws DataAccessException, Exception {
        super.cleanUp();
    }
}
