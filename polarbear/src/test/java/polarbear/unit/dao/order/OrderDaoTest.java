package polarbear.unit.dao.order;

import static com.polarbear.util.Constants.ORDER_STATE.UNPAY;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;
import static task.CreateAcceptanceTestDataTask.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Logistic;
import com.polarbear.domain.Order;
import com.polarbear.util.DateUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
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
        final Logistic LOGISTIC = null;
        final int STATE = UNPAY.value();
        final int CURTIME = DateUtil.getCurrentSeconds();
        orderDao.store(new Order(createUser1(), PRODUCT_TOTAL_NUMS, PRODUCT_TOTAL_PRICE, CONTACT, LOGISTIC_PRICE, LOGISTIC, STATE, CURTIME, CURTIME));
    }

}