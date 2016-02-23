package polarbear.acceptance.shopcart;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.acceptance.shopcart.TestMyShopcartUtil.*;
import static polarbear.test.util.Constants.SHOPCART_REMOVE_PRODUCT_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;
import java.util.List;

import polarbear.acceptance.Request.ResultCallback;
import polarbear.acceptance.shopcart.bean.MyShopcart;
import polarbear.acceptance.shopcart.bean.ShopcartProduct;

import com.polarbear.domain.product.Product;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;

public class RemoveProductFromShopcartTest {

    public List<ShopcartProduct> shouldSuccessReturnShopcartDataWhenRemoveProductInShopcartAndUserLogined(final List<ShopcartProduct> shopcartDataList,
            Product... pickProduct) {
        for (final Product p:pickProduct) {
            anRequest(SHOPCART_REMOVE_PRODUCT_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
            .addParams("pid",String.valueOf(p.getId()))
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    MyShopcart shopcart = resultBody(jsonResult, MyShopcart.class);
                    removeProductInShopcartDataList(shopcartDataList, p);
                    assertThatMyShopcart(shopcart, shopcartDataList);
                }
            });
        }
        return shopcartDataList;
    }
}