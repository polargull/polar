package polarbear.acceptance.regist;

import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.REGIST_STEP1_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultBody;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.meterware.httpunit.WebResponse;
import com.polarbear.util.JsonResult;
import com.polarbear.web.regist.AppRegisterController;

public class RegistStep1Test {
    final String CELLPHONE = "13717686218";
    final String ERR_CELLPHONE = "137176862";

    @Test
    public void shouldReturnVerifyCodeWhenInputCorrectCellphoneOnRegistStep1() throws UnsupportedEncodingException {
        anRequest(REGIST_STEP1_URL)
        .addParams("cellphone", CELLPHONE)
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat("结果状态:", resultState(jsonResult), is(SUCCESS));
                assertThat("校验码:", resultBody(jsonResult, AppRegisterController.VERIFY_CODE), notNullValue());
                assertThat("校验码:", Integer.valueOf(resultBody(jsonResult, AppRegisterController.VERIFY_CODE)), allOf(greaterThan(666666), lessThan(888888)));
            }
            public void onResponse(WebResponse response) {
                assertThat("加密校验码:", response.getNewCookieValue(AppRegisterController.ENCODE_VERIFY_CODE),not(nullValue()));
            }
        });
    }

    @Test
    public void shouldInvalidWhenInputErrCellphoneOnRegistStep1() throws UnsupportedEncodingException {
        anRequest(REGIST_STEP1_URL)
        .addParams("cellphone", ERR_CELLPHONE)
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
