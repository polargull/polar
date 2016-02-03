package polarbear.acceptance.shopcart;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.PRODUCT_1_ID;
import static polarbear.test.util.Constants.PRODUCT_2_PRICE;
import static polarbear.test.util.Constants.SHOPCART_REMOVE_PRODUCT_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.polarbear.service.shopcart.MyShopcart;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;

public class RemoveProductFromShopcartTest {

    @Test
    public void shouldSuccessReturnShopcartDataWhenSelectProductAddShopcartAndUserLogined() {
        anRequest(SHOPCART_REMOVE_PRODUCT_URL).withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
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