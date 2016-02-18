package polarbear.acceptance.balance;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.BALANCE_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;
import polarbear.testdata.acceptance.testdata.product.ProductTestDataFactory;

import com.polarbear.service.balance.to.BalanceDataTO;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;

public class BalanceTest {
    @Test
    public void shouldValidateWhenInputCorrectNameAndPwdLogin() {
        String pids = ProductTestDataFactory.createProduct1().getId() + "," + ProductTestDataFactory.createProduct2().getId();
        String nums = "1,1";
        anRequest(BALANCE_URL)
                .withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
                .addParams("pids", pids)
                .addParams("nums", nums)
                .post(new ResultCallback() {
                    public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
                        assertThat(resultState(jsonResult), is(SUCCESS));
                        BalanceDataTO balanceData = resultBody(jsonResult, BalanceDataTO.class);
                        assertThat(balanceData.getAddress(), nullValue());
                        assertThat(balanceData.getLogisticPrice(), equalTo(0d));
                    }
        });
    }
}