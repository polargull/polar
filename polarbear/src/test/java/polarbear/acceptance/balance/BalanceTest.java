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

import com.polarbear.service.balance.to.BalanceDataTO;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.front.LoginController;

public class BalanceTest {
    @Test
    public void shouldValidateWhenInputCorrectNameAndPwdLogin() {
        anRequest(BALANCE_URL).withCookie(LoginController.USER_LOGIN_COOKIE, "MToxNDUxOTgyNjQzNTQ0OjM1ZWJhMDVjMjY5NTMxNjc5OWM1YmYwM2Q0YTE5N2M3")
            .addParams("pids", "1,2")
            .addParams("nums", "1,1")
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    BalanceDataTO balanceData = resultBody(jsonResult, BalanceDataTO.class);
                    System.out.println(balanceData.getAddress());
                }
            });
    }
}