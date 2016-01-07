package polarbear.acceptance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.acceptance.login.LoginTest;
import polarbear.acceptance.regist.RegistStep1Test;
import polarbear.acceptance.regist.RegistStep2Test;
import polarbear.acceptance.shopcart.ShopcartAddTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { LoginTest.class, RegistStep1Test.class, RegistStep2Test.class, ShopcartAddTest.class })
public class AcceptanceTestSuite {

}
