package polarbear.unit.dao.shopcart;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static polarbear.test.util.Constants.*;
import static polarbear.testdata.builder.product.ProductBuilder.anProduct;
import static polarbear.testdata.builder.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.builder.shopcart.ShopcartDetailBuilder.anShopcartDetail;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ShopcartDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private BaseDao<Shopcart> shopcartDao;
    @Autowired
    private BaseDao<ShopcartDetail> shopcartDetailDao;
    
    @Before
    public void setUp() throws DaoException {
        jdbcTemplate.update("insert into user(id, name, pwd, cellphone, createTime) values(?, ?, ?, ?, ?)", USER_ID, UNAME, MD5_PWD, CELLPHONE, CURRENT_TIME);
        jdbcTemplate.update("insert into product(id, name) values(?, ?)", PRODUCT_1_ID, PRODUCT_NAME);
        jdbcTemplate.update("insert into shopcart(id, user_id, productNum, createTime) values(?, ?, ?, ?)", SHOPCARD_ID, USER_ID, SHOPCART_ORIGIN_NUM, CURRENT_TIME);
        jdbcTemplate.update("insert into `shopcart_detail`(`id`,`product_id`,`num`,`shopcart_id`,`createTime`) values(?, ?, ?, ?, ?)", new Object[] { SHOPCART_DETAIL_ID, PRODUCT_1_ID, PRODUCT_BUY_NUM, SHOPCARD_ID, CURRENT_TIME });
    }
    
    @Test
    public void shouldAddShopcartDetailSucessWhenAddMyShopcart() throws DaoException {
        ShopcartDetail testShopcartDetail = anShopcartDetail().withNum(PRODUCT_BUY_NUM).withProduct(anProduct().withID(PRODUCT_1_ID)).withShopcart(anShopcart().withId(SHOPCARD_ID)).build();
        shopcartDetailDao.store(testShopcartDetail);
        jdbcTemplate.query("select num, product_id, createTime, shopCart_id from shopcart_detail where id = ?", new Object[] {testShopcartDetail.getId()},
               new RowCallbackHandler(){
                      public void processRow(ResultSet rs) throws SQLException{
                            assertThat(rs.getInt(1), equalTo(PRODUCT_BUY_NUM));
                            assertThat(rs.getLong(2), equalTo(PRODUCT_1_ID));
                            assertThat(rs.getInt(3), notNullValue());
                            assertThat(rs.getLong(4), equalTo(SHOPCARD_ID));
                      }
              }
        );          
    }
    
    @Test
    public void shouldQueryShopcartDetailSucessWhenAddMyShopcart() {        
        try {
            ShopcartDetail sd = shopcartDetailDao.findByNamedQueryObject("queryByShopcartAndProduct", anShopcart().withId(SHOPCARD_ID).build(),anProduct().withID(PRODUCT_1_ID).build());
            assertThat(sd, notNullValue());
            assertThat(sd.getNum(), equalTo(PRODUCT_BUY_NUM));
            assertThat(sd.getProduct().getId(), equalTo(PRODUCT_1_ID));
            assertThat(sd.getShopCart().getId(), equalTo(SHOPCARD_ID));
        } catch (DaoException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void shouldQuerySucessWhenInShopcartGetOrderDetail() {
        try {
            Shopcart shopcart = shopcartDao.findById(Shopcart.class, SHOPCARD_ID);
            List<ShopcartDetail> shopcartDetails = shopcartDetailDao.findByNamedQuery("queryByShopcart", shopcart);
            assertThat(shopcartDetails.size(), is(1));
        } catch (DaoException e) {
            fail(e.getMessage());
        }
    }
}