package polarbear.integration.service.product;

import static com.polarbear.util.Constants.PRODUCT_STATE.PULL_OFF;
import static com.polarbear.util.Constants.PRODUCT_STATE.PUT_ON;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.CATEGORY_NAME;
import static polarbear.test.util.Constants.PRODUCT_NAME;
import static polarbear.test.util.Constants.PRODUCT_STYLE;
import static polarbear.test.util.Constants.PRODUCT_STYLE_ID;
import static polarbear.test.util.Constants.SHOPCARD_ID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.service.PageList;
import com.polarbear.service.product.ProductManagerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-service.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ProductManagerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private ProductManagerService productManagerSvc;

    @Before
    public void init() throws DaoException {
        jdbcTemplate.update("insert into `category`(`id`,`cg_desc`) values(?, ?)", new Object[] { SHOPCARD_ID, CATEGORY_NAME });
        jdbcTemplate.update("insert into `product_style`(`id`,`styleProperties`) values(?, ?)", new Object[] { PRODUCT_STYLE_ID, PRODUCT_STYLE });
        jdbcTemplate.update("insert into `product`(`id`,`name`,`state`,`category_id`) values(?, ?, ?, ?)", new Object[] { 1l, PRODUCT_NAME + 1, PUT_ON.value(), SHOPCARD_ID });
        jdbcTemplate.update("insert into `product`(`id`,`name`,`state`,`category_id`) values(?, ?, ?, ?)", new Object[] { 2l, PRODUCT_NAME + 2, PUT_ON.value(), SHOPCARD_ID });
        jdbcTemplate.update("insert into `product`(`id`,`name`,`state`,`category_id`) values(?, ?, ?, ?)", new Object[] { 3l, PRODUCT_NAME + 3, PULL_OFF.value(), SHOPCARD_ID });
        jdbcTemplate.update("insert into `product`(`id`,`name`,`state`,`productStyle_id`) values(?, ?, ?, ?)", new Object[] { 4l, PRODUCT_NAME + 4, PULL_OFF.value(),
                PRODUCT_STYLE_ID });
    }

    @Test
    public void testProductManagerQueryByName() throws DaoException {
        PageList<Product> pageLst1 = productManagerSvc.productList("name:羽绒服", 1, 10);
        assertThat(pageLst1.getTotal(), equalTo(0l));
        PageList<Product> pageLst2 = productManagerSvc.productList("name:" + PRODUCT_NAME, 1, 10);
        assertThat(pageLst2.getTotal(), equalTo(4l));
        PageList<Product> pageLst3 = productManagerSvc.productList("name:" + PRODUCT_NAME + 1, 1, 10);
        assertThat(pageLst3.getTotal(), equalTo(1l));
    }

    @Test
    public void testProductManagerQueryByStyle() throws DaoException {
        PageList<Product> pageLst1 = productManagerSvc.productList("style:全部", 1, 10);
        assertThat(pageLst1.getTotal(), equalTo(4l));
        PageList<Product> pageLst2 = productManagerSvc.productList("style:多款", 1, 10);
        assertThat(pageLst2.getTotal(), equalTo(1l));
        PageList<Product> pageLst3 = productManagerSvc.productList("style:单款", 1, 10);
        assertThat(pageLst3.getTotal(), equalTo(3l));
    }
}