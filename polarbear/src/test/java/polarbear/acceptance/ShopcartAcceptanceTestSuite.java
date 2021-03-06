package polarbear.acceptance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.acceptance.back.AcceptanceBackTestSuite;
import polarbear.acceptance.balance.BalanceTest;
import polarbear.acceptance.login.LoginTest;
import polarbear.acceptance.product.ProductTestSuite;
import polarbear.acceptance.regist.RegistTestSuite;
import polarbear.acceptance.shopcart.ShopcartTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ShopcartTestSuite.class })
public class ShopcartAcceptanceTestSuite {

}
