package polarbear.unit.controller.register;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static polarbear.test.util.Constants.REGIST_STEP1_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import polarbear.unit.controller.AbstractContextControllerTest;

import com.polarbear.service.RemoteInvokeServiceException;
import com.polarbear.service.register.AppRegisterStep1Service;
import com.polarbear.web.regist.AppRegisterController;
import com.polarbear.web.regist.bean.ReturnVerifyCode;

public class AppRegisterControllerStep1Test extends AbstractContextControllerTest {
    AppRegisterController appRegisterController = new AppRegisterController();
    public AppRegisterStep1Service appRegisterStep1Service;
    final long CELLPHONE = 13717686218l;
    final long ERR_CELLPHONE = 1717686218l;
    
    @Before
    public void setup() {
        setServiceAndDependentComponent(appRegisterController, "appRegisterStep1Service");
        super.setUp(appRegisterController);
    }
    @Test
    public void shouldReturnVerifyCodeWhenInputCorrectCellphoneOnRegisterStep1() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(appRegisterStep1Service).completeStep1(CELLPHONE);
                // 设定预期值
                will(returnValue(new ReturnVerifyCode(666669,"aaaaaaaaaaaaaaaa")));
            }
        });
        MvcResult result = mockMvc
            .perform(post(REGIST_STEP1_URL)
                    .param("cellphone", String.valueOf(CELLPHONE)))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
        assertThat(result.getResponse().getCookie(AppRegisterController.ENCODE_VERIFY_CODE).getValue(),not(nullValue()));
        assertThat(resultState(result), is(SUCCESS));
        assertThat(Integer.valueOf(resultBody(result, AppRegisterController.VERIFY_CODE)), allOf(greaterThan(666666),lessThan(888888)));
    }
    
    @Test
    public void shouldInvalidateWhenInputErrCellphoneOnRegisterStep1() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(appRegisterStep1Service).completeStep1(ERR_CELLPHONE);
                // 设定预期值
                will(returnValue(new ReturnVerifyCode(666669,"aaaaaaaaaaaaaaaa")));
            }
        });
        MvcResult result = mockMvc
            .perform(post(REGIST_STEP1_URL)
                    .param("cellphone", String.valueOf(ERR_CELLPHONE)))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
        assertThat(result.getResponse().getCookie(AppRegisterController.ENCODE_VERIFY_CODE),nullValue());
        assertThat(resultState(result), is(PARAM_ERR));
        assertThat(resultBody(result, AppRegisterController.VERIFY_CODE), nullValue());
    }
    
    @Test
    public void shouldInvalidateWhenSmsServiceErrOnRegisterStep1() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(appRegisterStep1Service).completeStep1(CELLPHONE);
                // 设定预期值
                will(throwException(new RemoteInvokeServiceException(SMS_SEND_FAIL)));
            }
        });
        MvcResult result = mockMvc
            .perform(post(REGIST_STEP1_URL)
                    .param("cellphone", String.valueOf(CELLPHONE)))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
        assertThat(result.getResponse().getCookie(AppRegisterController.ENCODE_VERIFY_CODE), nullValue());
        assertThat(resultState(result), is(SMS_SEND_FAIL));
        assertThat(resultBody(result, AppRegisterController.VERIFY_CODE), nullValue());
    }
}
