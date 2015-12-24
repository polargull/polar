package polarbear.unit.service.login;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static polarbear.testdata.user.UserBuilder.anUser;
import static com.polarbear.util.Constants.ResultState.*;

import javax.security.auth.login.LoginException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.service.login.LoginData;
import com.polarbear.service.login.LoginService;
import com.polarbear.util.MD5Util;

public class LoginServiceTest extends AbstractMock {
    private LoginService loginService = new LoginService();
    final String UNAME = "极地鸥";
    final String PWD = "123456";
    final String MD5_PWD = MD5Util.encode2hex(PWD);

    @Before
    public void setUp() {
        setServiceAndDependentComponent(loginService, "userDao");
    }

    @Test
    public void shouldRetureLoginedDataWhenLoginSuccess() {
        context.checking(new Expectations() {
            {
                allowing(userDao).findByNamedQueryObject("queryUnameAndPwd", UNAME, MD5_PWD);
                // 设定预期值
                will(returnValue(anUser().withUname(UNAME).build()));
            }
        });
        try {
            LoginData loginData = loginService.login(UNAME, PWD);
            assertThat(loginData.getUser().getName(), is(UNAME));
        } catch (LoginException e) {
            fail("登录应该成功但失败");
        }
    }

    @Test
    public void shouldThrowLoginExceptionWhenLoginFail() throws LoginException {
        context.checking(new Expectations() {
            {
                allowing(userDao).findByNamedQueryObject("queryUnameAndPwd", UNAME, MD5Util.encode2hex(PWD));
                // 设定预期值
                will(returnValue(null));
            }
        });
        expectedEx.expect(LoginException.class);
        expectedEx.expectMessage(LOGIN_NAME_PWD_ERR.emsg());
        loginService.login(UNAME, PWD);
    }
}
