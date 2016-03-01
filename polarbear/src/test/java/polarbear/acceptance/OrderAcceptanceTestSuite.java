package polarbear.acceptance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.acceptance.order.CancleOrderTest;
import polarbear.acceptance.order.CreateOrderTest;
import polarbear.acceptance.order.OrderRelationOpLoginValidateTest;
import polarbear.acceptance.order.PayOrderTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { CreateOrderTest.class, CancleOrderTest.class, PayOrderTest.class, OrderRelationOpLoginValidateTest.class })
public class OrderAcceptanceTestSuite {
}