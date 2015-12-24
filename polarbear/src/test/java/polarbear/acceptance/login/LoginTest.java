package polarbear.acceptance.login;

import static com.polarbear.util.Constants.ResultState.LOGIN_NAME_PWD_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import polarbear.acceptance.AbstractAcceptanceTest;
import polarbear.acceptance.Request.ResultCallback;
import polarbear.test.util.Constants;

import com.meterware.httpunit.WebResponse;
import com.polarbear.service.login.LoginData;
import com.polarbear.util.JsonResult;
import com.polarbear.util.cookie.UserCookieUtil;

public class LoginTest extends AbstractAcceptanceTest {
    
    final String CORRECT_UNAME = "极地鸥";
    final String CORRECT_PWD = "123456";
    
    final String ERROR_UNAME = "test@123.com";
    final String ERROR_PWD = "123abc";

    @Before
    public void setUp() throws Exception {
        super.setUp(Constants.LOGIN_URL);
    }

    @Test
    public void shouldValidateWhenInputCorrectNameAndPwd() throws MalformedURLException, IOException, SAXException {
        anRequest(url)
            .addParams("uname", CORRECT_UNAME)
            .addParams("password", CORRECT_PWD)
            .post(new ResultCallback() {
                public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                    assertThat(resultState(jsonResult), is(SUCCESS));
                    assertThat(resultBody(jsonResult,LoginData.class).getUser().getName(), is(CORRECT_UNAME));
                }
                public void onResponse(WebResponse response) {
                    assertThat(response.getNewCookieValue(UserCookieUtil.COOKIE_NAME),not(nullValue()));
                }
            });
    }
    
    @Test
    public void shouldInValidateWhenInputErrNameAndPwd() throws MalformedURLException, IOException, SAXException {
        anRequest(url)
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
