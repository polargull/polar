package polarbear.acceptance.back.login;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.ADMIN_LOGIN_URL;
import static polarbear.test.util.Constants.ADMIN_UNAME;
import static polarbear.test.util.Constants.ERROR_PWD;
import static polarbear.test.util.Constants.ERROR_UNAME;
import static polarbear.test.util.Constants.PWD;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.alibaba.fastjson.TypeReference;
import com.meterware.httpunit.WebResponse;
import com.polarbear.domain.Admin;
import com.polarbear.service.login.LoginData;
import com.polarbear.util.JsonResult;
import com.polarbear.web.login.back.LoginController;

public class AdminLoginTest {
    
    @Test
    public void shouldValidateWhenInputCorrectAdminNameAndPwdLogin() {
        anRequest(ADMIN_LOGIN_URL)
            .addParams("uname", ADMIN_UNAME)
            .addParams("password", PWD)
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException, ClassNotFoundException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    assertThat(resultBody(jsonResult, new TypeReference<LoginData<Admin>>(){}).getUser().getName(), is(ADMIN_UNAME));
                }
                public void onResponse(WebResponse response) {
                    assertThat(response.getNewCookieValue(LoginController.ADMIN_LOGIN_COOKIE),not(nullValue()));
                }
            });
    }
    
    @Test
    public void shouldInValidateWhenInputErrAdminNameAndPwdLogin() {
        anRequest(ADMIN_LOGIN_URL)
            .addParams("uname", ERROR_UNAME)
            .addParams("password", ERROR_PWD)
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(LOGIN_NAME_PWD_ERR));
                    assertThat(resultBody(jsonResult,LoginData.class), nullValue());
                }
                public void onResponse(WebResponse response) {
                    assertThat(response.getNewCookieValue(LoginController.ADMIN_LOGIN_COOKIE),nullValue());
                }
            });
    }
    
}
