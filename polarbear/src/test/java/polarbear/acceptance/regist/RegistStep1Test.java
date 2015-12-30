package polarbear.acceptance.regist;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.meterware.httpunit.WebResponse;
import com.polarbear.util.JsonResult;
import com.polarbear.web.regist.AppRegisterController;

public class RegistStep1Test {
    public static int RETURN_VERIFY_CODE;
    public static String RETURN_ENCODE_VERIFY_CODE;

    @Test
    public void shouldReturnVerifyCodeWhenInputCorrectCellphoneOnRegistStep1() throws UnsupportedEncodingException {
        anRequest(REGIST_STEP1_URL)
        .addParams("cellphone", String.valueOf(NEW_CELLPHONE))
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat("结果状态:", resultState(jsonResult), is(SUCCESS));
                assertThat("校验码:", resultBody(jsonResult, AppRegisterController.VERIFY_CODE), notNullValue());
                assertThat("校验码:", Integer.valueOf(resultBody(jsonResult, AppRegisterController.VERIFY_CODE)), allOf(greaterThan(666666), lessThan(888888)));
                RETURN_VERIFY_CODE = Integer.valueOf(resultBody(jsonResult, AppRegisterController.VERIFY_CODE));
            }
            public void onResponse(WebResponse response) {
                assertThat("加密校验码:", response.getNewCookieValue(AppRegisterController.ENCODE_VERIFY_CODE),not(equalTo("")));
                RETURN_ENCODE_VERIFY_CODE = response.getNewCookieValue(AppRegisterController.ENCODE_VERIFY_CODE);
            }
        });
    }

    @Test
    public void shouldInvalidWhenInputErrCellphoneOnRegistStep1() throws UnsupportedEncodingException {
        anRequest(REGIST_STEP1_URL)
        .addParams("cellphone", String.valueOf(ERR_CELLPHONE))
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat("结果状态:", resultState(jsonResult), is(PARAM_ERR));
                assertThat("校验码:", resultBody(jsonResult, AppRegisterController.VERIFY_CODE), nullValue());
                assertThat("校验码:", resultBody(jsonResult, AppRegisterController.VERIFY_CODE), nullValue());
            }
            public void onResponse(WebResponse response) {
                assertThat("加密校验码:", response.getNewCookieValue(AppRegisterController.ENCODE_VERIFY_CODE), nullValue());
            }
        });
    }

}
