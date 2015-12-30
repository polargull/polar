package temp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.unit.controller.ControllerTestSuite;
import polarbear.unit.dao.DaoTestSuite;
import polarbear.unit.service.ServiceTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { JodaTimeTest.class,FastJsonTest.class })
public class AppTestSuit {
    // the class remains empty,
    // used only as a holder for the above annotations
}
