package polarbear.integration.service.order;

import static com.polarbear.util.Constants.ORDER_STATE.UNPAY;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectCreateOrder;
import static polarbear.integration.service.order.factory.OrderParamFactory.createUser1ImmedidateBuyProduct1OrderParam;
import static polarbear.integration.service.order.factory.OrderParamFactory.createUser2ShopcartProduct1And3OrderParam;
import static polarbear.testdata.acceptance.testdata.ShopcartAcceptanceTestDataFactory.createShopcart2;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.util.factory.CurrentThreadUserFactory;

public class CreateOrderServiceTest extends AbstractOrderServiceTest {
    @Autowired
    BaseDao<Shopcart> shopcartDao;
    @Autowired
    BaseDao<ShopcartDetail> shopcartDetailDao;

    @Before
    public void init() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldCreateOrderSuccessWhenImmedidateBuyProduct1() throws DaoException, ValidateException, OrderStateException {
        Order newOrder = orderSvc.createOrder(createUser1ImmedidateBuyProduct1OrderParam().build());
        Order actOrder = orderSvc.getMyOrderDetail(newOrder.getId());
        assertThatOrder(actOrder, expectCreateOrder(createUser1ImmedidateBuyProduct1OrderParam(), UNPAY));
        assertRelateOrder(actOrder.getId(), createUser1ImmedidateBuyProduct1OrderParam().getBuyProducts(), UNPAY);
        assertThatProductNum(createUser1ImmedidateBuyProduct1OrderParam().getBuyProducts(), DECREASE);
    }

    @Test
    public void shouldCreateOrderSuccessWhenShopcartBuyProduct1And3() throws DaoException, ValidateException, OrderStateException {
        CurrentThreadUserFactory.setUser(createUser2());
        Order newOrder = orderSvc.createOrder(createUser2ShopcartProduct1And3OrderParam().build());
        Order actOrder = orderSvc.getMyOrderDetail(newOrder.getId());
        assertThatOrder(actOrder, expectCreateOrder(createUser2ShopcartProduct1And3OrderParam(), UNPAY));
        assertRelateOrder(actOrder.getId(), createUser2ShopcartProduct1And3OrderParam().getBuyProducts(), UNPAY);
        assertThatProductNum(createUser2ShopcartProduct1And3OrderParam().getBuyProducts(), DECREASE);
        assertThatShopcartAndDetail();
    }

    private void assertThatShopcartAndDetail() throws DaoException {
        Shopcart shopcart = shopcartDao.findById(Shopcart.class, createShopcart2().getId());
        assertThat("购物车商品总数", shopcart.getProductNum(), equalTo(0));
        List<ShopcartDetail> shopcartDetails = shopcartDetailDao.findByNamedQuery("queryByShopcart", createShopcart2());
        assertThat("购物车详情商品详情列表", shopcartDetails.size(), equalTo(0));
    }

    @After
    public void cleanUp() throws DataAccessException, Exception {
        super.cleanUp();
    }

}