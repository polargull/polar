package polarbear.unit.service.register;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.polarbear.util.Constants.ResultState.*;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import static polarbear.test.util.Constants.*;
import polarbear.unit.service.AbstractMock;

import com.polarbear.service.RemoteInvokeServiceException;
import com.polarbear.service.register.AppRegisterStep1Service;
import com.polarbear.service.sms.SmsService;
import com.polarbear.service.sms.bean.SmsMessage;
import com.polarbear.util.RandomUtil;
import com.polarbear.web.regist.bean.ReturnVerifyCode;

public class AppRegisterServiceStep1Test extends AbstractMock {

    public SmsService smsService;
    AppRegisterStep1Service appRegisterService = new AppRegisterStep1Service();
    int randomNum = RandomUtil.getRegisterVerificationCode();

    @Before
    public void setUp() {
        setServiceAndDependentComponent(appRegisterService, "smsService");
    }

    @Test
    public void shouldReturn6BitRandomVerificationCodeWhenInputCellphoneOnRegisterStep1() throws RemoteInvokeServiceException {
        context.checking(new Expectations() {
            {
                oneOf(smsService).send(with(any(String[].class)), with(any(SmsMessage.class)));
            }
        });
        ReturnVerifyCode returnVerifyCode = appRegisterService.completeStep1(CELLPHONE);
        assertThat(returnVerifyCode.getVerifyCode(), allOf(greaterThan(666666), lessThan(888888)));
    }

    @Test
    public void shouldThrowExceptionWhenSmsServiceFailOnRegisterStep1() throws RemoteInvokeServiceException {
        context.checking(new Expectations() {
            {
                oneOf(smsService).send(with(any(String[].class)), with(any(SmsMessage.class)));
                will(throwException(new RemoteInvokeServiceException(SMS_SEND_FAIL)));
            }
        });
        expectedEx.expect(RemoteInvokeServiceException.class);
        expectedEx.expectMessage(SMS_SEND_FAIL.emsg());
        appRegisterService.completeStep1(CELLPHONE);
    }

}
