package polarbear.unit.service.shopcart;

import static com.polarbear.util.Constants.ResultState.*;
import static polarbear.test.util.Constants.*;
import static polarbear.testdata.user.UserBuilder.anUser;

import javax.security.auth.login.LoginException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.dao.DaoException;
import com.polarbear.service.shopcart.ModifyShopcartService;
import com.polarbear.util.MD5Util;
public class AddShopcartServiceTest extends AbstractMock {
    private ModifyShopcartService addShopService = new ModifyShopcartService();   

    @Before
    public void setUp() {
        setServiceAndDependentComponent(addShopService, "userDao", "");
    }

    @Test
    public void shouldRetureLoginedDataWhenLoginSuccess() throws DaoException {
        context.checking(new Expectations() {
            {
                allowing(userDao).findByNamedQueryObject("queryUnameAndPwd", UNAME, MD5_PWD);
                // 设定预期值
                will(returnValue(anUser().withUname(UNAME).build()));
            }
        });
//        try {
//            LoginData loginData = loginService.login(UNAME, PWD);
//            assertThat(loginData.getUser().getName(), is(UNAME));
//        } catch (LoginException e) {
//            fail("登录应该成功但失败");
//        }
    }

    @Test
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
//        loginService.login(UNAME, PWD);
    }
}
