package polarbear.acceptance.shopcart;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.acceptance.shopcart.util.TestMyShopcartUtil.*;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import polarbear.acceptance.Request.ResultCallback;
import polarbear.acceptance.shopcart.bean.MyShopcart;
import polarbear.acceptance.shopcart.bean.ShopcartProduct;

import com.alibaba.fastjson.TypeReference;
import com.meterware.httpunit.WebResponse;
import com.polarbear.domain.User;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;

public class LoginSynchShopcartDataTest {
    private String USER_LOGIN_COOKIE = "";

    public List<ShopcartProduct> shouldSynchValidateWhenLoginHaveCorrectSynchData(final List<ShopcartProduct> shopcartDataList, ShopcartProduct... shopcartProducts) {
        for (ShopcartProduct shopcartProduct : shopcartProducts) {
            addNewOrUpdateOldShopcartDataList(shopcartDataList, shopcartProduct);
        }
        String shopcartCookieData = convertRequestParamByShopcartProductArray(shopcartProducts);
        anRequest(LOGIN_URL)
            .addParams("uname", UNAME)
            .addParams("password", PWD)
            .withCookie(LoginController.SHOPCART_COOKIE, shopcartCookieData).post(new ResultCallback() {
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
        anRequest(SHOPCART_GET_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, USER_LOGIN_COOKIE)
            .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                MyShopcart shopcart = resultBody(jsonResult, MyShopcart.class);
                assertThatMyShopcart(shopcart, shopcartDataList);
            }
        });
        return shopcartDataList;
    }

}