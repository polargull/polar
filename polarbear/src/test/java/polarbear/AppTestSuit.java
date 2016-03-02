package polarbear;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.integration.AllIntegrationTest;
import polarbear.unit.controller.ControllerTestSuite;
import polarbear.unit.dao.DaoAllTest;
import polarbear.unit.service.ServiceTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 
    ControllerTestSuite.class, 
    ServiceTestSuite.class, 
    DaoAllTest.class,
    AllIntegrationTest.class
})
public class AppTestSuit {
    // the class remains empty,
    // used only as a holder for the above annotations
}
