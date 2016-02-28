package polarbear.integration.service.order;

import static com.polarbear.util.Constants.ORDER_STATE.UNPAY;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.createUser12ProductUnpayOrder1;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import polarbear.testdata.builder.order.OrderParamBuilder;
import task.CreateAcceptanceTestDataTask;

import com.polarbear.dao.BaseDao;
import com.polarbear.domain.Order;
import com.polarbear.domain.OrderList;
import com.polarbear.domain.OrderListLog;
import com.polarbear.domain.OrderLog;
import com.polarbear.domain.product.Product;
import com.polarbear.service.balance.to.BuyProduct;
import com.polarbear.service.order.OrderService;
import com.polarbear.util.factory.CurrentThreadUserFactory;

@ContextConfiguration(locations = { "/spring/spring-service.xml", "/spring/spring-dao.xml" })
public class CancleOrderServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private OrderService orderSvc;
    @Autowired
    BaseDao<Order> orderDao;

    @Before
    public void init() throws Exception {
        CurrentThreadUserFactory.setUser(createUser1());
        jdbcTemplate.batchUpdate(CreateAcceptanceTestDataTask.createAllTestDataScriptArray());
    }

    @Test
    public void shouldSuccessWhenCancleOrder() throws DataAccessException, Exception {
        final String CANCLE_REASON = null;
        // Order testOrder =
        // orderSvc.createOrder(createUser1ImmedidateBuyProduct1OrderParam().build());
        // Order order =
         orderSvc.cancle(createUser12ProductUnpayOrder1().getId(), CANCLE_REASON);
//         Order order = orderDao.findById(Order.class, 1l);
//         order.setState(5);
//         orderDao.update(order);
//         jdbcTemplate.update("update orders o set o.state=5 where o.id=1");
        Order actOrder2 = jdbcTemplate.queryForObject("select * from Orders where id = ?", new Object[] { createUser12ProductUnpayOrder1().getId() }, BeanPropertyRowMapper.newInstance(Order.class));
        // for (Order o : actOrder) {
        // System.out.println(">>>>>>>>>>>>size: " + actOrder.size() +
        // ", state:" + o.getState());
        // }
        // System.out.println(">>>>>>>>>>>>state: " + actOrder.getState());
        // assertThat("totalnum:",actOrder2.getProductTotalNums(),
        // equalTo(order.getProductTotalNums()));
        assertThat("state:", actOrder2.getState(), equalTo(5));
        // List<OrderList> actOrderList =
        // jdbcTemplate.query("select * from Orderlist where order_id = ? ", new
        // Object[] { createUser12ProductUnpayOrder1().getId() },
        // BeanPropertyRowMapper.newInstance(OrderList.class));
        // assertThatNewOrder(actOrder, expectCancleOrder());
        // assertThatNewOrderLog(actOrder);
        // assertThatNewOrderList(actOrderList,
        // createUser1ImmedidateBuyProduct1OrderParam());
        // assertThatNewOrderListLog(actOrderList);
        // assertThatIncreaseProduct(createUser1ImmedidateBuyProduct1OrderParam());
    }

    @Test
    public void testOrderState() {
        List<Order> actOrder2 = jdbcTemplate.query("select * from Orders", BeanPropertyRowMapper.newInstance(Order.class));
        // for (Order o : actOrder) {
        // System.out.println(">>>>>>>>>>>>size: " + actOrder.size() +
        // ", state:" + o.getState());
        // }
        // System.out.println(">>>>>>>>>>>>state: " + actOrder.getState());
        assertThat("order size:", actOrder2.size(), equalTo(1));
        assertThat("order 1 state:", actOrder2.get(0).getState(), equalTo(1));
    }

    private void assertThatIncreaseProduct(OrderParamBuilder orderParamBuilder) {
        BuyProduct[] buyProducts = orderParamBuilder.getBuyProducts();
        for (BuyProduct buyProduct : buyProducts) {
            Product actProduct = jdbcTemplate.queryForObject("select * from Product where id = ? ", new Object[] { buyProduct.getPid() }, BeanPropertyRowMapper
                    .newInstance(Product.class));
            Product orignProduct = buyProduct.getProduct();
            assertThat("商品减少后的总数", actProduct.getNum(), equalTo(orignProduct.getNum() + buyProduct.getBuyNum()));
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

    @After
    public void cleanup() {
        CurrentThreadUserFactory.remove();
    }
}