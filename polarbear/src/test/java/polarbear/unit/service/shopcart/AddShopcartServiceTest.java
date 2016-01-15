package polarbear.unit.service.shopcart;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.*;
import static polarbear.testdata.product.ProductBuilder.anProduct;
import static polarbear.testdata.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.shopcart.ShopcartDetailBuilder.anShopcartDetail;
import static polarbear.testdata.user.UserBuilder.anUser;

import javax.security.auth.login.LoginException;

import org.jmock.Expectations;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartDetail;
import com.polarbear.service.product.query.ProductPicker;
import com.polarbear.service.shopcart.ModifyShopcartService;

public class AddShopcartServiceTest extends AbstractShopcartServiceTest {

    @Before
    public void setUp() {
        super.setUp();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRetureShopcarWhenAddShopcartAndLoginedAndNoHaveShopcart() throws DaoException, ValidateException {
        expectProductPickerOp();
        expectShopcartDaoOp(false);
        expectShopcartDetailDaoOp();
        testOriginPriceProductWhenNoHaveShopcart();
        testSalePriceProductWhenNoHaveShopcart();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRetureShopcarWhenAddShopcartAndLoginedAndHaveShopcart() throws DaoException, ValidateException {
        expectProductPickerOp();
        expectShopcartDaoOp(true);
        expectShopcartDetailDaoOp();
        testOriginPriceProductWhenHaveShopcart();
        testSalePriceProductWhenHaveShopcart();
    }

    @Test
    public void shouldThrowValidateExceptionWhenPickoutProductAlreadyPullOff() throws LoginException, DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                allowing(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(throwException(new ValidateException(PRODUCT_PULL_OFF)));
            }
        });
        expectedEx.expect(ValidateException.class);
        expectedEx.expectMessage(PRODUCT_PULL_OFF.emsg());
        modifyShopService.addShopcart(PRODUCT_ID);
    }

    @Test
    public void shouldThrowValidateExceptionWhenPickoutProductNumIs0() throws LoginException, DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                allowing(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(throwException(new ValidateException(PRODUCT_NUM_IS_0)));
            }
        });
        expectedEx.expect(ValidateException.class);
        expectedEx.expectMessage(PRODUCT_NUM_IS_0.emsg());
        modifyShopService.addShopcart(PRODUCT_ID);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldThrowValidateExceptionWhenStoreDBERR() throws LoginException, DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                allowing(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(returnValue(anProduct().withID(PRODUCT_ID).withPrice(99d).build()));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDao).findByNamedQueryObject(with(any(String.class)), with(any(Object.class)));
                will(returnValue(null));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDao).store(with(any(Shopcart.class)));
                will(throwException(new DaoException(DB_ERR)));
            }
        });
        expectedEx.expect(DaoException.class);
        expectedEx.expectMessage(DB_ERR.emsg());
        modifyShopService.addShopcart(PRODUCT_ID);
    }

    private void testOriginPriceProductWhenHaveShopcart() throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopService.addShopcart(PRODUCT_ID);
        assertThat(shopcart.getProductNum(), is(SHOPCART_ORIGIN_NUM + 1));
        assertThat(shopcart.getPrice(), is(SHOPCART_ORIGIN_PRICE + PRODUCT_PRICE));
    }
    
    private void testSalePriceProductWhenHaveShopcart() throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopService.addShopcart(PRODUCT_ID);
        assertThat(shopcart.getProductNum(), is(SHOPCART_ORIGIN_NUM + 2));
        assertThat(shopcart.getPrice(), is(SHOPCART_ORIGIN_PRICE + PRODUCT_PRICE + PRODUCT_SALE_PRICE));
    }

    private void testOriginPriceProductWhenNoHaveShopcart() throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopService.addShopcart(PRODUCT_ID);
        assertThat(shopcart.getProductNum(), is(1));
        assertThat(shopcart.getPrice(), is(SHOPCART_ORIGIN_PRICE));
    }

    private void testSalePriceProductWhenNoHaveShopcart() throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopService.addShopcart(PRODUCT_ID);
        assertThat(shopcart.getProductNum(), is(1));
        assertThat(shopcart.getPrice(), is(PRODUCT_SALE_PRICE));
    }    

}