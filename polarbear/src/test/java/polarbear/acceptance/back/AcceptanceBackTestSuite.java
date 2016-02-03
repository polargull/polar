package polarbear.acceptance.back;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.acceptance.back.login.AdminLoginTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { AdminLoginTest.class })
public class AcceptanceBackTestSuite {
}