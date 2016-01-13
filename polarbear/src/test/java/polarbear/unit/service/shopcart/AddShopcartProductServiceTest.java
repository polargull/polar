package polarbear.unit.service.shopcart;

import static com.polarbear.util.Constants.ResultState.*;
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

public class AddShopcartProductServiceTest extends AbstractShopcartServiceTest {

    @Before
    public void setUp() {
        super.setUp();
    }
    
    @Test
    public void shouldAddSuccessWhenAddHaveStockProductInShopcart() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                oneOf(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(returnValue(anProduct().withID(PRODUCT_ID).withPrice(PRODUCT_PRICE).build()));
                oneOf(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(returnValue(anProduct()
                        .withID(PRODUCT_ID)
                        .withPrice(PRODUCT_PRICE)
                        .withSalePrice(PRODUCT_SALE_PRICE)
                        .withSaleBeginTime((int)(new DateTime().plusMinutes(-1).getMillis()/1000L))
                        .withSaleEndTime((int)(new DateTime().plusDays(1).getMillis()/1000L))
                        .build()));
            }
        });
        context.checking(new Expectations() {
            {
                context.checking(new Expectations() {
                    {
                        oneOf(shopcartDao).findByNamedQueryObject(with(any(String.class)), with(any(Object.class)));
                        will(returnValue(anShopcart().withPrice(SHOPCART_ORIGIN_PRICE).withProductNum(SHOPCART_ORIGIN_NUM).withUser(anUser()).build()));
                        oneOf(shopcartDao).findByNamedQueryObject(with(any(String.class)), with(any(Object.class)));
                        will(returnValue(anShopcart().withPrice(SHOPCART_ORIGIN_PRICE + PRODUCT_PRICE).withProductNum(SHOPCART_ORIGIN_NUM + PRODUCT_BUY_NUM).withUser(anUser()).build()));
                    }
                });
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDao).store(with(any(Shopcart.class)));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartLogDao).store(with(any(ShopcartLog.class)));
            }
        });
        testAddOneOriginPriceProduct();
        testAgainAddOneSalePriceProduct();
    }

    @Test
    public void shouldThrowValidateExceptionWhenAddNoHaveStockProductInShopcart() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                oneOf(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(throwException(new ValidateException(PRODUCT_NUM_IS_0)));               
            }
        });
        expectedEx.expect(ValidateException.class);
        expectedEx.expectMessage(PRODUCT_NUM_IS_0.emsg());
        modifyShopService.addProductToShopcart(PRODUCT_ID, PRODUCT_BUY_NUM);
    }

    @Test
    public void shouldThrowValidateExceptionWhenAddAlreadyPullOffProductInShopcart() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                oneOf(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(throwException(new ValidateException(PRODUCT_PULL_OFF)));               
            }
        });
        expectedEx.expect(ValidateException.class);
        expectedEx.expectMessage(PRODUCT_PULL_OFF.emsg());
        modifyShopService.addProductToShopcart(PRODUCT_ID, PRODUCT_BUY_NUM);        
    }

    private void testAddOneOriginPriceProduct() throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopService.addProductToShopcart(PRODUCT_ID, PRODUCT_BUY_NUM);
        assertThat(shopcart.getProductNum(), is(SHOPCART_ORIGIN_NUM + PRODUCT_BUY_NUM));
        assertThat(shopcart.getPrice(), is(SHOPCART_ORIGIN_PRICE + PRODUCT_PRICE * PRODUCT_BUY_NUM));
    }
    
    private void testAgainAddOneSalePriceProduct() throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopService.addProductToShopcart(PRODUCT_ID, PRODUCT_BUY_NUM);
        assertThat(shopcart.getProductNum(), is(SHOPCART_ORIGIN_NUM + PRODUCT_BUY_NUM * 2));
        assertThat(shopcart.getPrice(), is(SHOPCART_ORIGIN_PRICE + PRODUCT_PRICE + PRODUCT_SALE_PRICE * PRODUCT_BUY_NUM));
    }
    
}