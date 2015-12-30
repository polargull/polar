package polarbear.acceptance.regist;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static polarbear.acceptance.Request.anRequest;
import static polarbear.test.util.Constants.*;
import static polarbear.test.util.JsonResultConvertUtil.resultState;

import java.io.UnsupportedEncodingException;
import org.junit.Test;
import polarbear.acceptance.Request.ResultCallback;
import com.polarbear.util.JsonResult;
import com.polarbear.web.regist.AppRegisterController;

public class RegistStep2Test {
    @Test
    public void shouldValidateWhenInputCorrectVerifyCodeOnRegistStep2() throws UnsupportedEncodingException {
        System.out.println(RegistStep1Test.RETURN_ENCODE_VERIFY_CODE+" >>> "+RegistStep1Test.RETURN_VERIFY_CODE);
        anRequest(REGIST_STEP2_URL)
        .withCookie(AppRegisterController.ENCODE_VERIFY_CODE, RegistStep1Test.RETURN_ENCODE_VERIFY_CODE)
        .addParams(AppRegisterController.VERIFY_CODE, String.valueOf(RegistStep1Test.RETURN_VERIFY_CODE))
        .addParams("pwd", PWD)
        .post(new ResultCallback() {
            public void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException {
                assertThat("结果状态:", resultState(jsonResult), is(SUCCESS));
            }
        });
    }

}
