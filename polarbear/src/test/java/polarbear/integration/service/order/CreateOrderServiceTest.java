package polarbear.integration.service.order;

import static com.polarbear.util.Constants.ORDER_STATE.UNPAY;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;
import static polarbear.integration.service.order.assertutil.AssertUtil.*;
import static polarbear.integration.service.order.factory.OrderFactory.*;
import static polarbear.integration.service.order.factory.OrderParamFactory.createUser1ImmedidateBuyProduct1OrderParam;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import polarbear.testdata.builder.order.OrderParamBuilder;
import task.CreateAcceptanceTestDataTask;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.OrderList;
import com.polarbear.domain.OrderListLog;
import com.polarbear.domain.OrderLog;
import com.polarbear.domain.product.Product;
import com.polarbear.service.balance.to.BuyProduct;
import com.polarbear.service.order.OrderService;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-service.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class CreateOrderServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private OrderService orderSvc;

    @Before
    public void init() throws Exception {
        CurrentThreadUserFactory.setUser(createUser1());
        jdbcTemplate.batchUpdate(CreateAcceptanceTestDataTask.createAllTestDataScriptArray());
    }

    @Test
    public void shouldCreateOrderSuccessWhenImmedidateBuyProduct1() throws DaoException, ValidateException {
        Order testOrder = orderSvc.createOrder(createUser1ImmedidateBuyProduct1OrderParam().build());
        Order actOrder = jdbcTemplate.queryForObject("select * from Orders where id = ? ", new Object[] { testOrder.getId() }, BeanPropertyRowMapper.newInstance(Order.class));
        List<OrderList> actOrderList = jdbcTemplate.query("select * from Orderlist where order_id = ? ", new Object[] { testOrder.getId() }, BeanPropertyRowMapper
                .newInstance(OrderList.class));
        assertThatNewOrder(actOrder, expectOrder(createUser1ImmedidateBuyProduct1OrderParam()));
        assertThatNewOrderLog(actOrder);
        assertThatNewOrderList(actOrderList, createUser1ImmedidateBuyProduct1OrderParam());
        assertThatNewOrderListLog(actOrderList);
        assertThatDecreaseProduct(createUser1ImmedidateBuyProduct1OrderParam());
    }

    private void assertThatDecreaseProduct(OrderParamBuilder orderParamBuilder) {
        BuyProduct[] buyProducts = orderParamBuilder.getBuyProducts();
        for (BuyProduct buyProduct : buyProducts) {
            Product actProduct = jdbcTemplate.queryForObject("select * from Product where id = ? ", new Object[] { buyProduct.getPid() }, BeanPropertyRowMapper
                    .newInstance(Product.class));
            Product orignProduct = buyProduct.getProduct();
            assertThat("商品减少后的总数", actProduct.getNum(), equalTo(orignProduct.getNum() - buyProduct.getBuyNum()));
        }
    }

    private void assertThatNewOrderLog(Order act) {
        List<OrderLog> actOrderLog = jdbcTemplate.query("select * from Orderlog where order_id = ? ", new Object[] { act.getId() }, BeanPropertyRowMapper
                .newInstance(OrderLog.class));
        assertThat("订单操作日志", actOrderLog.get(0).getOp(), equalTo(UNPAY.op()));
        assertThat("订单日志状态", actOrderLog.get(0).getState(), equalTo(UNPAY.value()));
        assertThat("订单日志创建时间", actOrderLog.get(0).getCreateTime(), not(nullValue()));
    }

    private void assertThatNewOrderList(List<OrderList> actOrderList, OrderParamBuilder paramBuilder) {
        BuyProduct[] buyProducts = paramBuilder.getBuyProducts();
        assertThat("订单列表数量", actOrderList.size(), equalTo(buyProducts.length));
        for (int i = 0; i < actOrderList.size(); i++) {
            assertThat("订单列表商品id", actOrderList.get(i).getProductId(), equalTo(buyProducts[i].getPid()));
            assertThat("订单列表商品名", actOrderList.get(i).getProductName(), equalTo(buyProducts[i].getProductName()));
            assertThat("订单列表商品图片", actOrderList.get(i).getProductImg(), equalTo(buyProducts[i].getProductImg()));
            assertThat("订单列表商品数量", actOrderList.get(i).getProductNums(), equalTo(buyProducts[i].getBuyNum()));
            assertThat("订单列表商品真实价格", actOrderList.get(i).getProductPrice(), equalTo(buyProducts[i].getProductRealPrice()));
            assertThat("订单列表创建时间", actOrderList.get(i).getCreateTime(), not(nullValue()));
            assertThat("订单列表更新时间", actOrderList.get(i).getUpdateTime(), not(nullValue()));
            assertThat("订单列表状态", actOrderList.get(i).getState(), equalTo(UNPAY.value()));
            assertThat("订单列表物流", actOrderList.get(i).getLogistic(), nullValue());
        }
    }

    private void assertThatNewOrderListLog(List<OrderList> actOrderList) {
        for (OrderList orderList : actOrderList) {
            List<OrderListLog> listLogList = getOrderListLogBylistId(orderList.getId());
            assertThat("订单列表操作日志", listLogList.get(0).getOp(), equalTo(UNPAY.op()));
            assertThat("订单列表日志状态", listLogList.get(0).getState(), equalTo(UNPAY.value()));
            assertThat("订单列表创建时间", listLogList.get(0).getCreateTime(), not(nullValue()));
        }
    }

    private List<OrderListLog> getOrderListLogBylistId(long id) {
        return jdbcTemplate.query("select * from Orderlistlog where orderlist_id = ? ", new Object[] { id }, BeanPropertyRowMapper.newInstance(OrderListLog.class));
    }

}