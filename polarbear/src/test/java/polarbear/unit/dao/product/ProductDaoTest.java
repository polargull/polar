package polarbear.unit.dao.product;

import static org.hamcrest.Matchers.*;
import static com.polarbear.util.Constants.PRODUCT_STATE.*;
import static polarbear.test.util.Constants.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import static org.junit.Assert.*;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
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
            Product p = productDao.findByNamedQueryObject("queryProductByIdAndState", PRODUCT_ID, PUT_ON.value());
            assertThat(p, notNullValue());
            assertThat(p.getId(), is(PRODUCT_ID));
            assertThat(p.getState(), is(PUT_ON.value()));
        } catch (DaoException e) {
            fail("挑选商品dao操作失败了,msg:" + e.getMessage());
        }
    }
}