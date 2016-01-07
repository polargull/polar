package polarbear.acceptance.shopcart;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import polarbear.acceptance.Request.ResultCallback;

import com.meterware.httpunit.WebResponse;
import com.polarbear.service.login.LoginData;
import com.polarbear.service.shopcart.MyShopcart;
import com.polarbear.service.shopcart.ShopcartProduct;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.UserCookieUtil;

public class ShopcartAddTest {

    @Test
    public void shouldSuccessReturnShopcartJsonDataWhenSelectProductAddShopcartAndUserLogined() {
        anRequest(SHOPCART_ADD_URL)
            .withCookie(UserCookieUtil.COOKIE_NAME, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
            .addParams("pid", String.valueOf(PRODUCT_ID))
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    MyShopcart myShopcart = resultBody(jsonResult, MyShopcart.class);
                    ShopcartProduct p = resultBody(jsonResult, MyShopcart.class).getProductList().get(0);
                    assertThat(p.getName(), is(PRODUCT_NAME));
                    assertThat(myShopcart.getShopcart().getCount(), is(PRODUCT_COUNT));
                }
            });
    }

//    @Test
    public void shouldSuccessReturnShopcartCookieDataWhenSelectProductAddShopcartAndUserUnLogin() {
        anRequest(SHOPCART_ADD_URL)
            .addParams("pid", String.valueOf(PRODUCT_ID))
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    assertThat(resultBody(jsonResult,LoginData.class).getUser().getName(), is(UNAME));
                }
            });
    }

//    @Test
    public void shouldReturnFailJsonTipWhenSelectProductAddShopcart() {
        anRequest(LOGIN_URL)
            .addParams("uname", ERROR_UNAME)
            .addParams("password", ERROR_PWD)
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(LOGIN_NAME_PWD_ERR));
                    assertThat(resultBody(jsonResult,LoginData.class), nullValue());
                }
                public void onResponse(WebResponse response) {
                    assertThat(response.getNewCookieValue(UserCookieUtil.COOKIE_NAME),nullValue());
                }
            });
    }
}
