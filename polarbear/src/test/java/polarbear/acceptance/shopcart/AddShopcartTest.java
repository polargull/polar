package polarbear.acceptance.shopcart;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request;
import polarbear.acceptance.Request.ResultCallback;

import com.meterware.httpunit.WebResponse;
import com.polarbear.service.shopcart.MyShopcart;
import com.polarbear.service.shopcart.ShopcartProduct;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.UserCookieUtil;
import com.polarbear.web.shopcart.ShopcartController;

public class AddShopcartTest {

    @Test
    public void shouldSuccessReturnCookieShopcartNumJsonDataWhenSelectProductAddShopcartAndUserLogined() {
        anRequest(SHOPCART_ADD_URL).withCookie(UserCookieUtil.COOKIE_NAME, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
        .addParams("pid",String.valueOf(PRODUCT_ID))
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                assertThat(jsonResult.getBody().isEmpty(),is(true));
            }
            public void onResponse(WebResponse response) {
                assertThat(response.getNewCookieValue(ShopcartController.COUNT),is("1"));
            }
        });
    }

    @Test
    public void shouldReturnNeedLoginFailJsonTipWhenNoLogin() {
        anRequest(SHOPCART_ADD_URL)
        .addParams("pid",String.valueOf(PRODUCT_ID))
        .post(new ResultCallback() {
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
        Request request = anRequest(SHOPCART_ADD_URL).withCookie(UserCookieUtil.COOKIE_NAME, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3");
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
