package polarbear.acceptance.login;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.LOGIN_URL;
import static polarbear.test.util.Constants.PWD;
import static polarbear.test.util.Constants.SHOPCART_DATA_USER1_LIST;
import static polarbear.test.util.Constants.SHOPCART_GET_URL;
import static polarbear.test.util.Constants.UNAME;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct1;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct2;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createSalePrice6Product3;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.alibaba.fastjson.TypeReference;
import com.meterware.httpunit.WebResponse;
import com.polarbear.domain.User;
import com.polarbear.domain.product.Product;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.service.shopcart.MyShopcart;
import com.polarbear.service.shopcart.ShopcartProduct;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;

public class LoginSynchShopcartDataTest {
    private String USER_LOGIN_COOKIE = "";

    @Test
    public void shouldSynchValidateWhenLoginHaveCorrectSynchData() {
        Product p1 = createProduct1();
        Product p2 = createProduct2();
        Product p3 = createSalePrice6Product3();
        SHOPCART_DATA_USER1_LIST.add(new ShopcartProduct(p1, 1));
        SHOPCART_DATA_USER1_LIST.add(new ShopcartProduct(p2, 2));
        SHOPCART_DATA_USER1_LIST.add(new ShopcartProduct(p3, 3));
        anRequest(LOGIN_URL).addParams("uname", UNAME).addParams("password", PWD).withCookie(LoginController.SHOPCART_COOKIE, "1_1|2_2|3_3").post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                assertThat(resultBody(jsonResult, new TypeReference<LoginData<User>>() {
                }).getUser().getName(), is(UNAME));
            }

            public void onResponse(WebResponse response) {
                USER_LOGIN_COOKIE = response.getNewCookieValue(LoginController.USER_LOGIN_COOKIE);
                assertThat(response.getNewCookieValue(LoginController.USER_LOGIN_COOKIE), not(nullValue()));
            }
        });
        anRequest(SHOPCART_GET_URL).withCookie(LoginController.USER_LOGIN_COOKIE, USER_LOGIN_COOKIE).post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                MyShopcart shopcart = resultBody(jsonResult, MyShopcart.class);
                assertThat(shopcart.getProductNum(), equalTo(getUser1ShopcartTotalNum()));
                assertThat(shopcart.getTotalPrice(), equalTo(getUser1ShopcartTotalPrice()));
                assertThat(shopcart.getProductList().size(), equalTo(SHOPCART_DATA_USER1_LIST.size()));
            }
        });
    }

    private int getUser1ShopcartTotalNum() {
        int totalNum = 0;
        for (ShopcartProduct sp:SHOPCART_DATA_USER1_LIST) {
            totalNum += sp.getNum();
        }
        return totalNum;
    }

    private double getUser1ShopcartTotalPrice() {
        double totalPrice = 0;
        for (ShopcartProduct sp:SHOPCART_DATA_USER1_LIST) {
            totalPrice += sp.getProduct().getRealPrice();
        }
        return totalPrice;
    }
}