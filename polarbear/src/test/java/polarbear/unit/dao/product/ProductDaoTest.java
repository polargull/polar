package polarbear.unit.dao.product;

import static com.polarbear.util.Constants.PRODUCT_STATE.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static polarbear.test.util.Constants.*;
import static polarbear.testdata.DomainEntityConvertSqlUtil.createInsertSql;
import static polarbear.testdata.builder.product.ProductBuilder.anProduct;
import static polarbear.testdata.builder.product.StyleBuilder.anStyle;
import static polarbear.testdata.DomainEntityConvertSqlUtil.createInsertSql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import polarbear.testdata.builder.product.StyleBuilder;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Category;
import com.polarbear.domain.product.Product;
import com.polarbear.service.PageList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ProductDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private BaseDao<Product> productDao;
    Product p1,p2,p3,p4;
    @Before
    public void setUp() throws DataAccessException, SQLException {
        Category category = new Category(SHOPCARD_ID, CATEGORY_NAME);
        StyleBuilder style = anStyle().withId(PRODUCT_STYLE_ID).withProperty(PRODUCT_STYLE);
        p1 = anProduct().withID(1l).withName(PRODUCT_NAME + 1).putOn().withPrice(60d).saleDay(1).salePrice(50d).withCategory(category).build();
        p2 = anProduct().withID(2l).withName(PRODUCT_NAME + 2).putOn().withCategory(category).build();
        p3 = anProduct().withID(3l).withName(PRODUCT_NAME + 3).pullOff().withPrice(66d).saleDay(1).salePrice(55d).withCategory(category).build();
        p4 = anProduct().withID(4l).withName(PRODUCT_NAME + 4).pullOff().withStyle(style).build();
        jdbcTemplate.update(createInsertSql(category));
        jdbcTemplate.update(createInsertSql(style.build()));
        jdbcTemplate.update(createInsertSql(p1));
        jdbcTemplate.update(createInsertSql(p2));
        jdbcTemplate.update(createInsertSql(p3));
        jdbcTemplate.update(createInsertSql(p4));
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
        assertThat(productList.getList().get(0).getName(), equalTo(p1.getName()));
        assertThat(productList.getList().get(1).getName(), equalTo(p2.getName()));
        assertThat(productList.getTotal(), equalTo(2l));
        assertThat(productList.getPageNo(), equalTo(1));
    }

    @Test
    public void queryProductManagerListByProductName() throws DaoException {
        String hqlCondition = "name like '%" + PRODUCT_NAME + "%'";
        PageList<Product> pageList = productDao.findPageListByDynamicCondition(Product.class, 1, 10, hqlCondition);
        assertThat(pageList.getList().size(), equalTo(4));
        assertThat(pageList.getList().get(0).getName(), equalTo(p4.getName()));
        assertThat(pageList.getList().get(1).getName(), equalTo(p3.getName()));
        assertThat(pageList.getList().get(2).getName(), equalTo(p2.getName()));
        assertThat(pageList.getList().get(3).getName(), equalTo(p1.getName()));
        assertThat(pageList.getTotal(), equalTo(4l));
        assertThat(pageList.getPageNo(), equalTo(1));
    }
    
    @Test
    public void queryProductManagerListByStyle() throws DaoException {
        String hqlCondition = "productStyle != null";
        PageList<Product> pageList = productDao.findPageListByDynamicCondition(Product.class, 1, 10, hqlCondition);
        assertThat(pageList.getList().size(), equalTo(1));
        assertThat(pageList.getList().get(0).getName(), equalTo(p4.getName()));
        assertThat(pageList.getTotal(), equalTo(1l));
        assertThat(pageList.getPageNo(), equalTo(1));
        hqlCondition = "productStyle = null";
        pageList = productDao.findPageListByDynamicCondition(Product.class, 1, 10, hqlCondition);
        assertThat(pageList.getList().size(), equalTo(3));
        assertThat(pageList.getList().get(0).getName(), equalTo(p3.getName()));
        assertThat(pageList.getList().get(1).getName(), equalTo(p2.getName()));
        assertThat(pageList.getList().get(2).getName(), equalTo(p1.getName()));
        assertThat(pageList.getTotal(), equalTo(3l));
        assertThat(pageList.getPageNo(), equalTo(1));;
    }
    
    @Test
    public void decreaseProductNum() throws DaoException {
        Product queryRes1P1 = jdbcTemplate.queryForObject("select * from product where id = ? ", new Object[] { p1.getId() }, BeanPropertyRowMapper.newInstance(Product.class));
        assertThat(queryRes1P1.getNum(), equalTo(p1.getNum()));
        
        productDao.executeUpdate("decreaseProductNum", p1.getId());
        Product queryRes2P1 = jdbcTemplate.queryForObject("select * from product where id = ? ", new Object[] { p1.getId() }, BeanPropertyRowMapper.newInstance(Product.class));
        assertThat(queryRes2P1.getNum(), equalTo(p1.getNum() - 1));
    }
}
