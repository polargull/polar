package polarbear.unit.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.unit.dao.admin.AdminDaoTest;
import polarbear.unit.dao.product.ProductDaoTest;
import polarbear.unit.dao.shopcart.ShopcartDaoTest;
import polarbear.unit.dao.user.UserDaoTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { UserDaoTest.class, AdminDaoTest.class, ProductDaoTest.class, ShopcartDaoTest.class, OrderDaoTestSuite.class })
public class DaoAllTest {
}