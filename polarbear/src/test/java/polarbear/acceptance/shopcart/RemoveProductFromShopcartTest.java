package polarbear.acceptance.shopcart;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.PRODUCT_1_ID;
import static polarbear.test.util.Constants.PRODUCT_1_PRICE;
import static polarbear.test.util.Constants.PRODUCT_2_PRICE;
import static polarbear.test.util.Constants.PRODUCT_BUY_NUM;
import static polarbear.test.util.Constants.SHOPCART_REMOVE_PRODUCT_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct1;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct2;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createSalePrice6Product3;

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
                assertThat(shopcart.getProductNum(), equalTo(5));
                assertThat(shopcart.getTotalPrice(), equalTo((createProduct2().getRealPrice() * 3 + createSalePrice6Product3().getRealPrice() * 3)));
                assertThat(shopcart.getProductList().size(), equalTo(2));
            }
        });
    }
}