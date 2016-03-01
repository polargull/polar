package polarbear.acceptance.order;

import static com.polarbear.util.Constants.ORDER_STATE.*;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectCreateOrder;
import static polarbear.integration.service.order.factory.OrderParamFactory.createUser1ImmedidateBuyProduct1OrderParam;
import static polarbear.test.util.Constants.ORDER_CREATE_URL;
import static polarbear.test.util.Constants.ORDER_DETAIL_URL;
import static polarbear.test.util.Constants.ORDER_PAY_URL;
import static polarbear.test.util.Constants.ORDER_TOPAY_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.test.util.LoginEncoder.encodeLoginUser;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.polarbear.domain.Order;
import com.polarbear.service.order.bean.OrderParam;
import com.polarbear.util.JsonResult;
import com.polarbear.util.convert.Arrays;
import com.polarbear.web.login.front.LoginController;
public class PayOrderTest {
    
    @Test
    public void shouldSuccessWhenToPayAndPayOrder() {
        OrderParam user1OrderParam = createUser1ImmedidateBuyProduct1OrderParam().build();
        anRequest(ORDER_CREATE_URL)
        .withCookie(LoginController.USER_LOGIN_COOKIE, encodeLoginUser(createUser1()))
        .addParams("pids", Arrays.toString(user1OrderParam.getPids()))
        .addParams("nums", Arrays.toString(user1OrderParam.getNums()))
        .addParams("buyMode", String.valueOf(user1OrderParam.getBuyMode()))
        .addParams("payCode", String.valueOf(user1OrderParam.getPayCode()))
        .addParams("addressId", String.valueOf(user1OrderParam.getAddressId()))
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                Order newOrder = resultBody(jsonResult, Order.class);
                testToPayAndPayOrder(newOrder.getId());
            }
        });
    }
    
    public void testToPayAndPayOrder(final long orderId) {
        anRequest(ORDER_TOPAY_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, encodeLoginUser(createUser1()))
            .addParams("orderId", String.valueOf(orderId))
            .addParams("payPlatform", "1")
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    shouldSuccessWhenPayOrder(orderId);
                }
        });
    }
    
    private void shouldSuccessWhenPayOrder(final long orderId) {
        anRequest(ORDER_PAY_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, encodeLoginUser(createUser1()))
            .addParams("orderId", String.valueOf(orderId))
            .addParams("threePartId", "100000001")
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    assertOrder(String.valueOf(orderId));
                }
        });
    }

    private void assertOrder(String orderId) {
        anRequest(ORDER_DETAIL_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
            .addParams("orderId", orderId)
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    Order actOrder = resultBody(jsonResult, Order.class);
                    assertThatOrder(actOrder, expectCreateOrder(createUser1ImmedidateBuyProduct1OrderParam(), PAYED));
                }
            });
    }
}
