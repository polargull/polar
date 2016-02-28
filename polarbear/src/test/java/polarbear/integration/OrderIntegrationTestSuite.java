package polarbear.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.integration.service.order.CreateOrderServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { CreateOrderServiceTest.class })
public class OrderIntegrationTestSuite {
}