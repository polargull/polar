package polarbear.acceptance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.acceptance.order.CreateOrderTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { CreateOrderTest.class })
public class OrderAcceptanceTestSuite {

}
