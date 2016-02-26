package temp;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.polarbear.domain.Category;
import com.polarbear.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class JdbcTemplateTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Before
    public void setUp() {
        jdbcTemplate.update("insert into `category`(`id`,`cg_desc`) values(?,?)", new Object[] { 1l, "服装" });
        jdbcTemplate.update("insert into `category`(`id`,`cg_desc`) values(?,?)", new Object[] { 2l, "玩具" });
    }

    @Test
    public void testUpdateUserName() {
        // User uu = userDao.findById(User.class, 1l);
        List<User> ulst = jdbcTemplate.query("select * from user where name = 'fuweigood'", BeanPropertyRowMapper.newInstance(User.class));
        boolean hasValue = false;
        for (User u : ulst) {
            if (u.getName().equals("fuweigood")) {
                hasValue = true;
            }
        }
    }

    @Test
    public void testQueryForObject() {
        Category category1 = jdbcTemplate.queryForObject("select * from Category where cg_desc = ? ", new Object[] { "服装" }, BeanPropertyRowMapper.newInstance(Category.class));
        assertTrue(category1.getId() == 1l);
    }
    
    @Test
    public void testQueryForListHaveIn() {
        Map<String, Object> paramMap = new HashMap<String, Object>() {{
            put("id", 1);
            put("id", 2);
        }};
        List<Map<String, Object>> category1 = jdbcTemplate.queryForList("select * from Category where id in (:id) ", paramMap);
        System.out.println(category1.toString());
    }
}