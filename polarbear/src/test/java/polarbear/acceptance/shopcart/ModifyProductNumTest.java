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

public class ModifyProductNumTest {
    public static final int PRODUCT_2_ALREADY_BUY_NUM = 1;
    @Test
    public void shouldReturnMyShopcartDataWhenModifyProductNumAndUserLogined() {
        anRequest(SHOPCART_MODIFY_PRODUCT_NUM_URL).withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
        .addParams("pid", String.valueOf(PRODUCT_1_ID))
        .addParams("num", String.valueOf(PRODUCT_BUY_NUM))
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                MyShopcart shopcart = resultBody(jsonResult, MyShopcart.class);
                assertThat(shopcart.getProductNum(), equalTo(PRODUCT_BUY_NUM + PRODUCT_2_ALREADY_BUY_NUM));
                assertThat(shopcart.getTotalPrice(), equalTo(PRODUCT_1_PRICE * PRODUCT_BUY_NUM + PRODUCT_2_PRICE * PRODUCT_2_ALREADY_BUY_NUM));
                assertThat(shopcart.getProductList().size(), equalTo(2));
            }
        });
    }
}