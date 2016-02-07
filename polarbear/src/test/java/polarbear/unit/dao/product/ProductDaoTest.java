package polarbear.unit.dao.product;

import static com.polarbear.util.Constants.PRODUCT_STATE.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static polarbear.test.util.Constants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Category;
import com.polarbear.domain.Product;
import com.polarbear.service.PageList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ProductDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private BaseDao<Product> productDao;

    @Before
    public void setUp() {
        jdbcTemplate.update("insert into `category`(`id`,`cg_desc`) values(?, ?)", new Object[] { SHOPCARD_ID, CATEGORY_NAME });
        jdbcTemplate.update("insert into `product_style`(`id`,`styleProperties`) values(?, ?)", new Object[] { PRODUCT_STYLE_ID, PRODUCT_STYLE });
        jdbcTemplate.update("insert into `product`(`id`,`name`,`state`,`category_id`) values(?, ?, ?, ?)", new Object[] { 1l, PRODUCT_NAME + 1, PUT_ON.value(), SHOPCARD_ID });
        jdbcTemplate.update("insert into `product`(`id`,`name`,`state`,`category_id`) values(?, ?, ?, ?)", new Object[] { 2l, PRODUCT_NAME + 2, PUT_ON.value(), SHOPCARD_ID });
        jdbcTemplate.update("insert into `product`(`id`,`name`,`state`,`category_id`) values(?, ?, ?, ?)", new Object[] { 3l, PRODUCT_NAME + 3, PULL_OFF.value(), SHOPCARD_ID });
        jdbcTemplate.update("insert into `product`(`id`,`name`,`state`,`productStyle_id`) values(?, ?, ?, ?)", new Object[] { 4l, PRODUCT_NAME + 4, PULL_OFF.value(), PRODUCT_STYLE_ID });
    }

    @Test
    public void shouldHaveProductWhenQueryProductByIdAndState() {
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
        ids.add(1l);
        ids.add(2l);
        ids.add(3l);
        Map<String, List> param = new HashMap<String, List>() {
            {
                put("ids", ids);
            }
        };
        List<Product> productList = productDao.findByNamedQuery("queryPutOnProductByIds", param);
        assertThat(productList.size(), equalTo(2));
        assertThat(productList.get(0).getId(), equalTo(1l));
        assertThat(productList.get(1).getId(), equalTo(2l));
    }

    @Test
    public void queryProductByCategory() throws DaoException {
        PageList<Product> productList = productDao.findByNamedQueryByPage("queryPutOnProductByCategoryId", 1, 10, new Category(1L));
        assertThat(productList.getList().size(), equalTo(2));
        assertThat(productList.getList().get(0).getName(), equalTo(PRODUCT_NAME + 1));
        assertThat(productList.getList().get(1).getName(), equalTo(PRODUCT_NAME + 2));
        assertThat(productList.getTotal(), equalTo(2l));
        assertThat(productList.getPageNo(), equalTo(1));
    }

    @Test
    public void queryProductManagerListByProductName() throws DaoException {
        String hqlCondition = "name like '%" + PRODUCT_NAME + "%'";
        PageList<Product> pageList = productDao.findPageListByDynamicCondition(Product.class, 1, 10, hqlCondition);
        assertThat(pageList.getList().size(), equalTo(4));
        assertThat(pageList.getList().get(0).getName(), equalTo(PRODUCT_NAME + 4));
        assertThat(pageList.getList().get(1).getName(), equalTo(PRODUCT_NAME + 3));
        assertThat(pageList.getList().get(2).getName(), equalTo(PRODUCT_NAME + 2));
        assertThat(pageList.getList().get(3).getName(), equalTo(PRODUCT_NAME + 1));
        assertThat(pageList.getTotal(), equalTo(4l));
        assertThat(pageList.getPageNo(), equalTo(1));
    }
    
    @Test
    public void queryProductManagerListByStyle() throws DaoException {
        String hqlCondition = "productStyle != null";
        PageList<Product> pageList = productDao.findPageListByDynamicCondition(Product.class, 1, 10, hqlCondition);
        assertThat(pageList.getList().size(), equalTo(1));
        assertThat(pageList.getList().get(0).getName(), equalTo(PRODUCT_NAME + 4));
        assertThat(pageList.getTotal(), equalTo(1l));
        assertThat(pageList.getPageNo(), equalTo(1));
        hqlCondition = "productStyle = null";
        pageList = productDao.findPageListByDynamicCondition(Product.class, 1, 10, hqlCondition);
        assertThat(pageList.getList().size(), equalTo(3));
        assertThat(pageList.getList().get(0).getName(), equalTo(PRODUCT_NAME + 3));
        assertThat(pageList.getList().get(1).getName(), equalTo(PRODUCT_NAME + 2));
        assertThat(pageList.getList().get(2).getName(), equalTo(PRODUCT_NAME + 1));
        assertThat(pageList.getTotal(), equalTo(3l));
        assertThat(pageList.getPageNo(), equalTo(1));;
    }
}
