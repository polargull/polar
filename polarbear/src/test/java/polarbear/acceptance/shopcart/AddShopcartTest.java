package polarbear.acceptance.shopcart;

import static com.polarbear.util.Constants.ResultState.NEED_LOGIN;
import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.ERR_PRODUCT_ID;
import static polarbear.test.util.Constants.PRODUCT_1_ID;
import static polarbear.test.util.Constants.PRODUCT_2_ID;
import static polarbear.test.util.Constants.SHOPCART_ADD_URL;
import static polarbear.test.util.Constants.SHOPCART_DATA_USER1_LIST;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request;
import polarbear.acceptance.Request.ResultCallback;

import com.meterware.httpunit.WebResponse;
import com.polarbear.service.shopcart.ShopcartProduct;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;
import com.polarbear.web.shopcart.ShopcartController;

public class AddShopcartTest {

    @Test
    public void shouldSuccessReturnCookieShopcartNumJsonDataWhenSelectProductAddShopcartAndUserLogined() {
        ShopcartProduct p1 = SHOPCART_DATA_USER1_LIST.get(0);
        requestTest(p1.getProduct().getId(), new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                assertThat(jsonResult.getBody().isEmpty(), is(true));
            }
            public void onResponse(WebResponse response) {
                assertThat(response.getNewCookieValue(ShopcartController.SHOPCART_PRODUCT_NUM), is("7"));
            }
        });        
        requestTest(PRODUCT_2_ID, new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                assertThat(jsonResult.getBody().isEmpty(), is(true));
            }
            public void onResponse(WebResponse response) {
                assertThat(response.getNewCookieValue(ShopcartController.SHOPCART_PRODUCT_NUM), is("8"));
            }
        });
    }

    private void requestTest(long pid, ResultCallback callback) {
        anRequest(SHOPCART_ADD_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
            .addParams("pid", String.valueOf(pid))
            .post(callback);
    }

    @Test
    public void shouldReturnNeedLoginFailJsonTipWhenNoLogin() {
        anRequest(SHOPCART_ADD_URL).addParams("pid", String.valueOf(PRODUCT_1_ID)).post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(NEED_LOGIN));
            }
        });
    }

    @Test
    public void shouldReturnParamFailJsonTipWhenInputErrPidOrNoInputPid() {
        testInputPidParam(ERR_PRODUCT_ID);
        testInputPidParam(null);
    }

    private void testInputPidParam(String pid) {
        Request request = anRequest(SHOPCART_ADD_URL).withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3");
        if (pid != null) {
            request.addParams("pid", pid);
        }
        request.post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(PARAM_ERR));
            }
        });
    }
}
