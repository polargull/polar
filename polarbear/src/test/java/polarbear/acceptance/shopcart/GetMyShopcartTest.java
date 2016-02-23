package polarbear.acceptance.shopcart;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.acceptance.shopcart.TestMyShopcartUtil.assertThatMyShopcart;
import static polarbear.acceptance.shopcart.TestMyShopcartUtil.convertRequestParamByShopcartProductArray;
import static polarbear.test.util.Constants.PRODUCT_1_PRICE;
import static polarbear.test.util.Constants.PRODUCT_2_PRICE;
import static polarbear.test.util.Constants.SHOPCART_GET_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct1;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct2;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createSalePrice6Product3;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;
import polarbear.acceptance.shopcart.bean.MyShopcart;
import polarbear.acceptance.shopcart.bean.ShopcartProduct;

import com.polarbear.domain.product.Product;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;
import com.polarbear.web.shopcart.ShopcartController;

public class GetMyShopcartTest {

    public void shouldReturnMyShopcartEmptyDataWhenQueryShopcartAndUserFirstLogin() {
        anRequest(SHOPCART_GET_URL)
        .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                MyShopcart shopcart = resultBody(jsonResult, MyShopcart.class);
                assertThat(shopcart.getProductNum(), equalTo(0));
                assertThat(shopcart.getTotalPrice(), equalTo(0d));
                assertThat(shopcart.getProductList().size(), equalTo(0));
            }
        });
    }
    
    public void shouldReturnMyShopcartDataWhenQueryShopcartAndUserLogined(final List<ShopcartProduct> shopcartDataList) {
        anRequest(SHOPCART_GET_URL)
        .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                MyShopcart shopcart = resultBody(jsonResult, MyShopcart.class);
                assertThatMyShopcart(shopcart, shopcartDataList);
            }
        });
    }
    
    @Test
    public void shouldReturnMyShopcartDataWhenQueryShopcartAndUserNoLogin() {
        Product pickProduct1 = createProduct1();
        Product pickProduct2 = createProduct2();
        ShopcartProduct buyProduct1 = new ShopcartProduct(pickProduct1, 1);
        ShopcartProduct buyProduct2 = new ShopcartProduct(pickProduct2, 2);
        String shopcartCookieData =convertRequestParamByShopcartProductArray(new ShopcartProduct[]{buyProduct1,buyProduct2});
        
        anRequest(SHOPCART_GET_URL)
        .withCookie(ShopcartController.SHOPCART, shopcartCookieData)
        .post(new ResultCallback() {
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