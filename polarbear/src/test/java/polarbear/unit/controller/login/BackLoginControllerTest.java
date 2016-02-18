package polarbear.unit.controller.login;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.*;
import static polarbear.testdata.builder.user.UserBuilder.anUser;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.security.auth.login.LoginException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import polarbear.unit.controller.AbstractContextControllerTest;

import com.alibaba.fastjson.TypeReference;
import com.polarbear.domain.User;
import com.polarbear.service.login.AdminLoginService;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.web.login.back.LoginController;

public class BackLoginControllerTest extends AbstractContextControllerTest {
    
    LoginController loginController = new LoginController();
    public AdminLoginService adminLoginService;

    @Before
    public void setup() {
        setServiceAndDependentComponent(loginController, "adminLoginService");
        super.setUp(loginController);
    }
    
    @Test
    public void shouldValidateWhenInputCorrectNameAndPwdLogin() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(adminLoginService).login(UNAME, PWD);
                // 设定预期值
                will(returnValue(new LoginData<User>(anUser().withUname(UNAME).build())));
            }
        });
        MvcResult result = mockMvc
            .perform(post(ADMIN_LOGIN_URL)
                    .param("uname", UNAME)
                    .param("password", PWD))
            .andExpect(status().isOk())
//            .andDo(print())
            .andReturn();
        assertThat(result.getResponse().getCookie(LoginController.ADMIN_LOGIN_COOKIE).getValue(),not(nullValue()));
        assertThat(resultState(result), is(SUCCESS));
        assertThat(resultBody(result, new TypeReference<LoginData<User>>(){}).getUser().getName(), is(UNAME));
    }
    
    @Test
    public void shouldInValidateWhenInputErrorNameOrPwdLogin() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(adminLoginService).login(UNAME, PWD);
                // 设定预期值
                will(throwException(new LoginException(LOGIN_NAME_PWD_ERR.emsg())));
            }
        });
        MvcResult result = mockMvc
            .perform(post(ADMIN_LOGIN_URL)
                    .param("uname", UNAME)
                    .param("password", PWD))
            .andExpect(status().isOk())
            .andReturn();
        assertThat(resultState(result), is(LOGIN_NAME_PWD_ERR));
    }
}
