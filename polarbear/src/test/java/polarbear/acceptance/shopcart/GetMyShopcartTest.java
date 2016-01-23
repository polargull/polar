package polarbear.acceptance.shopcart;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.SHOPCART_GET_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.polarbear.service.shopcart.MyShopcart;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.UserCookieUtil;

public class GetMyShopcartTest {

    @Test
    public void shouldReturnMyShopcartDataWhenQueryShopcartAndUserLogined() {
        anRequest(SHOPCART_GET_URL).withCookie(UserCookieUtil.COOKIE_NAME, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3").post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                MyShopcart shopcart = resultBody(jsonResult, MyShopcart.class);
                assertThat(shopcart.getProductNum(), equalTo(1));
                assertThat(shopcart.getTotalPrice(), equalTo(100d));
                assertThat(shopcart.getProductList().size(), equalTo(1));
            }
        });
    }
}