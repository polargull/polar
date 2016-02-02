package polarbear.acceptance.back;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.acceptance.back.login.LoginTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { LoginTest.class })
public class AcceptanceBackTestSuite {
}