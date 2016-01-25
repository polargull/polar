package polarbear.unit.service.shopcart;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.PRODUCT_1_ID;
import static polarbear.test.util.Constants.SHOPCART_ORIGIN_NUM;

import org.junit.Before;
import org.junit.Test;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;

public class RemoveShopcartProductServiceTest extends AbstractShopcartServiceTest {

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldSuccessWhenRemoveProductFromShopcart() throws DaoException, ValidateException {
        expectFindProductOp();
        expectRemoveShopcartProductComponentOp();
        expectShopcartOp();
        assertThat(modifyShopcartService.removeProductFromShopCart(PRODUCT_1_ID).getProductNum(), is(SHOPCART_ORIGIN_NUM - 1));
    }
}