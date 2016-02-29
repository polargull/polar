package polarbear.unit.dao.product;

import static com.polarbear.util.Constants.PRODUCT_STATE.PUT_ON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static polarbear.test.util.Constants.PRODUCT_1_ID;
import static polarbear.test.util.Constants.PRODUCT_NAME;
import static polarbear.testdata.acceptance.testdata.CategoryAcceptanceTestDataFactory.createCategory3;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createMutiplyStyle1Product4;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createMutiplyStyle1Product5;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct1;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct2;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createPullOffProduct6;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createSalePrice6Product3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import task.CreateAcceptanceTestDataTask;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.product.Product;
import com.polarbear.service.PageList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ProductDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private BaseDao<Product> productDao;
    Product p1, p2, salePrice6p3, mutiplyStyle1p4, mutiplyStyle1p5, pullOffp6;
    @Before
    public void setUp() throws Exception {
        p1 = createProduct1();
        p2 = createProduct2();
        salePrice6p3 = createSalePrice6Product3();
        mutiplyStyle1p4 = createMutiplyStyle1Product4();
        mutiplyStyle1p5 = createMutiplyStyle1Product5();
        pullOffp6 = createPullOffProduct6();
        jdbcTemplate.batchUpdate(CreateAcceptanceTestDataTask.createAllTestDataScriptArray());
    }

    @Test
    public void shouldHaveProductWhenQueryProductById1AndStatePutOn() {
        try {
            Product p = productDao.findByNamedQueryObject("queryProductByIdAndState", PRODUCT_1_ID, PUT_ON.value());
            assertThat(p, notNullValue());
            assertThat(p.getId(), is(PRODUCT_1_ID));
            assertThat(p.getState(), is(PUT_ON.value()));
        } catch (DaoException e) {
            fail("挑选商品dao操作失败了,msg:" + e.getMessage());
        }
    }

    @SuppressWarnings( { "serial", "unchecked" })
    @Test
    public void queryProductByIds() throws DaoException {
        final List<Long> ids = new ArrayList<Long>();
        ids.add(p1.getId());
        ids.add(p2.getId());
        ids.add(pullOffp6.getId());
        Map<String, List> param = new HashMap<String, List>() {
            {
                put("ids", ids);
            }
        };
        List<Product> productList = productDao.findByNamedQuery("queryPutOnProductByIds", param);
        assertThat(productList.size(), equalTo(2));
    }

    @Test
    public void queryProductByCategory() throws DaoException {
        final int CATEGORY_IS_3_PUT_ON_TOTAL_NUM = 5;
        PageList<Product> productList = productDao.findByNamedQueryByPage("queryPutOnProductByCategoryId", 1, 10, createCategory3());
        assertThat(productList.getList().size(), equalTo(CATEGORY_IS_3_PUT_ON_TOTAL_NUM));        
    }

    @Test
    public void queryProductManagerListByProductName() throws DaoException {
        final int TOTAL_PRODUCT_NUM = 6;
        String hqlCondition = "name like '%" + PRODUCT_NAME + "%'";
        PageList<Product> pageList = productDao.findPageListByDynamicCondition(Product.class, 1, 10, hqlCondition);
        assertThat(pageList.getList().size(), equalTo(TOTAL_PRODUCT_NUM));
    }
    
    @Test
    public void queryProductManagerListByStyle() throws DaoException {
        final int HAVE_STYLE_PRODUCT_NUM = 2;
        final int NOT_HAVE_STYLE_PRODUCT_NUM = 4;
        String hqlCondition = "productStyle != null";
        PageList<Product> pageList = productDao.findPageListByDynamicCondition(Product.class, 1, 10, hqlCondition);
        assertThat(pageList.getList().size(), equalTo(HAVE_STYLE_PRODUCT_NUM));        
        hqlCondition = "productStyle = null";
        pageList = productDao.findPageListByDynamicCondition(Product.class, 1, 10, hqlCondition);
        assertThat(pageList.getList().size(), equalTo(NOT_HAVE_STYLE_PRODUCT_NUM));
    }

    @Test
    public void decreaseProductNum() throws DaoException {
        final int DECREASE_PRODUCT_NUM = -2;
        modifyProductNum(DECREASE_PRODUCT_NUM);        
    }

    @Test
    public void increaseProductNum() throws DaoException {
        final int INCREASE_PRODUCT_NUM = 2;
        modifyProductNum(INCREASE_PRODUCT_NUM);       
    }

    private void modifyProductNum(int modifyNum) throws DaoException {
        Product queryRes1P1 = jdbcTemplate.queryForObject("select * from product where id = ? ", new Object[] { p1.getId() }, BeanPropertyRowMapper.newInstance(Product.class));
        assertThat(queryRes1P1.getNum(), equalTo(p1.getNum()));

        productDao.executeUpdate("modifyProductNum", modifyNum, p1.getId());
        Product queryRes2P1 = jdbcTemplate.queryForObject("select * from product where id = ? ", new Object[] { p1.getId() }, BeanPropertyRowMapper.newInstance(Product.class));
        assertThat(queryRes2P1.getNum(), equalTo(p1.getNum() + modifyNum));
    }
    
}