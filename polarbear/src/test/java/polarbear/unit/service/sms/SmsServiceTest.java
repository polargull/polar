package polarbear.unit.service.sms;

import static org.junit.Assert.*;
import static com.polarbear.util.Constants.ResultState.*;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.service.RemoteInvokeServiceException;
import com.polarbear.service.sms.SmsService;
import com.polarbear.service.sms.ThirdSmsServiceApi;
import com.polarbear.service.sms.bean.SmsMessage;

public class SmsServiceTest extends AbstractMock {
    SmsService smsService = new SmsService();
    public ThirdSmsServiceApi thirdSmsServiceApi;

    @Before
    public void setUp() {
        setServiceAndDependentComponent(smsService, "thirdSmsServiceApi");
    }

    @Test
    public void shouldNotReturnValueWhenSendSuccess() throws RemoteInvokeServiceException {
        context.checking(new Expectations() {
            {
                oneOf(thirdSmsServiceApi).send(with(any(String[].class)), with(any(String.class)));
                will(returnValue(1));
            }
        });
        try {
            smsService.send(new String[] { "13717686218" }, new SmsMessage(9654899, "测试码...."));
        } catch (Exception e) {
            fail("短信发送服务应该成功，可结果报异常了！");
            return;
        }
        assertTrue("短信发送服务应该成功", true);
    }

    @Test
    public void shouldThrowExceptionWhenSendSmsFail() throws RemoteInvokeServiceException {
        context.checking(new Expectations() {
            {
                oneOf(thirdSmsServiceApi).send(with(any(String[].class)), with(any(String.class)));
                will(returnValue(0));
            }
        });
        expectedEx.expect(RemoteInvokeServiceException.class);
        expectedEx.expectMessage(SMS_SEND_FAIL.emsg());
        smsService.send(new String[] { "13717686218" }, new SmsMessage(9654899, "测试码...."));
    }
}
