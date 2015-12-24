package polarbear.unit.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.unit.dao.user.UserDaoTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { UserDaoTest.class })
public class DaoTestSuite {
}