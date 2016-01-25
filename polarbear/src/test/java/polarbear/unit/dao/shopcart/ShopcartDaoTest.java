package polarbear.unit.dao.shopcart;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static polarbear.test.util.Constants.*;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.domain.User;
import com.polarbear.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ShopcartDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private BaseDao<Shopcart> shopcartDao;
    @Autowired
    private BaseDao<ShopcartDetail> shopcartDetailDao;
    @Autowired
    private BaseDao<User> userDao;
    @Autowired
    private BaseDao<Product> productDao;
    
    private User testUser;
    private Product testProduct;
    private Shopcart testShopcart;
    private ShopcartDetail testShopcartDetail;
    
    @Before
    public void setUp() throws DaoException {
        testUser = anUser().withUname(NEW_REGISTER).withPassword(MD5_PWD).withCellphone(NEW_CELLPHONE).withCreateTime(DateUtil.getCurrentSeconds()).build();
        userDao.store(testUser);
        testProduct = anProduct().withNum(PRODUCT_NUM).withPrice(PRODUCT_1_PRICE).build();
        productDao.store(testProduct);
        testShopcart = anShopcart().withProductNum(SHOPCART_ORIGIN_NUM).withUser(anUser().withID(testUser.getId())).build();
        shopcartDao.store(testShopcart);
        testShopcartDetail = anShopcartDetail().withNum(PRODUCT_BUY_NUM).withProduct(anProduct().withID(testProduct.getId())).withShopcart(anShopcart().withId(testShopcart.getId())).build();
        shopcartDetailDao.store(testShopcartDetail);
    }        
    
//    @Test
    public void shouldAddShopcartDetailSucessWhenAddMyShopcart() throws DaoException {        
        jdbcTemplate.query("select num, product_id, createTime, shopCart_id from shopcart_detail where id = ?", new Object[] {testShopcartDetail.getId()},
               new RowCallbackHandler(){
                      public void processRow(ResultSet rs) throws SQLException{
                            assertThat(rs.getInt(1), equalTo(PRODUCT_BUY_NUM));
                            assertThat(rs.getLong(2), equalTo(testProduct.getId()));
                            assertThat(rs.getInt(3), notNullValue());
                            assertThat(rs.getLong(4), equalTo(testShopcart.getId()));
                      }
              }
        );          
    }
    
//    @Test
    public void shouldQueryShopcartDetailSucessWhenAddMyShopcart() {        
        try {
            ShopcartDetail sd = shopcartDetailDao.findByNamedQueryObject("queryByShopcartAndProduct", anShopcart().withId(testShopcart.getId()).build(),anProduct().withID(testProduct.getId()).build());
            assertThat(sd, notNullValue());
            assertThat(sd.getNum(), equalTo(PRODUCT_BUY_NUM));
            assertThat(sd.getProduct().getId(), equalTo(testProduct.getId()));
            assertThat(sd.getShopCart().getId(), equalTo(testShopcart.getId()));
        } catch (DaoException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void shouldQuerySucessWhenInShopcartGetOrderDetail() {
        try {
            Shopcart shopcart = shopcartDao.findById(Shopcart.class, testShopcart.getId());
            List<ShopcartDetail> shopcartDetails = shopcartDetailDao.findByNamedQuery("queryByShopcart", shopcart);
            assertThat(shopcartDetails.size(), is(1));
        } catch (DaoException e) {
            fail(e.getMessage());
        }
    }
}