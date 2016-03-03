package polarbear.integration.service.order;

import static com.polarbear.util.Constants.ORDER_STATE.DELIVERY;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectOrder1;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.BUY_PRODUCTS;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.createUser1_2ProductPayOrder3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import task.CreateAcceptanceTestDataTask;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Admin;
import com.polarbear.domain.Logistic;
import com.polarbear.domain.Order;
import com.polarbear.util.factory.CurrentThreadAdminFactory;

public class DeliveryOrderServiceTest extends AbstractOrderServiceTest {
    final String COMPANY = "申通";
    final String LOGISTIC_ORDER_ID = "st1056";
    @Autowired
    BaseDao<Logistic> logisticDao;
    
    @Before
    public void init() throws Exception {
        CurrentThreadAdminFactory.setAdmin(new Admin(1l,"admin","123456"));
        jdbcTemplate.batchUpdate(CreateAcceptanceTestDataTask.createAllTestDataScriptArray());
    }

    @Test
    public void shouldSuccessWhenDeliveryOrder() throws DataAccessException, Exception {
        final Order payedOrder = createUser1_2ProductPayOrder3();
        orderSvc.delivery(payedOrder.getId(), COMPANY, LOGISTIC_ORDER_ID);
        Order actOrder = orderDao.findById(Order.class, payedOrder.getId());
        assertThatOrder(actOrder, expectOrder1(DELIVERY));
        assertRelateOrder(payedOrder.getId(), BUY_PRODUCTS, DELIVERY);
        assertThatLogistic(payedOrder);
    }

    private void assertThatLogistic(Order order) throws DaoException {
//        Logistic logistic = jdbcTemplate.queryForObject("select * from logistic l where l.order_id = ?", new Object[] { orderId }, BeanPropertyRowMapper
//                .newInstance(Logistic.class));
        Logistic logistic = logisticDao.findByNamedQueryObject("queryLogisticByOrder", order);
        assertThat("物流公司名:", logistic.getCompany(), equalTo(COMPANY));
        assertThat("物流订单号:", logistic.getLogisticOrderId(), equalTo(LOGISTIC_ORDER_ID));
        assertThat("物流绑定的订单:", logistic.getOrder().getId(), equalTo(order.getId()));
        assertThat("物流创建时间:", logistic.getCreateTime(), not(nullValue()));
        assertThat("物流退货子订单:", logistic.getOrderList(), nullValue());
    }

    @After
    public void cleanUp() throws DataAccessException, Exception {
        CurrentThreadAdminFactory.remove();
    }
}