package polarbear.unit.service.register;

import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.VERIFY_CODE_INVIDIT;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.CELLPHONE;
import static polarbear.test.util.Constants.ERR_VERIFY_CODE;
import static polarbear.test.util.Constants.PWD;
import static polarbear.test.util.Constants.VERIFY_CODE;

import java.io.UnsupportedEncodingException;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.ValidateException;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.User;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.service.register.AppRegisterStep2Service;
import com.polarbear.service.register.util.VerifyCodeEncoder;
import com.polarbear.util.date.ExpiryClock;
import com.polarbear.util.date.SystemClock;

public class AppRegisterServiceStep2Test extends AbstractMock {
    AppRegisterStep2Service appRegisterStep2Service = new AppRegisterStep2Service();
    String NEED_COMPARE_VERIFY_CODE_ENCODE;
    String EXPIRY_VERIFY_CODE_ENCODE; 
        
    @Before
    public void setUp() {
        setServiceAndDependentComponent(appRegisterStep2Service, "userDao");
        try {
            NEED_COMPARE_VERIFY_CODE_ENCODE = VerifyCodeEncoder.getInstance().encodeNeedCompareVerifyCode(VERIFY_CODE, CELLPHONE);            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRegisterSuccessWhenInputVerificationCodeAndPasswordOnRegisterStep2() throws ValidateException, DaoException, NumberFormatException, UnsupportedEncodingException {
        context.checking(new Expectations() {
            {
                allowing(userDao).store(with(any(User.class)));
            }
        });
        LoginData<User> loginData = appRegisterStep2Service.completeStep2(VERIFY_CODE, NEED_COMPARE_VERIFY_CODE_ENCODE, PWD);
        assertThat(loginData.getUser().getName(), is(String.valueOf(CELLPHONE)));
    }
    
    @Test
    public void shouldThrowExceptionWhenInputVerificationCodeTimeoutOnRegisterStep2() throws ValidateException, NumberFormatException, DaoException, UnsupportedEncodingException {
        expectedEx.expect(ValidateException.class);
        expectedEx.expectMessage(VERIFY_CODE_INVIDIT.emsg());
        EXPIRY_VERIFY_CODE_ENCODE = VerifyCodeEncoder.getInstance().setClock(new ExpiryClock().plusMinutes(-1)).encodeNeedCompareVerifyCode(VERIFY_CODE, CELLPHONE);//使用过期时钟测试过期效验码
        VerifyCodeEncoder.getInstance().setClock(new SystemClock());//恢复使用系统使用
        appRegisterStep2Service.completeStep2(VERIFY_CODE, EXPIRY_VERIFY_CODE_ENCODE, PWD);
    }

    @Test
    public void shouldThrowExceptionWhenInputVerificationCodeErrOnRegisterStep2() throws ValidateException, NumberFormatException, DaoException, UnsupportedEncodingException {
        expectedEx.expect(ValidateException.class);
        expectedEx.expectMessage(PARAM_ERR.emsg());
        appRegisterStep2Service.completeStep2(ERR_VERIFY_CODE, NEED_COMPARE_VERIFY_CODE_ENCODE, PWD);
    }

}
