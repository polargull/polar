package polarbear.unit.controller.register;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static polarbear.test.util.Constants.REGIST_STEP2_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import javax.servlet.http.Cookie;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import polarbear.unit.controller.AbstractContextControllerTest;

import com.polarbear.service.register.AppRegisterStep1Service;
import com.polarbear.service.register.AppRegisterStep2Service;
import com.polarbear.web.regist.AppRegisterController;

public class AppRegisterControllerStep2Test extends AbstractContextControllerTest {
    AppRegisterController appRegisterController = new AppRegisterController();
    public AppRegisterStep2Service appRegisterStep2Service;
    final long CELLPHONE = 13717686218l;
    final int VERIFYCODE = 666888;
    final String PWD = "123456";
    
    @Before
    public void setup() {
        setServiceAndDependentComponent(appRegisterController, "appRegisterStep2Service");
        super.setUp(appRegisterController);
    }

    @Test
    public void shouldValidateWhenInputCorrectCellphoneOnRegisterStep1() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(appRegisterStep2Service).completeStep2(VERIFYCODE, AppRegisterStep1Service.encodeNeedCompareVerifyCode(VERIFYCODE, CELLPHONE), CELLPHONE, PWD);
            }
        });
        Cookie cookie = new Cookie(AppRegisterController.ENCODE_VERIFY_CODE, AppRegisterStep1Service.encodeNeedCompareVerifyCode(VERIFYCODE, CELLPHONE)); 
        MvcResult result = mockMvc
            .perform(post(REGIST_STEP2_URL).cookie(cookie)
                    .param("verifycode", String.valueOf(VERIFYCODE))
                    .param("pwd", PWD))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
        assertThat(resultState(result), is(SUCCESS));
    }
}
