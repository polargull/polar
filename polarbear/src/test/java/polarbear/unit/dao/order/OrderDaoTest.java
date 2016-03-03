package polarbear.unit.dao.order;

import static com.polarbear.util.Constants.ORDER_STATE.UNPAY;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.createUser1_2ProductUnpayOrder1;
import static task.CreateAcceptanceTestDataTask.createAllTestDataScriptArray;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.util.date.DateUtil;

@ContextConfiguration(locations = {"/spring/spring-dao.xml" })
public class OrderDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private BaseDao<Order> orderDao;

    @Before
    public void setUp() throws DataAccessException, Exception {
        jdbcTemplate.batchUpdate(createAllTestDataScriptArray());
    }

    @Test
    public void testCreateOrder() throws DaoException {
        final int PRODUCT_TOTAL_NUMS = 1;
        final double PRODUCT_TOTAL_PRICE = 100;
        final String CONTACT = "xxxx|xxxxx|xxxxx";
        final double LOGISTIC_PRICE = 10;
        final int STATE = UNPAY.value();
        final int CURTIME = DateUtil.getCurrentSeconds();
        Order newOrder = new Order(createUser1(), PRODUCT_TOTAL_NUMS, PRODUCT_TOTAL_PRICE, CONTACT, LOGISTIC_PRICE, STATE, CURTIME, CURTIME);
        orderDao.store(newOrder);
//        Order actOrder = jdbcTemplate.queryForObject("select * from Orders where id = ?", new Object[] { newOrder.getId() }, BeanPropertyRowMapper.newInstance(Order.class));
        Order actOrder = orderDao.findById(Order.class, newOrder.getId());
        assertThat(actOrder, not(nullValue()));
        assertThat(actOrder.getContact(), is(CONTACT));
    }

    @Test
    public void testUpdateOrder() throws DaoException {
        Order order = createUser1_2ProductUnpayOrder1();
        order.setState(99);
        orderDao.store(order);
        Order actOrder = orderDao.findById(Order.class, order.getId());
        assertThat(actOrder.getState(), equalTo(99));
    }
    
}