package polarbear.integration.service.product;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.PRODUCT_NAME;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct1;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import task.CreateAcceptanceTestDataTask;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.product.Product;
import com.polarbear.service.PageList;
import com.polarbear.service.product.ProductManagerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-service.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ProductManagerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private ProductManagerService productManagerSvc;

    @Before
    public void init() throws Exception {
        jdbcTemplate.batchUpdate(CreateAcceptanceTestDataTask.createAllTestDataScriptArray());
    }

    @Test
    public void testProductManagerQueryByName() throws DaoException {
        final String NOT_EXIST_PRODUCT_NAME = "name:羽绒服";
        PageList<Product> pageLst1 = productManagerSvc.productList(NOT_EXIST_PRODUCT_NAME, 1, 10);
        assertThat(pageLst1.getTotal(), equalTo(0));
        
        final String EXIST_PRODUCT_NAME = "name:" + createProduct1().getName();        
        PageList<Product> pageLst2 = productManagerSvc.productList(EXIST_PRODUCT_NAME, 1, 10);
        assertThat(pageLst2.getTotal(), equalTo(1));

        final String VAGUE_PRODUCT_NAME = "name:" + PRODUCT_NAME;
        final int VAGUE_QUERY_TOTAL_PRODUCT_NUMS = 6;        
        PageList<Product> pageLst3 = productManagerSvc.productList(VAGUE_PRODUCT_NAME, 1, 10);
        assertThat(pageLst3.getTotal(), equalTo(VAGUE_QUERY_TOTAL_PRODUCT_NUMS));
    }

    @Test
    public void testProductManagerQueryByStyle() throws DaoException {
        final int TOTAL_PRODUCT_NUMS = 6;
        PageList<Product> pageLst1 = productManagerSvc.productList("style:全部", 1, 10);
        assertThat(pageLst1.getTotal(), equalTo(TOTAL_PRODUCT_NUMS));
        final int MULTIPLY_STYLE_NUMS = 2;
        PageList<Product> pageLst2 = productManagerSvc.productList("style:多款", 1, 10);
        assertThat(pageLst2.getTotal(), equalTo(MULTIPLY_STYLE_NUMS));
        final int SINGAL_STYLE_NUMS = 4;
        PageList<Product> pageLst3 = productManagerSvc.productList("style:单款", 1, 10);
        assertThat(pageLst3.getTotal(), equalTo(SINGAL_STYLE_NUMS));
    }

    @Test
    public void testProductManagerQueryByTimeRang() throws DaoException {
        int yesterday = (int) (new DateTime().plusDays(-1).getMillis() / 1000l);
        int futureThreeday = (int) (new DateTime().plusDays(3).getMillis() / 1000l);
        int yesterday_1 = (int) (new DateTime().plusDays(-2).getMillis() / 1000l);
        
        final String INCLUDE_SALE_TIME = "saleTimeRrang:" + yesterday + "-" + futureThreeday + "";
        final String NOT_INCLUDE_SALE_TIME = "saleTimeRrang:" + yesterday_1 + "-" + yesterday + "";
        final int VALIDATE_SALE_PRODUCT_NUM = 1;
        final int NOT_VALIDATE_SALE_PRODUCT_NUM = 0;
        PageList<Product> pageLst1 = productManagerSvc.productList(INCLUDE_SALE_TIME, 1, 10);
        assertThat(pageLst1.getTotal(), equalTo(VALIDATE_SALE_PRODUCT_NUM));
        PageList<Product> pageLst2 = productManagerSvc.productList(NOT_INCLUDE_SALE_TIME, 1, 10);
        assertThat(pageLst2.getTotal(), equalTo(NOT_VALIDATE_SALE_PRODUCT_NUM));
    }
}
