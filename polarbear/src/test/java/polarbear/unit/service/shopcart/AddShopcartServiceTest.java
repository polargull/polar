package polarbear.unit.service.shopcart;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.*;

import javax.security.auth.login.LoginException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Shopcart;
import com.polarbear.domain.ShopcartLog;
import com.polarbear.domain.User;

import static com.polarbear.util.Constants.*;
import com.polarbear.service.product.query.ProductPicker;
import com.polarbear.service.shopcart.ModifyShopcartService;
import com.polarbear.util.MD5Util;
import com.polarbear.util.factory.CurrentThreadUserFactory;

import static polarbear.testdata.product.ProductBuilder.*;
import static polarbear.testdata.user.UserBuilder.*;

public class AddShopcartServiceTest extends AbstractMock {
    private ModifyShopcartService modifyShopService = new ModifyShopcartService();
    public ProductPicker          productPicker;

    @Before
    public void setUp() {
        setServiceAndDependentComponent(modifyShopService, "shopcartDao", "shopcartLogDao", "productPicker");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRetureShopcarProductNumWhenAddShopcartAndLogined() throws DaoException, ValidateException {
//        CurrentThreadUserFactory.setUser(anUser().withID(USER_ID).build());
        context.checking(new Expectations() {
            {
                allowing(productPicker).pickoutTheProduct(PRODUCT_ID);
                will(returnValue(anProduct().withID(PRODUCT_ID).build()));
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
        assertThat(modifyShopService.addShopcart(PRODUCT_ID), is(1));
    }

    // @Test
    public void shouldThrowLoginExceptionWhenLoginFail() throws LoginException, DaoException {
        context.checking(new Expectations() {
            {
                allowing(userDao).findByNamedQueryObject("queryUnameAndPwd", UNAME, MD5Util.encode2hex(PWD));
                // 设定预期值
                will(returnValue(null));
            }
        });
        expectedEx.expect(LoginException.class);
        expectedEx.expectMessage(LOGIN_NAME_PWD_ERR.emsg());
        // loginService.login(UNAME, PWD);
    }
}
