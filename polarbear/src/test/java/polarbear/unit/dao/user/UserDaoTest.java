package polarbear.unit.dao.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import com.polarbear.domain.User;
import com.polarbear.util.MD5Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class UserDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final String UNAME = "极地鸥";
    private final String PWD = "123456";
    final String MD5_PWD = MD5Util.encode2hex(PWD);

    @Autowired
    private BaseDao<User> userDao;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldHaveUserWhenInputCorrectUnameAndPwd() {
        User u = userDao.findByNamedQueryObject("queryUnameAndPwd", UNAME, MD5_PWD);
        assertThat("用户名：" + UNAME + ", 密码：" + PWD + "应该不为空", u, is(notNullValue()));
        assertThat(u.getName(), is(UNAME));
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
