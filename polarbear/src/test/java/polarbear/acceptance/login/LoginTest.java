package polarbear.acceptance.login;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.JsonResultConvertUtil.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.xml.sax.SAXException;

import polarbear.acceptance.Request.ResultCallback;
import static polarbear.test.util.Constants.*;

import com.meterware.httpunit.WebResponse;
import com.polarbear.service.login.LoginData;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.UserCookieUtil;

public class LoginTest {
    
    @Test
    public void shouldValidateWhenInputCorrectNameAndPwd() throws MalformedURLException, IOException, SAXException {
        anRequest(LOGIN_URL)
            .addParams("uname", UNAME)
            .addParams("password", PWD)
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    assertThat(resultBody(jsonResult,LoginData.class).getUser().getName(), is(UNAME));
                }
                public void onResponse(WebResponse response) {
                    assertThat(response.getNewCookieValue(UserCookieUtil.COOKIE_NAME),not(nullValue()));
                }
            });
    }
    
    @Test
    public void shouldInValidateWhenInputErrNameAndPwd() throws MalformedURLException, IOException, SAXException {
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
