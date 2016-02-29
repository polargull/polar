package polarbear.acceptance.order;

import static com.polarbear.util.Constants.ResultState.NEED_LOGIN;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import java.io.UnsupportedEncodingException;
import org.junit.Test;
import polarbear.acceptance.Request.ResultCallback;
import com.polarbear.util.JsonResult;

public class OrderRelationOpLoginValidateTest {

    @Test
    public void shouldReturnNeedLoginFailJsonTipWhenNoLoginInOrderOp() {
        orderOpWhenUserNoLogin(ORDER_CREATE_URL);
        orderOpWhenUserNoLogin(ORDER_CANCLE_URL);
    }

    private void orderOpWhenUserNoLogin(String orderOpReqUrl) {
        anRequest(orderOpReqUrl).post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat("订单操作未登录校验：",resultState(jsonResult), is(NEED_LOGIN));
            }
        });
    }
}