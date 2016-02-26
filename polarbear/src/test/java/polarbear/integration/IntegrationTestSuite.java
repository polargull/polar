package polarbear.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.integration.service.order.CreateOrderServiceTest;
import polarbear.integration.service.product.ProductManagerServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ProductManagerServiceTest.class, CreateOrderServiceTest.class })
public class IntegrationTestSuite {
}