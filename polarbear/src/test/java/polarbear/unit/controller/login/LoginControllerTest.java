package polarbear.unit.controller.login;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static polarbear.test.util.Constants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static polarbear.testdata.user.UserBuilder.anUser;
import static polarbear.test.util.JsonResultConvertUtil.*;
import javax.security.auth.login.LoginException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import polarbear.unit.controller.AbstractContextControllerTest;

import com.polarbear.service.login.LoginData;
import com.polarbear.service.login.LoginService;
import com.polarbear.util.cookie.UserCookieUtil;
import com.polarbear.web.login.LoginController;

public class LoginControllerTest extends AbstractContextControllerTest {
    
    LoginController loginController = new LoginController();
    public LoginService loginService;

    @Before
    public void setup() {
        setServiceAndDependentComponent(loginController, "loginService");
        super.setUp(loginController);
    }
    
    @Test
    public void shouldValidateWhenInputCorrectNameAndPwdLogin() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(loginService).login(UNAME, PWD);
                // 设定预期值
                will(returnValue(new LoginData(anUser().withUname(UNAME).build())));
            }
        });
        MvcResult result = mockMvc
            .perform(post(LOGIN_URL)
                    .param("uname", UNAME)
                    .param("password", PWD))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
        assertThat(result.getResponse().getCookie(UserCookieUtil.COOKIE_NAME).getValue(),not(nullValue()));
        assertThat(resultState(result), is(SUCCESS));
        assertThat(resultBody(result,LoginData.class).getUser().getName(), is(UNAME));
    }
    
    @Test
    public void shouldInValidateWhenInputErrorNameOrPwdLogin() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(loginService).login(UNAME, PWD);
                // 设定预期值
                will(throwException(new LoginException(LOGIN_NAME_PWD_ERR.emsg())));
            }
        });
        MvcResult result = mockMvc
            .perform(post(LOGIN_URL)
                    .param("uname", UNAME)
                    .param("password", PWD))
            .andExpect(status().isOk())
            .andReturn();
        assertThat(resultState(result), is(LOGIN_NAME_PWD_ERR));
    }
}
