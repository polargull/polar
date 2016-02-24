package polarbear.unit.controller.order;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import polarbear.unit.controller.AbstractContextControllerTest;

import com.polarbear.web.order.OrderController;

public class OrderControllerTest extends AbstractContextControllerTest {
    
    OrderController orderController = new OrderController();
//    public UserLoginService loginService;
//
    @Before
    public void setup() {
//        setServiceAndDependentComponent(loginController, "loginService");
        super.setUp(orderController);
    }
    
//    @Test
//    public void shouldValidateWhenInputCorrectNameAndPwdLogin() throws Exception {
//        context.checking(new Expectations() {
//            {
//                allowing(loginService).login(UNAME, PWD);
//                // 设定预期值
//                will(returnValue(new LoginData<User>(anUser().withUname(UNAME).build())));
//            }
//        });
//        MvcResult result = mockMvc
//            .perform(post(LOGIN_URL)
//                    .param("uname", UNAME)
//                    .param("password", PWD))
//            .andExpect(status().isOk())
//            .andReturn();
//        assertThat(result.getResponse().getCookie(LoginController.USER_LOGIN_COOKIE).getValue(),not(nullValue()));
//        assertThat(resultState(result), is(SUCCESS));
//        assertThat(resultBody(result, new TypeReference<LoginData<User>>(){}).getUser().getName(), is(UNAME));
//    }
    
    @Test
    public void shouldInValidateWhenInputErrorNameOrPwdLogin() throws Exception {
//        context.checking(new Expectations() {
//            {
//                allowing(loginService).login(UNAME, PWD);
//                // 设定预期值
//                will(throwException(new LoginException(LOGIN_NAME_PWD_ERR.emsg())));
//            }
//        });
        MvcResult result = mockMvc
            .perform(
                    post(ORDER_CREATE_URL)
                    .param("pids", "1,2")
                    .param("nums", "1,2")
                    .param("buyMode", "1")
                    .param("payCode", "u")
                    .param("addressId", "1")
                    )
            .andExpect(status().isOk())
            .andReturn();
        assertThat(resultState(result), is(SUCCESS));
    }
}
