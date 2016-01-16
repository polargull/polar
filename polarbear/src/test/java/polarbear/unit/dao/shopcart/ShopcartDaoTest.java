package polarbear.unit.dao.shopcart;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static polarbear.test.util.Constants.PRODUCT_ID;
import static polarbear.test.util.Constants.PRODUCT_NUM;
import static polarbear.test.util.Constants.PRODUCT_PRICE;
import static polarbear.test.util.Constants.SHOPCARD_ID;
import static polarbear.test.util.Constants.USER_ID;
import static polarbear.testdata.product.ProductBuilder.anProduct;
import static polarbear.testdata.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.shopcart.ShopcartDetailBuilder.anShopcartDetail;
import static polarbear.testdata.user.UserBuilder.anUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ShopcartDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private BaseDao<Shopcart> shopcartDao;
    @Autowired
    private BaseDao<ShopcartDetail> shopcartDetailDao;
    
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
    public void shouldAddShopcartDetailSucessWhenAddMyShopcart() throws DaoException {
        try {            
            shopcartDetailDao.store(anShopcartDetail()
                                    .withProduct(anProduct().withID(PRODUCT_ID))
                                    .withShopcart(anShopcart().withId(SHOPCARD_ID))
                                    .withNum(PRODUCT_NUM)
                                    .build());
        } catch (DaoException e) {
            fail("注册dao操作失败了,msg:" + e.getMessage());
        }        
        jdbcTemplate.query("select num, product_id, createTime, shopCart_id from shopcart_detail where id = ?",new Object[] {1l},
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
    
    @Test
    public void shouldQueryShopcartDetailSucessWhenAddMyShopcart() {
        jdbcTemplate.update("insert into shopcart_detail(num,shopcart_id,product_id,createtime) values(?,?,?,?)",PRODUCT_NUM,SHOPCARD_ID,PRODUCT_ID,DateUtil.getCurrentSeconds());
        ShopcartDetail sd;
        try {
            sd = shopcartDetailDao.findByNamedQueryObject("queryByShopcartAndProduct", anShopcart().withId(SHOPCARD_ID).build(),anProduct().withID(PRODUCT_ID).build());
            assertThat(sd, notNullValue());
            assertThat(sd.getNum(), equalTo(PRODUCT_NUM));
            assertThat(sd.getProduct().getId(), equalTo(PRODUCT_ID));
            assertThat(sd.getShopCart().getId(), equalTo(SHOPCARD_ID));
        } catch (DaoException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void shouldQuerySucessWhenInShopcartGetOrderDetail() {
        jdbcTemplate.update("insert into shopcart_detail(num,shopcart_id,product_id,createtime) values(?,?,?,?)", PRODUCT_NUM, SHOPCARD_ID, PRODUCT_ID + 1, DateUtil
                .getCurrentSeconds());
        try {
            Shopcart shopcart = shopcartDao.findById(Shopcart.class, SHOPCARD_ID);
            List<ShopcartDetail> shopcartDetails = shopcart.getShopcartDetails();
            assertThat(shopcartDetails.size(), is(2));
        } catch (DaoException e) {
            fail(e.getMessage());
        }
    }
}