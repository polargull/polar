package polarbear.unit.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import polarbear.unit.dao.order.OrderDaoTest;
import polarbear.unit.dao.order.OrderListDaoTest;
import polarbear.unit.dao.order.PayLogDaoTest;


@RunWith(Suite.class)
@Suite.SuiteClasses( { OrderDaoTest.class, OrderListDaoTest.class, PayLogDaoTest.class })
public class OrderDaoTestSuite {
}