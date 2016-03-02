package polarbear.acceptance.order;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectOrder2;
import static polarbear.test.util.Constants.ORDER_DETAIL_URL;
import static polarbear.test.util.Constants.ORDER_SIGN_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.test.util.LoginEncoder.encodeLoginUser;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.*;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.polarbear.domain.Order;
import com.polarbear.util.JsonResult;
import com.polarbear.util.Constants.ORDER_STATE;
import com.polarbear.web.login.front.LoginController;
public class SignOrderTest {

    @Test
    public void shouldSuccessWhenSignOrder() {
        final String signOrderId = createUser1_2ProductDeliveryOrder2().getId().toString(); 
        anRequest(ORDER_SIGN_URL)
        .withCookie(LoginController.USER_LOGIN_COOKIE, encodeLoginUser(createUser1()))
        .addParams("orderId", signOrderId)
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                assertOrder(signOrderId);
            }
        });
    }
    
    @Test
    public void shouldFailWhenSignOrderInCancleState() {
        final String signOrderId = createUser1_2ProductUnpayOrder1().getId().toString(); 
        anRequest(ORDER_SIGN_URL)
        .withCookie(LoginController.USER_LOGIN_COOKIE, encodeLoginUser(createUser1()))
        .addParams("orderId", signOrderId)
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(ORDER_OPREATE_ERR));
            }
        });
    }

    private void assertOrder(String orderId) {
        anRequest(ORDER_DETAIL_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, encodeLoginUser(createUser1()))
            .addParams("orderId", orderId)
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    Order actOrder = resultBody(jsonResult, Order.class);
                    assertThatOrder(actOrder, expectOrder2(ORDER_STATE.SUCCESS));
                }
            });
    }
}
