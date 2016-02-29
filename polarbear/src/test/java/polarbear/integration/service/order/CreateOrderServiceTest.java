package polarbear.integration.service.order;

import static com.polarbear.util.Constants.ORDER_STATE.*;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectCreateOrder;
import static polarbear.integration.service.order.factory.OrderParamFactory.createUser1ImmedidateBuyProduct1OrderParam;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.createUser1_2ProductUnpayOrder1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.service.order.OrderStateException;

public class CreateOrderServiceTest extends AbstractOrderServiceTest {

    @Before
    public void init() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldCreateOrderSuccessWhenImmedidateBuyProduct1() throws DaoException, ValidateException, OrderStateException {
        Order newOrder = orderSvc.createOrder(createUser1ImmedidateBuyProduct1OrderParam().build());
        Order actOrder = orderSvc.getMyOrderDetail(newOrder.getId());
        assertThatOrder(actOrder, expectCreateOrder(createUser1ImmedidateBuyProduct1OrderParam()));
        assertRelateOrder(actOrder.getId(), createUser1ImmedidateBuyProduct1OrderParam().getBuyProducts(), UNPAY);
        assertThatProductNum(createUser1ImmedidateBuyProduct1OrderParam().getBuyProducts(), INCREASE);
    }

    @After
    public void teardown() throws DataAccessException, Exception {
        super.cleanUp();
    }

}
