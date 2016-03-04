package polarbear.acceptance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.acceptance.order.CancleOrderTest;
import polarbear.acceptance.order.CreateOrderTest;
import polarbear.acceptance.order.DeliveryOrderTest;
import polarbear.acceptance.order.OrderRelationOpLoginValidateTest;
import polarbear.acceptance.order.PayOrderTest;
import polarbear.acceptance.order.SignOrderTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { CreateOrderTest.class, CancleOrderTest.class, PayOrderTest.class, SignOrderTest.class, OrderRelationOpLoginValidateTest.class, DeliveryOrderTest.class })
public class OrderAcceptanceTestSuite {
}