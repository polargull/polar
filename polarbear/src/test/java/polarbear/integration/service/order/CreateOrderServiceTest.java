package polarbear.integration.service.order;

import static com.polarbear.util.Constants.BUY_MODE.IMMEDIDATE;
import static com.polarbear.util.Constants.ORDER_STATE.UNPAY;
import static com.polarbear.util.Constants.PAY_CODE.HOU_DAO_FU_KUAN;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.testdata.acceptance.testdata.AddressAcceptanceTestDataFactory.createUser1Address1;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct1;
import static polarbear.testdata.builder.order.OrderParamBuilder.anOrderParam;

import java.math.BigDecimal;

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
import com.polarbear.domain.Address;
import com.polarbear.domain.Logistic;
import com.polarbear.domain.Order;
import com.polarbear.service.balance.to.BuyProduct;
import com.polarbear.service.order.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-service.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class CreateOrderServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private OrderService orderSvc;
    final BuyProduct[] BUY_SINGAL_PRODUCT = new BuyProduct[] { new BuyProduct(createProduct1(), 1) };

    @Before
    public void init() throws Exception {
        jdbcTemplate.batchUpdate(CreateAcceptanceTestDataTask.createAllTestDataScriptArray());
    }

    @Test
    public void shouldCreateOrderSuccessWhenImmedidateBuyProduct1() throws DaoException, ValidateException {
        Order testOrder = orderSvc.createOrder(createUser1ImmedidateBuyProduct1OrderParam().build());
        Order actOrder = jdbcTemplate.queryForObject("select * from Order where id = ? ", new Object[] { testOrder.getId() }, BeanPropertyRowMapper.newInstance(Order.class));
        assertThatOrder(actOrder, expectOrder(createUser1ImmedidateBuyProduct1OrderParam()));
    }

    private void assertThatOrder(Order act, Order expect) {
        assertThat("订单商品总数", act.getProductTotalNums(), equalTo(expect.getProductTotalNums()));
        assertThat("订单总价格", act.getProductTotalPrice(), equalTo(expect.getProductTotalPrice()));
        assertThat("订单联系方式", act.getContact(), equalTo(expect.getContact()));
        assertThat("订单状态", act.getState(), equalTo(expect.getState()));
        assertThat("订单物流数据", act.getLogistic(), is(expect.getLogistic()));
        assertThat("订单运费数据", act.getLogisticPrice(), equalTo(expect.getLogisticPrice()));
    }

    private Order expectOrder(OrderParamBuilder paramBuilder) {
        int productTotalNums = 0;
        double productTotalPrice = 0;
        final String CONTACT = getOrderContact(paramBuilder.getAddress());
        final int STATE = UNPAY.value();
        final Logistic LOGISTIC = null;
        for (BuyProduct buyProduct : paramBuilder.getBuyProducts()) {
            productTotalNums += buyProduct.getBuyNum();
            double productPrice = new BigDecimal(buyProduct.getProductRealPrice()).multiply(new BigDecimal(buyProduct.getBuyNum())).setScale(2, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
            productTotalPrice = new BigDecimal(productTotalPrice).add(new BigDecimal(productPrice)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        final double LOGISTIC_PRICE = productTotalPrice >= 49 ? 0 : 10;
        return new Order(productTotalNums, productTotalPrice, CONTACT, LOGISTIC_PRICE, LOGISTIC, STATE);
    }

    private String getOrderContact(Address address) {
        return address.getReceiverName() + "|" + address.getCellphone() + "|" + address.getPhone() + "|" + address.getDistrict() + "|" + address.getAddress();
    }

    private OrderParamBuilder createUser1ImmedidateBuyProduct1OrderParam() {
        return anOrderParam().withBuyProducts(BUY_SINGAL_PRODUCT).withAddress(createUser1Address1()).withBuyMode(IMMEDIDATE).withPayCode(HOU_DAO_FU_KUAN);
    }
}