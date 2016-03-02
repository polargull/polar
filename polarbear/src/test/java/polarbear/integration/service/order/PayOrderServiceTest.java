package polarbear.integration.service.order;

import static com.polarbear.util.Constants.ORDER_STATE.PAYED;
import static com.polarbear.util.Constants.PAY_CODE.WEI_XIN;
import static com.polarbear.util.Constants.PAY_CODE.ZHI_FU_BAO;
import com.polarbear.util.Constants.PAY_CODE;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectOrder1;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.BUY_PRODUCTS;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.createUser1_2ProductUnpayOrder1;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.Pay;
import com.polarbear.domain.ToPayLog;
import com.polarbear.service.order.OrderStateException;

public class PayOrderServiceTest extends AbstractOrderServiceTest {

    @Before
    public void init() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldSuccessWhenToPayOrder() throws DataAccessException, Exception {
        final Order toPayOrder = createUser1_2ProductUnpayOrder1();
        setOrderAlreadyToPay(toPayOrder, ZHI_FU_BAO);
        assertPayLogListSize(toPayOrder.getId(), 1, ZHI_FU_BAO);
        setOrderAlreadyToPay(toPayOrder, WEI_XIN);
        assertPayLogListSize(toPayOrder.getId(), 2, WEI_XIN);
    }

    private void assertPayLogListSize(long id, int listSize, PAY_CODE payFlatform) {
        List<ToPayLog> toPayLoglst = jdbcTemplate.query("select * from to_pay_log log where log.order_id = ?", new Object[] { id }, BeanPropertyRowMapper
                .newInstance(ToPayLog.class));
        assertThat("订单去支付日志:", toPayLoglst.size(), equalTo(listSize));
        assertThat("订单去支付日志:", toPayLoglst.get(listSize - 1).getPayPlatform(), equalTo(payFlatform.value()));
    }

    @Test
    public void shouldSuccessWhenPayOrder() throws DataAccessException, Exception {
        final Order payOrder = createUser1_2ProductUnpayOrder1();
        setOrderAlreadyToPay(payOrder, ZHI_FU_BAO);
        setOrderAlreadyToPay(payOrder, WEI_XIN);
        final String THREE_PART_ID = "1000000000001";        
        orderSvc.pay(payOrder.getId(), THREE_PART_ID);
        Order actOrder = orderSvc.getMyOrderDetail(payOrder.getId());
        assertThatOrder(actOrder, expectOrder1(PAYED));
        assertRelateOrder(actOrder.getId(), BUY_PRODUCTS, PAYED);
        assertPay(payOrder.getId(), WEI_XIN);
    }

    private void assertPay(long orderId, PAY_CODE payFlatform) {
        Pay pay = jdbcTemplate.queryForObject("select * from pay p where p.order_id = ?", new Object[] { orderId }, BeanPropertyRowMapper
                .newInstance(Pay.class));
        assertThat("订单支付平台:", pay.getPayPlatform(), equalTo(payFlatform.value()));
    }
    
    private void setOrderAlreadyToPay(final Order payOrder, PAY_CODE payFlatform) throws DaoException, ValidateException, OrderStateException {
        orderSvc.toPay(payOrder.getId(), payFlatform.value());
    }

    @After
    public void cleanUp() throws DataAccessException, Exception {
        super.cleanUp();
    }
}