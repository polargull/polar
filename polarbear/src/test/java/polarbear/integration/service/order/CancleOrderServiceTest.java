package polarbear.integration.service.order;

import static com.polarbear.util.Constants.ORDER_STATE.CANCLE;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectOrder1;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.BUY_PRODUCTS;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.createUser1_2ProductUnpayOrder1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;

import com.polarbear.domain.Order;

public class CancleOrderServiceTest extends AbstractOrderServiceTest {
    @Before
    public void init() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldSuccessWhenCancleOrder() throws DataAccessException, Exception {
        final String CANCLE_REASON = null;
        final Order cancleOrder = createUser1_2ProductUnpayOrder1();
        orderSvc.cancle(cancleOrder.getId(), CANCLE_REASON);
        Order actOrder = orderSvc.getMyOrderDetail(createUser1_2ProductUnpayOrder1().getId());
        assertThatOrder(actOrder, expectOrder1(CANCLE));
        assertRelateOrder(cancleOrder.getId(), BUY_PRODUCTS, CANCLE);
        assertThatProductNum(BUY_PRODUCTS, DECREASE);
    }

    @After
    public void cleanUp() throws DataAccessException, Exception {
        super.cleanUp();
    }
}
