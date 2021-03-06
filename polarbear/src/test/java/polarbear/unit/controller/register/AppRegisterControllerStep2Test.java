package polarbear.unit.controller.register;

import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static polarbear.test.util.Constants.CELLPHONE;
import static polarbear.test.util.Constants.PWD;
import static polarbear.test.util.Constants.REGIST_STEP2_URL;
import static polarbear.test.util.Constants.UNAME;
import static polarbear.test.util.Constants.VERIFY_CODE;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.testdata.builder.user.UserBuilder.anUser;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import polarbear.unit.controller.AbstractContextControllerTest;

import com.alibaba.fastjson.TypeReference;
import com.polarbear.ValidateException;
import com.polarbear.domain.User;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.service.register.AppRegisterStep2Service;
import com.polarbear.service.register.util.VerifyCodeEncoder;
import com.polarbear.web.login.front.LoginController;
import com.polarbear.web.regist.AppRegisterController;

public class AppRegisterControllerStep2Test extends AbstractContextControllerTest {
    AppRegisterController appRegisterController = new AppRegisterController();
    public AppRegisterStep2Service appRegisterStep2Service;
    String NEED_COMPARE_VERIFY_CODE_ENCODE;

    @Before
    public void setup() {
        setServiceAndDependentComponent(appRegisterController, "appRegisterStep2Service");
        super.setUp(appRegisterController);
        try {
            NEED_COMPARE_VERIFY_CODE_ENCODE = VerifyCodeEncoder.getInstance().encodeNeedCompareVerifyCode(VERIFY_CODE, CELLPHONE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void shouldValidateWhenInputCorrectVerifyCodeAndPwdOnRegisterStep2() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(appRegisterStep2Service).completeStep2(VERIFY_CODE, NEED_COMPARE_VERIFY_CODE_ENCODE, PWD);
                will(returnValue(new LoginData<User>(anUser().withUname(UNAME).build())));
            }
        });
        Cookie cookie = new Cookie(AppRegisterController.ENCODE_VERIFY_CODE, NEED_COMPARE_VERIFY_CODE_ENCODE);
        MvcResult result = mockMvc.perform(post(REGIST_STEP2_URL).cookie(cookie).param("verifycode", String.valueOf(VERIFY_CODE)).param("pwd", PWD)).andExpect(status().isOk())
        // .andDo(print())
                .andReturn();
        assertThat(resultState(result), is(SUCCESS));
        assertThat(result.getResponse().getCookie(LoginController.USER_LOGIN_COOKIE).getValue(), not(nullValue()));
        assertThat(resultBody(result, new TypeReference<LoginData<User>>(){}).getUser().getName(), is(UNAME));
    }

    @Test
    public void shouldInValidateWhenInputErrVerifyCodeOrPwdOrOnRegisterStep2() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(appRegisterStep2Service).completeStep2(VERIFY_CODE, NEED_COMPARE_VERIFY_CODE_ENCODE, PWD);
                will(returnValue(new LoginData<User>(anUser().withUname(UNAME).build())));
            }
        });
        Cookie cookie = new Cookie(AppRegisterController.ENCODE_VERIFY_CODE, NEED_COMPARE_VERIFY_CODE_ENCODE);
        testInputErrVerifyCodeOrPwdPost(null, null, null);
        testInputErrVerifyCodeOrPwdPost(cookie, null, null);
        testInputErrVerifyCodeOrPwdPost(cookie, "123", PWD);
        testInputErrVerifyCodeOrPwdPost(cookie, "666688", "1");
        testInputErrVerifyCodeOrPwdPost(null, String.valueOf(VERIFY_CODE), PWD);
    }

    private void testInputErrVerifyCodeOrPwdPost(Cookie cookie, String verifycode, String pwd) throws Exception, UnsupportedEncodingException {
        MockHttpServletRequestBuilder mockRequest = post(REGIST_STEP2_URL);
        if (verifycode != null)
            mockRequest.param("verifycode", verifycode);
        if (pwd != null)
            mockRequest.param("pwd", pwd);
        if (cookie != null)
            mockRequest.cookie(cookie);
        MvcResult result = mockMvc.perform(mockRequest).andExpect(status().isOk())
        // .andDo(print())
                .andReturn();
        assertThat(resultState(result), is(PARAM_ERR));
    }

    @Test
    public void shouldInValidateWhenEncodeVerifyCodeErrOnRegisterStep2() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(appRegisterStep2Service).completeStep2(VERIFY_CODE, NEED_COMPARE_VERIFY_CODE_ENCODE, PWD);
                will(throwException(new ValidateException(PARAM_ERR)));
            }
        });
        Cookie cookie = new Cookie(AppRegisterController.ENCODE_VERIFY_CODE, NEED_COMPARE_VERIFY_CODE_ENCODE);
        MvcResult result = mockMvc.perform(post(REGIST_STEP2_URL).cookie(cookie).param("verifycode", String.valueOf(VERIFY_CODE)).param("pwd", PWD)).andExpect(status().isOk())
        // .andDo(print())
                .andReturn();
        assertThat(resultState(result), is(PARAM_ERR));
    }

}
