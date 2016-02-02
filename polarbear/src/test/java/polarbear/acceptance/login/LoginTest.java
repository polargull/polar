package polarbear.acceptance.login;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.ERROR_PWD;
import static polarbear.test.util.Constants.ERROR_UNAME;
import static polarbear.test.util.Constants.LOGIN_URL;
import static polarbear.test.util.Constants.PWD;
import static polarbear.test.util.Constants.UNAME;
import static polarbear.test.util.JsonResultConvertUtil.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.alibaba.fastjson.TypeReference;
import com.meterware.httpunit.WebResponse;
import com.polarbear.domain.User;
import com.polarbear.service.login.LoginData;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.UserCookieUtil;

public class LoginTest {
    
    @Test
    public void shouldValidateWhenInputCorrectNameAndPwdLogin() {
        anRequest(LOGIN_URL)
            .addParams("uname", UNAME)
            .addParams("password", PWD)
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    assertThat(resultBody(jsonResult, new TypeReference<LoginData<User>>(){}).getUser().getName(), is(UNAME));
                }
                public void onResponse(WebResponse response) {
                    assertThat(response.getNewCookieValue(UserCookieUtil.COOKIE_NAME),not(nullValue()));
                }
            });
    }
    
    @Test
    public void shouldInValidateWhenInputErrNameAndPwdLogin() {
        anRequest(LOGIN_URL)
            .addParams("uname", ERROR_UNAME)
            .addParams("password", ERROR_PWD)
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(LOGIN_NAME_PWD_ERR));
                    assertThat(resultBody(jsonResult,LoginData.class), nullValue());
                }
                public void onResponse(WebResponse response) {
                    assertThat(response.getNewCookieValue(UserCookieUtil.COOKIE_NAME),nullValue());
                }
            });
    }
    
}
