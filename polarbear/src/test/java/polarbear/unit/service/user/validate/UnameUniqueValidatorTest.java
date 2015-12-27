package polarbear.unit.service.user.validate;

import static org.junit.Assert.assertTrue;
import static polarbear.testdata.user.UserBuilder.anUser;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import polarbear.unit.service.AbstractMock;

import com.polarbear.ValidateException;
import com.polarbear.domain.User;
import com.polarbear.service.user.validate.UnameUniqueValidator;
import static polarbear.test.util.Constants.*;

public class UnameUniqueValidatorTest extends AbstractMock {
    private UnameUniqueValidator validator  = new UnameUniqueValidator();

    @Before
    public void setUp() {
        setServiceAndDependentComponent(validator, "userDao");
    }

    @Test
    public void shouldNotReturnWhenValidatePass() {
        context.checking(new Expectations() {
            {
                allowing(userDao).findByNamedQuery("queryUname", UNAME);
                // 设定预期值
                will(returnValue(new ArrayList<User>()));
            }
        });
        try {
            validator.validate(UNAME);
        } catch (Exception e) {
            assertTrue("用户名唯一性检验", false);
        }
        assertTrue("用户名唯一性检验", true);
    }

    @Test
    public void shouldThrowValidateExceptionWhenValidateNotPass() throws ValidateException {
        context.checking(new Expectations() {
            {
                allowing(userDao).findByNamedQuery("queryUname", UNAME);
                List<User> userList = new ArrayList<User>();
                userList.add(anUser().withUname(UNAME).build());
                // 设定预期值
                will(returnValue(userList));
            }
        });
        expectedEx.expect(ValidateException.class);
        expectedEx.expectMessage("用户名已被占用");
        validator.validate(UNAME);
    }
}
