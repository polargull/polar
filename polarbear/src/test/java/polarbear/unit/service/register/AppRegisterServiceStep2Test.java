package polarbear.unit.service.register;

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
import com.polarbear.service.register.AppRegisterStep1Service;
import com.polarbear.service.register.AppRegisterStep2Service;

public class AppRegisterServiceStep2Test extends AbstractMock {
    AppRegisterStep2Service appRegisterStep2Service = new AppRegisterStep2Service();
    
    @Before
    public void setUp() {
        setServiceAndDependentComponent(appRegisterStep2Service, "userDao");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRegisterSuccessWhenInputVerificationCodeAndPasswordOnRegisterStep2() throws ValidateException {
        context.checking(new Expectations() {
            {
                allowing(userDao).store(with(any(User.class)));
            }
        });
        LoginData loginData = appRegisterStep2Service.completeStep2(VERIFY_CODE, AppRegisterStep1Service.encodeNeedCompareVerifyCode(VERIFY_CODE, CELLPHONE), PWD);
        assertThat(loginData.getUser().getName(), is(String.valueOf(CELLPHONE)));
    }

    public void shouldThrowExceptionWhenInputVerificationCodeTimeoutOnRegisterStep2() {

    }

    public void shouldThrowExceptionWhenInputVerificationCodeErrOnRegisterStep2() {

    }

    public void shouldThrowExceptionWhenInputPasswordFormatErrOnRegisterStep2() {

    }
}
