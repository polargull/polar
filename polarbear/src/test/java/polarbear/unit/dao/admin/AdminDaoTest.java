package polarbear.unit.dao.admin;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static polarbear.test.util.Constants.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Admin;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class AdminDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Autowired
    private BaseDao<Admin> adminDao;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldHaveAdminWhenInputCorrectUnameAndPwd() throws DaoException {
        jdbcTemplate.update("insert into `admin`(`name`,`pwd`) values(?, ?)", new Object[] { ADMIN_UNAME, MD5_PWD });
        Admin admin = adminDao.findByNamedQueryObject("queryAdminNameAndPwd", ADMIN_UNAME, MD5_PWD);
        assertThat("用户名：" + UNAME + ", 密码：" + PWD + "应该不为空", admin, is(notNullValue()));
        assertThat(admin.getName(), is(ADMIN_UNAME));
    }

}