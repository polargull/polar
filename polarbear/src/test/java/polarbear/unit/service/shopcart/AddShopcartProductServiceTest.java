package polarbear.unit.service.shopcart;

import static com.polarbear.util.Constants.ResultState.PRODUCT_NUM_IS_0;
import static com.polarbear.util.Constants.ResultState.PRODUCT_PULL_OFF;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.PRODUCT_BUY_NUM;
import static polarbear.test.util.Constants.PRODUCT_ID;
import static polarbear.test.util.Constants.PRODUCT_PRICE;
import static polarbear.test.util.Constants.PRODUCT_SALE_PRICE;
import static polarbear.test.util.Constants.SHOPCART_ORIGIN_NUM;
import static polarbear.test.util.Constants.SHOPCART_ORIGIN_PRICE;
import static polarbear.testdata.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.user.UserBuilder.anUser;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;

public class AddShopcartProductServiceTest extends AbstractShopcartServiceTest {

    @Before
    public void setUp() {
        super.setUp();
    }
    
    @Test
    public void shouldAddSuccessWhenAddHaveStockProductInShopcart() throws DaoException, ValidateException {
        expectProductPickerOp();
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
        expectShopcartDetailDaoOp();        
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
        modifyShopService.increaseProductToShopcart(PRODUCT_ID, PRODUCT_BUY_NUM);
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
        modifyShopService.increaseProductToShopcart(PRODUCT_ID, PRODUCT_BUY_NUM);        
    }

    private void testAddOneOriginPriceProduct() throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopService.increaseProductToShopcart(PRODUCT_ID, PRODUCT_BUY_NUM);
        assertThat(shopcart.getProductNum(), is(SHOPCART_ORIGIN_NUM + PRODUCT_BUY_NUM));
        assertThat(shopcart.getPrice(), is(SHOPCART_ORIGIN_PRICE + PRODUCT_PRICE * PRODUCT_BUY_NUM));
    }
    
    private void testAgainAddOneSalePriceProduct() throws DaoException, ValidateException {
        Shopcart shopcart = modifyShopService.increaseProductToShopcart(PRODUCT_ID, PRODUCT_BUY_NUM);
        assertThat(shopcart.getProductNum(), is(SHOPCART_ORIGIN_NUM + PRODUCT_BUY_NUM * 2));
        assertThat(shopcart.getPrice(), is(SHOPCART_ORIGIN_PRICE + PRODUCT_PRICE + PRODUCT_SALE_PRICE * PRODUCT_BUY_NUM));
    }
    
}