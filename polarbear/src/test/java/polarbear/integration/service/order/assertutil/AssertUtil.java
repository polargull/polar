package polarbear.integration.service.order.assertutil;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.polarbear.domain.Order;

public class AssertUtil {
    public static void assertThatNewOrder(Order act, Order expect) {
        assertThat("订单商品总数", act.getProductTotalNums(), equalTo(expect.getProductTotalNums()));
        assertThat("订单总价格", act.getProductTotalPrice(), equalTo(expect.getProductTotalPrice()));
        assertThat("订单联系方式", act.getContact(), equalTo(expect.getContact()));
        assertThat("订单状态", act.getState(), equalTo(expect.getState()));
        assertThat("订单物流数据", act.getLogistic(), is(expect.getLogistic()));
        assertThat("订单运费数据", act.getLogisticPrice(), equalTo(expect.getLogisticPrice()));
        assertThat("订单创建时间", act.getCreateTime(), not(nullValue()));
        assertThat("订单更新时间", act.getUpdateTime(), not(nullValue()));
    }
}