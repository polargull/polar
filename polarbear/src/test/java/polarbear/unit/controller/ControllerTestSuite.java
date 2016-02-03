package polarbear.unit.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.unit.controller.login.BackLoginControllerTest;
import polarbear.unit.controller.login.FrontLoginControllerTest;
import polarbear.unit.controller.register.AppRegisterControllerStep1Test;
import polarbear.unit.controller.register.AppRegisterControllerStep2Test;
import polarbear.unit.controller.shopcart.AddShopcartControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { FrontLoginControllerTest.class, BackLoginControllerTest.class, AppRegisterControllerStep1Test.class, AppRegisterControllerStep2Test.class, AddShopcartControllerTest.class })
public class ControllerTestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
