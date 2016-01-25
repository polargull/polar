package polarbear.unit.service.shopcart;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.*;

import org.junit.Before;
import org.junit.Test;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;

public class ModifyProductNumFromShopcartServiceTest extends AbstractShopcartServiceTest {

    @Before
    public void setUp() {
        super.setUp();
    }
    
    @Test
    public void shouldSucessWhenInShopcartUpdateProductNum() throws DaoException, ValidateException {
        expectProductPickerOp();
        expectShopcartOp();
        expectShopcartDetailOp();
        assertThat(modifyShopcartService.updateProductNumFromShopCart(PRODUCT_1_ID, PRODUCT_BUY_NUM).getProductNum(), is(SHOPCART_ORIGIN_NUM + PRODUCT_BUY_NUM));
    }
}