package polarbear.acceptance.order;

import static com.polarbear.util.Constants.ResultState.SUCCESS;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.*;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.test.util.LoginEncoder.encodeLoginUser;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.createUser1_2ProductUnpayOrder1;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.polarbear.domain.Order;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;
import static com.polarbear.util.Constants.ORDER_STATE.*;

public class CancleOrderTest {
    @Test
    public void shouldSuccessWhenCancleOrder() {
        final Order cancleOrder = createUser1_2ProductUnpayOrder1();
        anRequest(ORDER_CANCLE_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, encodeLoginUser(createUser1()))
            .addParams("orderId", cancleOrder.getId().toString())
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    assertOrder(cancleOrder.getId().toString());
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
                    assertThatOrder(actOrder, expectOrder1(CANCLE));
                }
            });
    }
}