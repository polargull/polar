package polarbear.acceptance.shopcart;

import static com.polarbear.util.Constants.ResultState.NEED_LOGIN;
import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.ERR_PRODUCT_ID;
import static polarbear.test.util.Constants.PRODUCT_1_ID;
import static polarbear.test.util.Constants.SHOPCART_ADD_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;

import polarbear.acceptance.Request;
import polarbear.acceptance.Request.ResultCallback;
import polarbear.acceptance.shopcart.bean.ShopcartProduct;

import com.meterware.httpunit.WebResponse;
import com.polarbear.domain.product.Product;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;
import com.polarbear.web.shopcart.ShopcartController;

import static polarbear.acceptance.shopcart.util.TestMyShopcartUtil.*;
public class AddShopcartTest {

    public List<ShopcartProduct> shouldSuccessReturnCookieShopcartNumJsonDataWhenSelectProductAddShopcartAndUserLogined(final List<ShopcartProduct> currentShopcartDataList,
            Product... pickBuyProduct) {
        for (final Product p : pickBuyProduct) {
             anRequest(SHOPCART_ADD_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
            .addParams("pid", String.valueOf(p.getId()))
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    assertThat(jsonResult.getBody().isEmpty(), is(true));
                    addShopcartDataList(currentShopcartDataList, new ShopcartProduct(p, 1));
                }

                public void onResponse(WebResponse response) {                    
                    assertThat("商品总数:", response.getNewCookieValue(ShopcartController.SHOPCART_PRODUCT_NUM), is(String.valueOf(getShopcartTotalNum(currentShopcartDataList))));
                }
            });
        }
        return currentShopcartDataList;
    }

    @Test
    public void shouldReturnNeedLoginFailJsonTipWhenNoLoginAddShopcart() {
        anRequest(SHOPCART_ADD_URL).addParams("pid", String.valueOf(PRODUCT_1_ID)).post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(NEED_LOGIN));
            }
        });
    }

    @Test
    public void shouldReturnParamFailJsonTipWhenInputErrPidOrNoInputPid() {
        testInputPidParam(ERR_PRODUCT_ID);
        testInputPidParam(null);
    }

    private void testInputPidParam(String pid) {
        Request request = anRequest(SHOPCART_ADD_URL).withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3");
        if (pid != null) {
            request.addParams("pid", pid);
        }
        request.post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(PARAM_ERR));
            }
        });
    }
}
