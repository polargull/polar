package polarbear.integration.service.order;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectCancleOrder;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.BUY_PRODUCTS;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;
import static com.polarbear.util.Constants.ORDER_STATE.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import task.CreateAcceptanceTestDataTask;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.OrderList;
import com.polarbear.domain.OrderListLog;
import com.polarbear.domain.OrderLog;
import com.polarbear.domain.product.Product;
import com.polarbear.service.balance.to.BuyProduct;
import com.polarbear.service.order.OrderService;
import com.polarbear.service.order.OrderStateException;
import com.polarbear.util.Constants.ORDER_STATE;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@ContextConfiguration(locations = { "/spring/spring-service.xml", "/spring/spring-dao.xml" })
public class AbstractOrderServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    public final static int INCREASE = 1;
    public final static int DECREASE = -1;

    @Autowired
    protected OrderService orderSvc;

    protected void setUp() throws DataAccessException, Exception {
        CurrentThreadUserFactory.setUser(createUser1());
        jdbcTemplate.batchUpdate(CreateAcceptanceTestDataTask.createAllTestDataScriptArray());
    }

    protected void cleanUp() throws DataAccessException, Exception {
        CurrentThreadUserFactory.remove();
    }

    protected void assertThatProductNum(BuyProduct[] buyProducts, int increaseOrDecrease) {
        for (BuyProduct buyProduct : buyProducts) {
            Product actProduct = jdbcTemplate.queryForObject("select * from Product where id = ? ", new Object[] { buyProduct.getPid() }, BeanPropertyRowMapper
                    .newInstance(Product.class));
            Product orignProduct = buyProduct.getProduct();
            assertThat("商品的总数", actProduct.getNum(), equalTo(orignProduct.getNum() + (buyProduct.getBuyNum() * increaseOrDecrease)));
        }
    }

    protected void assertThatOrderList(List<OrderList> actOrderList, BuyProduct[] buyProducts, ORDER_STATE state) {
        assertThat("订单列表商品种类数量", actOrderList.size(), equalTo(buyProducts.length));
        for (int i = 0; i < actOrderList.size(); i++) {
            assertThat("订单列表商品id", actOrderList.get(i).getProductId(), equalTo(buyProducts[i].getPid()));
            assertThat("订单列表商品名", actOrderList.get(i).getProductName(), equalTo(buyProducts[i].getProductName()));
            assertThat("订单列表商品图片", actOrderList.get(i).getProductImg(), equalTo(buyProducts[i].getProductImg()));
            assertThat("订单列表商品数量", actOrderList.get(i).getProductNums(), equalTo(buyProducts[i].getBuyNum()));
            assertThat("订单列表商品真实价格", actOrderList.get(i).getProductPrice(), equalTo(buyProducts[i].getProductRealPrice()));
            assertThat("订单列表创建时间", actOrderList.get(i).getCreateTime(), not(nullValue()));
            assertThat("订单列表更新时间", actOrderList.get(i).getUpdateTime(), not(nullValue()));
            assertThat("订单列表状态", actOrderList.get(i).getState(), equalTo(state.value()));
            assertThat("订单列表物流", actOrderList.get(i).getLogistic(), nullValue());
        }
    }

    protected void assertThatOrderLog(Order act, ORDER_STATE state) {
        List<OrderLog> actOrderLog = jdbcTemplate.query("select * from Orderlog where order_id = ? ", new Object[] { act.getId() }, BeanPropertyRowMapper
                .newInstance(OrderLog.class));
        int lastIndex = actOrderLog.size() - 1;
        assertThat("订单操作日志", actOrderLog.get(lastIndex).getOp(), equalTo(state.op()));
        assertThat("订单日志状态", actOrderLog.get(lastIndex).getState(), equalTo(state.value()));
        assertThat("订单日志创建时间", actOrderLog.get(lastIndex).getCreateTime(), not(nullValue()));
    }

    protected void assertThatOrderListLog(List<OrderList> actOrderList, ORDER_STATE state) {
        for (OrderList orderList : actOrderList) {
            List<OrderListLog> listLogList = jdbcTemplate.query("select * from Orderlistlog where orderlist_id = ? ", new Object[] { orderList.getId() }, BeanPropertyRowMapper
                    .newInstance(OrderListLog.class));
            assertThat("订单列表操作日志", listLogList.get(0).getOp(), equalTo(state.op()));
            assertThat("订单列表日志状态", listLogList.get(0).getState(), equalTo(state.value()));
            assertThat("订单列表创建时间", listLogList.get(0).getCreateTime(), not(nullValue()));
        }
    }

    protected void assertRelateOrder(long distOrderId, BuyProduct[] buyProducts ,ORDER_STATE distOrderState) throws DaoException, OrderStateException {
        Order actOrder = orderSvc.getMyOrderDetail(distOrderId);
        assertThatOrderLog(actOrder, distOrderState);
        List<OrderList> actOrderList = jdbcTemplate.query("select * from Orderlist where order_id = ? ", new Object[] { distOrderId }, BeanPropertyRowMapper
                .newInstance(OrderList.class));
        assertThatOrderList(actOrderList, buyProducts, distOrderState);
        assertThatOrderListLog(actOrderList, distOrderState);
    }
    
}