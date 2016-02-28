package polarbear.unit.dao.order;

import static com.polarbear.util.Constants.ORDER_STATE.PAYED;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.BUY_PRODUCTS;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.createUser12ProductUnpayOrder1;
import static task.CreateAcceptanceTestDataTask.createAllTestDataScriptArray;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.OrderList;
import com.polarbear.domain.User;
import com.polarbear.util.Constants.ORDER_STATE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class OrderListDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private BaseDao<OrderList> orderListDao;

    @Before
    public void setUp() throws DataAccessException, Exception {
        jdbcTemplate.batchUpdate(createAllTestDataScriptArray());
    }

    @Test
    public void testQueryOrderList() throws DaoException {
        List<OrderList> orderLists = orderListDao.findByNamedQuery("queryListByOrderId", createUser12ProductUnpayOrder1());
        assertThat("订单列表商品", orderLists.size(), equalTo(BUY_PRODUCTS.length));
        assertOrderListState(orderLists, ORDER_STATE.UNPAY);
    }

    @Test
    public void testbatchUpdateOrderState() throws DaoException {
        orderListDao.executeUpdate("batchUpdateStateByOrderId", PAYED.value(), createUser12ProductUnpayOrder1());
        List<OrderList> orderLists = jdbcTemplate.query("select * from orderlist where order_id = ?", new Object[] { createUser12ProductUnpayOrder1().getId() }, BeanPropertyRowMapper
                .newInstance(OrderList.class));
        assertThat("订单列表商品", orderLists.size(), equalTo(BUY_PRODUCTS.length));
        assertOrderListState(orderLists, PAYED);
    }

    private void assertOrderListState(List<OrderList> orderLists, ORDER_STATE state) {
        for (OrderList orderList : orderLists) {
            assertThat("订单列表状态", orderList.getState(), equalTo(state.value()));
        }
    }
}