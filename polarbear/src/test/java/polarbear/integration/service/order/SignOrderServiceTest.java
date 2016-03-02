package polarbear.integration.service.order;

import static com.polarbear.util.Constants.ORDER_STATE.SUCCESS;
import static com.polarbear.util.Constants.ResultState.*;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectOrder1;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.BUY_PRODUCTS;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.dao.DataAccessException;

import com.polarbear.domain.Order;
import com.polarbear.service.order.OrderStateException;

public class SignOrderServiceTest extends AbstractOrderServiceTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void init() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldFailWhenSignOrderInUnpayState() throws DataAccessException, Exception {
        expectedEx.expect(OrderStateException.class);
        expectedEx.expectMessage(ORDER_OPREATE_ERR.emsg());
        final Order cancleOrder = createUser1_2ProductUnpayOrder1();
        orderSvc.sign(cancleOrder.getId());
//        Order actOrder = orderSvc.getMyOrderDetail(createUser1_2ProductUnpayOrder1().getId());
//        assertThatOrder(actOrder, expectOrder(CANCLE));
//        assertRelateOrder(cancleOrder.getId(), BUY_PRODUCTS, CANCLE);
//        assertThatProductNum(BUY_PRODUCTS, DECREASE);
    }

    @Test
    public void shouldSuccessWhenSignOrderInDeliveryState() throws DataAccessException, Exception {
        final Order signOrder = createUser1_2ProductDeliveryOrder2();
        orderSvc.sign(signOrder.getId());
        Order actOrder = orderSvc.getMyOrderDetail(signOrder.getId());
        assertThatOrder(actOrder, expectOrder1(SUCCESS));
        assertRelateOrder(signOrder.getId(), BUY_PRODUCTS, SUCCESS);
    }

    @After
    public void cleanUp() throws DataAccessException, Exception {
        super.cleanUp();
    }
}
