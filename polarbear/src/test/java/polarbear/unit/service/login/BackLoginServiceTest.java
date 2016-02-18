package polarbear.unit.service.login;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static polarbear.test.util.Constants.ADMIN_UNAME;
import static polarbear.test.util.Constants.MD5_PWD;
import static polarbear.test.util.Constants.PWD;
import static polarbear.test.util.Constants.UNAME;
import static polarbear.testdata.builder.admin.AdminBuilder.anAdmin;

import javax.security.auth.login.LoginException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.dao.DaoException;
import com.polarbear.domain.Admin;
import com.polarbear.service.login.AdminLoginService;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.service.login.util.LoginEncoder;
import com.polarbear.util.MD5Util;
public class BackLoginServiceTest extends AbstractMock {
    private AdminLoginService adminLoginService = new AdminLoginService();   
    public LoginEncoder loginEncoder;
    
    @Before
    public void setUp() {
        setServiceAndDependentComponent(adminLoginService, "adminDao", "loginEncoder");
    }

    @Test
    public void shouldRetureLoginedDataWhenLoginSuccess() throws DaoException {
        context.checking(new Expectations() {
            {
                allowing(adminDao).findByNamedQueryObject("queryAdminNameAndPwd", UNAME, MD5_PWD);
                // 设定预期值
                will(returnValue(anAdmin().withUname(ADMIN_UNAME).withPassword(MD5_PWD).withID(1L).build()));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(loginEncoder).encodeLoginUser(with(any(Admin.class)));
                // 设定预期值
                will(returnValue("xxxxxxxx"));
            }
        });
        try {
            LoginData<Admin> loginData = adminLoginService.login(UNAME, PWD);
            assertThat(loginData.getUser().getName(), is(ADMIN_UNAME));
            assertThat(loginData.getAuthEncode(), is("xxxxxxxx"));
        } catch (LoginException e) {
            fail("登录应该成功但失败");
        }
    }

    @Test
    public void shouldThrowLoginExceptionWhenLoginFail() throws LoginException, DaoException {
        context.checking(new Expectations() {
            {
                allowing(adminDao).findByNamedQueryObject("queryAdminNameAndPwd", UNAME, MD5Util.encode2hex(PWD));
                // 设定预期值
                will(returnValue(null));
            }
        });
        expectedEx.expect(LoginException.class);
        expectedEx.expectMessage(LOGIN_NAME_PWD_ERR.emsg());
        adminLoginService.login(UNAME, PWD);
    }
}