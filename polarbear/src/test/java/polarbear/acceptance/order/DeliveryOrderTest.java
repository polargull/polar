package polarbear.acceptance.order;

import static com.polarbear.util.Constants.ResultState.ORDER_OPREATE_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectOrder3;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.test.util.LoginEncoder.encodeLoginUser;
import static polarbear.testdata.acceptance.testdata.AdminAcceptanceTestDataFactory.createAdmin;
import static polarbear.testdata.acceptance.testdata.OrderAcceptanceTestDataFactory.*;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.polarbear.domain.Order;
import com.polarbear.util.JsonResult;
import com.polarbear.util.Constants.ORDER_STATE;
import com.polarbear.web.login.back.LoginController;

public class DeliveryOrderTest {
    final String COMPANY = "申通";
    final String LOGISTIC_ORDER_ID = "st1056";

    @Test
    public void shouldSuccessWhenDeliveryOrder() {
        final String PAYED_ORDER_ID = createUser1_2ProductPayOrder3().getId().toString(); 
        anRequest(ORDER_DELIVERY_URL)
        .withCookie(LoginController.ADMIN_LOGIN_COOKIE, encodeLoginUser(createAdmin()))
        .addParams("orderId", PAYED_ORDER_ID)
        .addParams("companyName", COMPANY)
        .addParams("logisticsOrderIds", LOGISTIC_ORDER_ID)
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(SUCCESS));
                assertOrder(PAYED_ORDER_ID);
            }
        });
    }
    
    @Test
    public void shouldFailWhenSignOrderInCancleState() {
        final String UNPAY_ORDER_ID = createUser1_2ProductUnpayOrder1().getId().toString(); 
        anRequest(ORDER_DELIVERY_URL)
        .withCookie(LoginController.ADMIN_LOGIN_COOKIE, encodeLoginUser(createAdmin()))
        .addParams("orderId", UNPAY_ORDER_ID)
        .addParams("companyName", COMPANY)
        .addParams("logisticsOrderIds", LOGISTIC_ORDER_ID)
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat(resultState(jsonResult), is(ORDER_OPREATE_ERR));
            }
        });
    }

    private void assertOrder(String orderId) {
        anRequest(ORDER_DETAIL_URL)
            .withCookie("Login_User", encodeLoginUser(createUser1()))
            .addParams("orderId", orderId)
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    Order actOrder = resultBody(jsonResult, Order.class);
                    assertThatOrder(actOrder, expectOrder3(ORDER_STATE.DELIVERY));
                }
            });
    }
}
