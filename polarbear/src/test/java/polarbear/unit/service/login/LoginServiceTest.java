package polarbear.unit.service.login;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static polarbear.test.util.Constants.*;
import static polarbear.testdata.user.UserBuilder.anUser;

import javax.security.auth.login.LoginException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.User;
import com.polarbear.service.login.UserLoginService;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.service.login.util.LoginEncoder;
import com.polarbear.util.MD5Util;
public class LoginServiceTest extends AbstractMock {
    private UserLoginService loginService = new UserLoginService();   
    public LoginEncoder loginEncoder;
    
    @Before
    public void setUp() {
        setServiceAndDependentComponent(loginService, "userDao", "loginEncoder");
    }

    @Test
    public void shouldRetureLoginedDataWhenLoginSuccess() throws DaoException {
        context.checking(new Expectations() {
            {
                allowing(userDao).findByNamedQueryObject("queryUnameAndPwd", UNAME, MD5_PWD);
                // 设定预期值
                will(returnValue(anUser().withUname(UNAME).withID(1L).build()));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(loginEncoder).encodeLoginUser(with(any(User.class)));
                // 设定预期值
                will(returnValue("xxxxxxxx"));
            }
        });
        try {
            LoginData<User> loginData = loginService.login(UNAME, PWD);
            assertThat(loginData.getUser().getName(), is(UNAME));
            assertThat(loginData.getAuthEncode(), is("xxxxxxxx"));
        } catch (LoginException e) {
            fail("登录应该成功但失败");
        }
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
        loginService.login(UNAME, PWD);
    }
}
