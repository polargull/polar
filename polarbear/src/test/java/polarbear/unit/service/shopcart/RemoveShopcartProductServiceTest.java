package polarbear.unit.service.shopcart;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.*;
import static polarbear.testdata.product.ProductBuilder.anProduct;
import static polarbear.testdata.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.user.UserBuilder.anUser;

import org.jmock.Expectations;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartLog;

public class RemoveShopcartProductServiceTest extends AbstractShopcartServiceTest {

    @Before
    public void setUp() {
        super.setUp();
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldRemoveSuccessWhenAddHaveStockProductInShopcart() throws DaoException, ValidateException {
        expectProductPickerOp();
        context.checking(new Expectations() {
            {
                allowing(shopcartDao).store(with(any(Shopcart.class)));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDetailDao).store(with(any(ShopcartLog.class)));
            }
        });
        testRemoveOriginPriceProduct();
        testRemoveSalePriceProduct();
    }

    private void testRemoveSalePriceProduct() throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopService.decreaseProductFromShopcart(PRODUCT_ID, PRODUCT_REMOVE_NUM);
        assertThat(shopcart.getProductNum(), is(SHOPCART_ORIGIN_NUM - PRODUCT_REMOVE_NUM * 2));
        assertThat(shopcart.getPrice(), is(SHOPCART_ORIGIN_PRICE - PRODUCT_PRICE - PRODUCT_SALE_PRICE * PRODUCT_REMOVE_NUM));
    }

    private void testRemoveOriginPriceProduct() throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopService.decreaseProductFromShopcart(PRODUCT_ID, PRODUCT_REMOVE_NUM);
        assertThat(shopcart.getProductNum(), is(SHOPCART_ORIGIN_NUM - PRODUCT_REMOVE_NUM));
        assertThat(shopcart.getPrice(), is(SHOPCART_ORIGIN_PRICE - PRODUCT_PRICE * PRODUCT_REMOVE_NUM));
    }

}