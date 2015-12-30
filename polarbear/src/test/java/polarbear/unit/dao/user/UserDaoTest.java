package polarbear.unit.dao.user;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import static polarbear.testdata.user.UserBuilder.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.User;
import static polarbear.test.util.Constants.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class UserDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Autowired
    private BaseDao<User> userDao;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldHaveUserWhenInputCorrectUnameAndPwd() throws DaoException {
        User u = userDao.findByNamedQueryObject("queryUnameAndPwd", UNAME, MD5_PWD);
        assertThat("用户名：" + UNAME + ", 密码：" + PWD + "应该不为空", u, is(notNullValue()));
        assertThat(u.getName(), is(UNAME));
    }
    
    @Test
    public void shouldRegisterSuccessWhenInputCorrectUnameAndPwdAndCellphone() {
        try {
            userDao.store(anUser().withUname(NEW_REGISTER).withPassword(MD5_PWD).withCellphone(NEW_CELLPHONE).build());
        } catch (DaoException e) {
            fail("注册dao操作失败了,msg:" + e.getMessage());
        }
        User user = jdbcTemplate.queryForObject(
                "select * from user where name = ? and pwd = ?",
                new Object[] { NEW_REGISTER, MD5_PWD }, BeanPropertyRowMapper
                        .newInstance(User.class));
        assertThat("用户名：" + UNAME + ", 密码：" + PWD + "应该注册成功", user, notNullValue());        
    }

    // @Test
    public void testUpdateUserName() {
        // User uu = userDao.findById(User.class, 1l);
        List<User> ulst = jdbcTemplate.query("select * from user where name = 'fuweigood'", BeanPropertyRowMapper.newInstance(User.class));
        boolean hasValue = false;
        for (User u : ulst) {
            if (u.getName().equals("fuweigood")) {
                hasValue = true;
            }
        }
        assertTrue(hasValue);
    }

    // @Test
    // @Rollback(false)
    public void testUserList() {
        List<User> u = jdbcTemplate.query("select * from user", BeanPropertyRowMapper.newInstance(User.class));
        assertEquals(3, u.size());
    }
}
