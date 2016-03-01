package polarbear.acceptance.order;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.integration.service.order.assertutil.AssertUtil.assertThatOrder;
import static polarbear.integration.service.order.factory.ExpectOrderFactory.expectCreateOrder;
import static polarbear.integration.service.order.factory.OrderParamFactory.createUser1ImmedidateBuyProduct1OrderParam;
import static polarbear.integration.service.order.factory.OrderParamFactory.createUser2ShopcartProduct1And3OrderParam;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.*;
import static polarbear.test.util.Constants.ORDER_CREATE_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.test.util.LoginEncoder.*;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.polarbear.domain.Order;
import com.polarbear.service.order.bean.OrderParam;
import com.polarbear.util.JsonResult;
import com.polarbear.util.convert.Arrays;
import com.polarbear.web.login.front.LoginController;
import static com.polarbear.util.Constants.ORDER_STATE.UNPAY;
public class CreateOrderTest {

    @Test
    public void shouldReturnOrderWhenImmediateBuyCreateOrder() {
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
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    Order actOrder = resultBody(jsonResult, Order.class);
                    assertThatOrder(actOrder, expectCreateOrder(createUser1ImmedidateBuyProduct1OrderParam(),UNPAY));
                }
        });
    }
    
    @Test
    public void shouldReturnOrderWhenShopcartBuyCreateOrder() {
        OrderParam user2OrderParam = createUser2ShopcartProduct1And3OrderParam().build();
        anRequest(ORDER_CREATE_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, encodeLoginUser(createUser2()))
            .addParams("pids", Arrays.toString(user2OrderParam.getPids()))
            .addParams("nums", Arrays.toString(user2OrderParam.getNums()))
            .addParams("buyMode", String.valueOf(user2OrderParam.getBuyMode()))
            .addParams("payCode", String.valueOf(user2OrderParam.getPayCode()))
            .addParams("addressId", String.valueOf(user2OrderParam.getAddressId()))
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    Order actOrder = resultBody(jsonResult, Order.class);
                    assertThatOrder(actOrder, expectCreateOrder(createUser2ShopcartProduct1And3OrderParam(),UNPAY));
                }
        });
    }

}