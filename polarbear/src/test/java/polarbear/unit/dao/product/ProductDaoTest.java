package polarbear.unit.dao.product;

import static com.polarbear.util.Constants.PRODUCT_STATE.PUT_ON;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static polarbear.test.util.Constants.PRODUCT_1_ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ProductDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private BaseDao<Product> productDao;

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
    
    @Test
    public void queryProductByIds() throws DaoException {
        final List<Long> ids = new ArrayList<Long>();
        ids.add(1l);
        ids.add(2l);
        Map<String, List> param = new HashMap<String, List>() {
            {
                put("ids", ids);
            }
        };
        List<Product> productList = productDao.findByNamedQuery("queryPutOnProductByIds", param);
        assertThat(productList.size(),equalTo(2));
    }
    
    @Test
    public void queryProductByCategory() throws DaoException {
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.update("delete from product");
        jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
        jdbcTemplate.update("insert into `product`(`num`,`createTime`,`name`,`price`,`state`,`category_id`) VALUES (6, unix_timestamp(), '羽绒服1', 150, 1, 1)");
        jdbcTemplate.update("insert into `product`(`num`,`createTime`,`name`,`price`,`state`,`category_id`) VALUES (6, unix_timestamp()+1, '羽绒服2', 150, 1, 1)");
        List<Product> productList = productDao.findByNamedQueryByPage("queryPutOnProductByCategoryId", new Object[] { new Category(1L) }, "1", null);
        assertThat(productList.size(),equalTo(2));
        assertThat(productList.get(0).getName(),equalTo("羽绒服2"));
        assertThat(productList.get(1).getName(),equalTo("羽绒服1"));
    }

}