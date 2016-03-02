package polarbear.unit.dao.order;

import static com.polarbear.util.Constants.PAY_CODE.WEI_XIN;
import static com.polarbear.util.Constants.PAY_CODE.ZHI_FU_BAO;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static polarbear.testdata.DomainEntityConvertSqlUtil.createInsertSql;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.createUser1_2ProductUnpayOrder1;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Order;
import com.polarbear.domain.ToPayLog;
import com.polarbear.service.PageList;
import com.polarbear.util.date.DateUtil;

@ContextConfiguration(locations = { "/spring/spring-dao.xml" })
public class PayLogDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    BaseDao<ToPayLog> toPayLogDao;

    @Before
    public void setUp() throws DataAccessException, SQLException {
        Order order = createUser1_2ProductUnpayOrder1();
        int createTime = DateUtil.getCurrentSeconds();
        ToPayLog zhifubao_to_pay_log = new ToPayLog(1, ZHI_FU_BAO.value(), createTime, order);
        ToPayLog weixin_to_pay_log = new ToPayLog(2, WEI_XIN.value(), createTime, order);
        jdbcTemplate.update(createInsertSql(createUser1()));
        jdbcTemplate.update(createInsertSql(order));
        jdbcTemplate.update(createInsertSql(zhifubao_to_pay_log));
        jdbcTemplate.update(createInsertSql(weixin_to_pay_log));
    }

    @Test
    public void shouldReturnLastOnePayLog() throws DaoException {
        Order order = createUser1_2ProductUnpayOrder1();
        PageList<ToPayLog> lst = toPayLogDao.findByNamedQueryByPage("queryLastOneLogByOrder", 1, 1, order);
        assertThat("最后一条支付日志：", lst.getList().size(), equalTo(1));
        assertThat("最后一条支付日志支付平台号：", lst.getList().get(0).getPayPlatform(), equalTo(WEI_XIN.value()));
    }
}