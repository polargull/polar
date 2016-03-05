package polarbear.acceptance.order;

import static com.polarbear.util.Constants.ORDER_LIST_STATE.*;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.ORDER_MYLIST_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;
import static polarbear.test.util.LoginEncoder.encodeLoginUser;
import static polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory.createUser1;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.PageList;
import polarbear.acceptance.Request.ResultCallback;

import com.alibaba.fastjson.TypeReference;
import com.polarbear.domain.Order;
import com.polarbear.util.JsonResult;
import com.polarbear.util.Constants.ORDER_LIST_STATE;
import com.polarbear.web.login.front.LoginController;

public class MyOrderListTest {
    
    @Test
    public void shouldSuccessWhenQueryMyOrderList() {
        final int ALL_LIST_TOTLAL_NUM = 5;
        final int NEED_PAY_LIST_TOTLAL_NUM = 1;
        final int NEED_RECEIVE_LIST_TOTLAL_NUM = 2;
        final int NEED_COMMENT_TOTLAL_NUM = 1;
        testMyOrderList(ALL, ALL_LIST_TOTLAL_NUM);
        testMyOrderList(NEED_PAY, NEED_PAY_LIST_TOTLAL_NUM);
        testMyOrderList(NEED_RECEIVE, NEED_RECEIVE_LIST_TOTLAL_NUM);
        testMyOrderList(NEED_COMMENT, NEED_COMMENT_TOTLAL_NUM);
    }

    private void testMyOrderList(final ORDER_LIST_STATE state, final int expectListSize) {
        final String PAGE_NO = "1";
        final String PAGE_SIZE = "10";
        anRequest(ORDER_MYLIST_URL)
            .withCookie(LoginController.USER_LOGIN_COOKIE, encodeLoginUser(createUser1()))
            .addParams("orderListState", String.valueOf(state.value()))
            .addParams("pageNo", PAGE_NO)
            .addParams("pageSize", PAGE_SIZE)
            .post(new ResultCallback() {
                    public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
                        assertThat(resultState(jsonResult), is(SUCCESS));
                        assertThat("我的订单列表"+state.name()+"记录数:", resultBody(jsonResult, new TypeReference<PageList<Order>>() {
                        }).getList().size(), equalTo(expectListSize));
                    }
                });
    }

}