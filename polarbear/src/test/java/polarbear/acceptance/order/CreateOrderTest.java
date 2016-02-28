package polarbear.acceptance.order;

import static com.polarbear.util.Constants.ResultState.NEED_LOGIN;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.integration.service.order.factory.OrderParamFactory.createUser1ImmedidateBuyProduct1OrderParam;
import static polarbear.test.util.Constants.ORDER_CREATE_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory.createProduct1;
import static polarbear.integration.service.order.assertutil.AssertUtil.*;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.*;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.polarbear.domain.Order;
import com.polarbear.service.balance.to.BuyProduct;
import com.polarbear.service.order.bean.OrderParam;
import com.polarbear.util.JsonResult;
import com.polarbear.util.convert.Arrays;
import com.polarbear.web.login.front.LoginController;

public class CreateOrderTest {
    final BuyProduct[] BUY_SINGAL_PRODUCT = new BuyProduct[] { new BuyProduct(createProduct1(), 1) };

    @Test
    public void shouldReturnOrderWhenImmediateBuyCreateOrder() {
        OrderParam user1OrderParam = createUser1ImmedidateBuyProduct1OrderParam().build();
        anRequest(ORDER_CREATE_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
            .addParams("pids", Arrays.toString(user1OrderParam.getPids()))
            .addParams("nums", Arrays.toString(user1OrderParam.getNums()))
            .addParams("buyMode", String.valueOf(user1OrderParam.getBuyMode()))
            .addParams("payCode", String.valueOf(user1OrderParam.getPayCode()))
            .addParams("addressId", String.valueOf(user1OrderParam.getAddressId()))
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    Order actOrder = resultBody(jsonResult, Order.class);
                    assertThatNewOrder(actOrder, expectCreateOrder(createUser1ImmedidateBuyProduct1OrderParam()));
                }
        });
    }

    @Test
    public void shouldReturnNeedLoginFailJsonTipWhenNoLoginCreateOrder() {
        anRequest(ORDER_CREATE_URL).post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(NEED_LOGIN));
            }
        });
    }
}