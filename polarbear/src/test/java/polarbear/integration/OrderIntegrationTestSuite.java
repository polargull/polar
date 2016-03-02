package polarbear.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.integration.service.order.CancleOrderServiceTest;
import polarbear.integration.service.order.CreateOrderServiceTest;
import polarbear.integration.service.order.PayOrderServiceTest;
import polarbear.integration.service.order.SignOrderServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { CreateOrderServiceTest.class, CancleOrderServiceTest.class, PayOrderServiceTest.class, SignOrderServiceTest.class })
public class OrderIntegrationTestSuite {
}