package polarbear.unit.service.register;

import static com.polarbear.util.Constants.ResultState.*;
import static org.hamcrest.Matchers.*;
import static polarbear.test.util.Constants.*;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import polarbear.unit.service.AbstractMock;

import com.polarbear.ValidateException;
import com.polarbear.domain.User;
import com.polarbear.service.login.LoginData;
import com.polarbear.service.register.AppRegisterStep2Service;
import com.polarbear.service.register.util.VerifyCodeEncoder;
import com.polarbear.util.date.ExpiryClock;

public class AppRegisterServiceStep2Test extends AbstractMock {
    AppRegisterStep2Service appRegisterStep2Service = new AppRegisterStep2Service();
    String NEED_COMPARE_VERIFY_CODE_ENCODE = new VerifyCodeEncoder().encodeNeedCompareVerifyCode(VERIFY_CODE, CELLPHONE);
    String EXPIRY_VERIFY_CODE_ENCODE; 
        
    @Before
    public void setUp() {
        setServiceAndDependentComponent(appRegisterStep2Service, "userDao");
        EXPIRY_VERIFY_CODE_ENCODE = new VerifyCodeEncoder().setClock(new ExpiryClock().plusMinutes(-1)).encodeNeedCompareVerifyCode(VERIFY_CODE, CELLPHONE);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRegisterSuccessWhenInputVerificationCodeAndPasswordOnRegisterStep2() throws ValidateException {
        context.checking(new Expectations() {
            {
                allowing(userDao).store(with(any(User.class)));
            }
        });
        LoginData loginData = appRegisterStep2Service.completeStep2(VERIFY_CODE, NEED_COMPARE_VERIFY_CODE_ENCODE, PWD);
        assertThat(loginData.getUser().getName(), is(String.valueOf(CELLPHONE)));
    }
    
    @Test
    public void shouldThrowExceptionWhenInputVerificationCodeTimeoutOnRegisterStep2() throws ValidateException {
        expectedEx.expect(ValidateException.class);
        expectedEx.expectMessage(VERIFY_CODE_INVIDIT.emsg());
        appRegisterStep2Service.completeStep2(VERIFY_CODE, EXPIRY_VERIFY_CODE_ENCODE, PWD);
    }

    @Test
    public void shouldThrowExceptionWhenInputVerificationCodeErrOnRegisterStep2() throws ValidateException {
        expectedEx.expect(ValidateException.class);
        expectedEx.expectMessage(PARAM_ERR.emsg());
        appRegisterStep2Service.completeStep2(ERR_VERIFY_CODE, NEED_COMPARE_VERIFY_CODE_ENCODE, PWD);
    }
    
    public void shouldThrowExceptionWhenCellphoneErrOnRegisterStep2() {
//        expectedEx.expect(RemoteInvokeServiceException.class);
//        expectedEx.expectMessage(SMS_SEND_FAIL.emsg());
//        appRegisterStep2Service.completeStep1(CELLPHONE);
    }

}
