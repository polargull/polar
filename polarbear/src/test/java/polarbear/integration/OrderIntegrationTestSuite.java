package polarbear.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.integration.service.order.CancleOrderServiceTest;
import polarbear.integration.service.order.CreateOrderServiceTest;
import polarbear.integration.service.order.PayOrderServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { CreateOrderServiceTest.class, CancleOrderServiceTest.class, PayOrderServiceTest.class })
public class OrderIntegrationTestSuite {
}