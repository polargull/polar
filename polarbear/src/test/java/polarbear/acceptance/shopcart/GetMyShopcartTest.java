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

import com.polarbear.service.shopcart.MyShopcart;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.UserCookieUtil;
import com.polarbear.web.login.front.LoginController;
import com.polarbear.web.shopcart.ShopcartController;

public class GetMyShopcartTest {

    @Test
    public void shouldReturnMyShopcartDataWhenQueryShopcartAndUserLogined() {
        anRequest(SHOPCART_GET_URL).withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3").post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                MyShopcart shopcart = resultBody(jsonResult, MyShopcart.class);
                assertThat(shopcart.getProductNum(), equalTo(3));
                assertThat(shopcart.getTotalPrice(), equalTo(PRODUCT_1_PRICE * 2 + PRODUCT_2_PRICE * 1));
                assertThat(shopcart.getProductList().size(), equalTo(2));
            }
        });
    }
    
    @Test
    public void shouldReturnMyShopcartDataWhenQueryShopcartAndUserNoLogin() {
        anRequest(SHOPCART_GET_URL).withCookie(ShopcartController.SHOPCART, "1_3|2_3|").post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                MyShopcart shopcart = resultBody(jsonResult, MyShopcart.class);
                assertThat(shopcart.getProductNum(), equalTo(6));
                assertThat(shopcart.getTotalPrice(), equalTo(PRODUCT_1_PRICE * 3 + PRODUCT_2_PRICE * 3));
                assertThat(shopcart.getProductList().size(), equalTo(2));
            }
        });
    }
}