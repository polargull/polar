package polarbear.unit.service.shopcart;

import static com.polarbear.util.Constants.ResultState.DB_ERR;
import static com.polarbear.util.Constants.ResultState.PRODUCT_NUM_IS_0;
import static com.polarbear.util.Constants.ResultState.PRODUCT_PULL_OFF;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.PRODUCT_ID;
import static polarbear.testdata.product.ProductBuilder.anProduct;
import static polarbear.testdata.shopcart.ShopcartBuilder.anShopcart;
import static polarbear.testdata.user.UserBuilder.anUser;

import javax.security.auth.login.LoginException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartLog;
import com.polarbear.service.product.query.ProductPicker;
import com.polarbear.service.shopcart.ModifyShopcartService;

public class AddShopcartServiceTest extends AbstractMock {
    private ModifyShopcartService modifyShopService = new ModifyShopcartService();
    public ProductPicker productPicker;

    @Before
    public void setUp() {
        setServiceAndDependentComponent(modifyShopService, "shopcartDao", "shopcartLogDao", "productPicker");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRetureShopcarProductNumWhenAddShopcartAndLoginedAndNoHaveShopcart() throws DaoException, ValidateException {
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
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartLogDao).store(with(any(ShopcartLog.class)));
            }
        });
        Shopcart shopcart = modifyShopService.addShopcart(PRODUCT_ID);
        assertThat(shopcart.getProductNum(), is(1));
        assertThat(shopcart.getPrice(), is(99d));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldRetureShopcarProductNumWhenAddShopcartAndLoginedAndHaveShopcart() throws DaoException, ValidateException {
        context.checking(new Expectations() {
            {
                allowing(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(returnValue(anProduct().withID(PRODUCT_ID).withPrice(99d).build()));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(shopcartDao).findByNamedQueryObject(with(any(String.class)), with(any(Object.class)));
                will(returnValue(anShopcart().withPrice(66d).withProductNum(6).withUser(anUser()).build()));
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
        Shopcart shopcart = modifyShopService.addShopcart(PRODUCT_ID);
        assertThat(shopcart.getProductNum(), is(7));
        assertThat(shopcart.getPrice(), is(165d));
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
}
