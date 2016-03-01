package polarbear.integration.service.order;

import static com.polarbear.util.Constants.PAY_CODE.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.createUser1_2ProductUnpayOrder1;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.polarbear.domain.Order;
import com.polarbear.domain.ToPayLog;
import com.polarbear.util.Constants.PAY_CODE;

public class PayOrderServiceTest extends AbstractOrderServiceTest {

    @Before
    public void init() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldSuccessWhenToPayOrder() throws DataAccessException, Exception {
        final Order toPayOrder = createUser1_2ProductUnpayOrder1();
        orderSvc.toPay(toPayOrder.getId(), ZHI_FU_BAO.value());
        assertPayLogListSize(toPayOrder.getId(), 1, ZHI_FU_BAO);
        orderSvc.toPay(toPayOrder.getId(), WEI_XIN.value());
        assertPayLogListSize(toPayOrder.getId(), 2, WEI_XIN);
    }

    private void assertPayLogListSize(long id, int listSize, PAY_CODE payFlatform) {
        List<ToPayLog> toPayLoglst = jdbcTemplate.query("select * from to_pay_log log where log.order_id = ?", new Object[] { id }, BeanPropertyRowMapper
                .newInstance(ToPayLog.class));
        assertThat("订单去支付日志:", toPayLoglst.size(), equalTo(listSize));
        assertThat("订单去支付日志:", toPayLoglst.get(listSize - 1).getPayPlatform(), equalTo(payFlatform.value()));
    }

    @After
    public void cleanUp() throws DataAccessException, Exception {
        super.cleanUp();
    }
}
