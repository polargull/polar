package polarbear.acceptance.regist;

import static com.polarbear.util.Constants.ResultState.SUCCESS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.CELLPHONE;
import static polarbear.test.util.Constants.REGIST_STEP2_URL;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import polarbear.acceptance.Request.ResultCallback;

import com.polarbear.service.register.util.VerifyCodeEncoder;
import com.polarbear.util.JsonResult;
import com.polarbear.web.regist.AppRegisterController;

public class RegistStep2Test {
    final int VERIFY_CODE = 666888;
    String NEED_COMPARE_VERIFY_CODE_ENCODE = new VerifyCodeEncoder().encodeNeedCompareVerifyCode(VERIFY_CODE, CELLPHONE);
    @Test
    public void shouldValidateWhenInputCorrectVerifyCodeOnRegistStep2() throws UnsupportedEncodingException {
        anRequest(REGIST_STEP2_URL)
        .withCookie(AppRegisterController.ENCODE_VERIFY_CODE, NEED_COMPARE_VERIFY_CODE_ENCODE)
        .addParams(AppRegisterController.VERIFY_CODE, String.valueOf(VERIFY_CODE))
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat("结果状态:", resultState(jsonResult), is(SUCCESS));
            }
        });
    }

}
