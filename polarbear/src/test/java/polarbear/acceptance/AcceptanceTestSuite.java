package polarbear.acceptance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.acceptance.login.LoginTest;
import polarbear.acceptance.regist.RegistStep1Test;
import polarbear.acceptance.regist.RegistStep2Test;
import polarbear.acceptance.shopcart.AddShopcartTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { LoginTest.class, RegistStep1Test.class, RegistStep2Test.class, AddShopcartTest.class })
public class AcceptanceTestSuite {

}
