package polarbear.unit.dao.shopcart;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static polarbear.test.util.Constants.*;
import static polarbear.testdata.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.shopcart.ShopcartLogBuilder.anShopcartLog;
import static polarbear.testdata.product.ProductBuilder.anProduct;
import static polarbear.testdata.user.UserBuilder.anUser;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartLog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ShopcartDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private BaseDao<Shopcart> shopcartDao;
    @Autowired
    private BaseDao<ShopcartLog> shopcartLogDao;
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void shouldHaveShopcartWhenAddMyShopcart() throws DaoException {
        try {            
            shopcartDao.store(anShopcart().withPrice(PRODUCT_PRICE).withProductNum(PRODUCT_NUM).withUser(anUser().withID(USER_ID)).build());
        } catch (DaoException e) {
            fail("注册dao操作失败了,msg:" + e.getMessage());
        }        
        jdbcTemplate.query("select price, productNum, createTime from shopcart where user_id = ?",new Object[] {USER_ID},
               new RowCallbackHandler(){
                      public void processRow(ResultSet rs) throws SQLException{
                            assertThat(rs.getDouble(1), equalTo(PRODUCT_PRICE));
                            assertThat(rs.getInt(2), equalTo(PRODUCT_NUM));        
                            assertThat(rs.getInt(3), notNullValue());
                      }
              }
        );          
    }
    
    @Test
    public void shouldHaveOneShopcartLogWhenAddMyShopcart() throws DaoException {
        try {            
            shopcartLogDao.store(anShopcartLog()
                                    .withProduct(anProduct().withID(PRODUCT_ID))
                                    .withShopcart(anShopcart().withId(SHOPCARD_ID))
                                    .withCount(PRODUCT_NUM)
                                    .build());
        } catch (DaoException e) {
            fail("注册dao操作失败了,msg:" + e.getMessage());
        }        
        jdbcTemplate.query("select count, product_id, createTime, shopCart_id from shopcart_log where shopcart_id = ?",new Object[] {USER_ID},
               new RowCallbackHandler(){
                      public void processRow(ResultSet rs) throws SQLException{
                            assertThat(rs.getInt(1), equalTo(PRODUCT_NUM));
                            assertThat(rs.getLong(2), equalTo(PRODUCT_ID));        
                            assertThat(rs.getInt(3), notNullValue());
                            assertThat(rs.getLong(4), equalTo(SHOPCARD_ID));
                      }
              }
        );          
    }
}