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

public class RemoveProductFromShopcartTest {

    @Test
    public void shouldSuccessReturnShopcartDataWhenSelectProductAddShopcartAndUserLogined() {
        anRequest(SHOPCART_REMOVE_PRODUCT_URL).withCookie(UserCookieUtil.COOKIE_NAME, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
        .addParams("pid",String.valueOf(PRODUCT_1_ID))
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                MyShopcart shopcart = resultBody(jsonResult, MyShopcart.class);
                assertThat(shopcart.getProductNum(), equalTo(ModifyProductNumTest.PRODUCT_2_ALREADY_BUY_NUM));
                assertThat(shopcart.getTotalPrice(), equalTo(ModifyProductNumTest.PRODUCT_2_ALREADY_BUY_NUM * PRODUCT_2_PRICE));
                assertThat(shopcart.getProductList().size(), equalTo(1));
            }
        });
    }
}