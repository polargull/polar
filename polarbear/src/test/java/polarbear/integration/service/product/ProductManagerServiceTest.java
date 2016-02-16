package polarbear.integration.service.product;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.CATEGORY_NAME;
import static polarbear.test.util.Constants.PRODUCT_NAME;
import static polarbear.test.util.Constants.PRODUCT_STYLE;
import static polarbear.test.util.Constants.PRODUCT_STYLE_ID;
import static polarbear.test.util.Constants.SHOPCARD_ID;
import static polarbear.testdata.DomainEntityConvertSqlUtil.createInsertSql;
import static polarbear.testdata.product.ProductBuilder.anProduct;
import static polarbear.testdata.product.StyleBuilder.anStyle;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import polarbear.testdata.product.StyleBuilder;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Category;
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
    public void init() throws DaoException, SQLException {
        Category category = new Category(SHOPCARD_ID, CATEGORY_NAME);
        StyleBuilder style = anStyle().withId(PRODUCT_STYLE_ID).withProperty(PRODUCT_STYLE);
        jdbcTemplate.update(createInsertSql(category));
        jdbcTemplate.update(createInsertSql(style.build()));
        jdbcTemplate.update(createInsertSql(anProduct().withID(1l).withName(PRODUCT_NAME + 1).putOn().withCategory(category).build()));
        jdbcTemplate.update(createInsertSql(anProduct().withID(2l).withName(PRODUCT_NAME + 2).putOn().withCategory(category).build()));
        jdbcTemplate.update(createInsertSql(anProduct().withID(3l).withName(PRODUCT_NAME + 3).pullOff().withCategory(category).build()));
        jdbcTemplate.update(createInsertSql(anProduct().withID(4l).withName(PRODUCT_NAME + 4).pullOff().withStyle(style).build()));
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