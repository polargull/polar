package polarbear.unit.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.unit.service.balance.BalanceServiceTestSuite;
import polarbear.unit.service.login.FrontLoginServiceTest;
import polarbear.unit.service.product.MultipleStyleProductQueryTest;
import polarbear.unit.service.register.AppRegisterServiceStep1Test;
import polarbear.unit.service.register.AppRegisterServiceStep2Test;
import polarbear.unit.service.shopcart.AddProductToShopcartServiceTest;
import polarbear.unit.service.shopcart.ModifyProductNumFromShopcartServiceTest;
import polarbear.unit.service.shopcart.RemoveShopcartProductServiceTest;
import polarbear.unit.service.sms.SmsServiceTest;
import polarbear.unit.service.user.validate.UnameUniqueValidatorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { FrontLoginServiceTest.class
    ,MultipleStyleProductQueryTest.class 
    ,AppRegisterServiceStep1Test.class 
    ,AppRegisterServiceStep2Test.class
    ,SmsServiceTest.class
    ,UnameUniqueValidatorTest.class
    ,AddProductToShopcartServiceTest.class
    ,ModifyProductNumFromShopcartServiceTest.class
    ,RemoveShopcartProductServiceTest.class
    ,BalanceServiceTestSuite.class
    })
public class ServiceTestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
